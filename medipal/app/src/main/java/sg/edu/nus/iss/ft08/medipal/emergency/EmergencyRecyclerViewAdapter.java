package sg.edu.nus.iss.ft08.medipal.emergency;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;


import static android.support.v4.content.ContextCompat.startActivity;

public class EmergencyRecyclerViewAdapter extends
        RecyclerView.Adapter<EmergencyRecyclerViewAdapter.ViewHolder> {

    private final EmergencyContactDetailFragment.OnListFragmentInteractionListener eListener;
    private List<EmergencyContact> emergencyContacts;
    private EmergencyContact emergencyContact;

    public EmergencyRecyclerViewAdapter(Context context,
                                        EmergencyContactDetailFragment
                                                .OnListFragmentInteractionListener listener)
    {
        emergencyContacts = findAllEmergencyContact(context);
        eListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_emergency, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final EmergencyContact current = emergencyContacts.get(position);

        holder.eItem = current;
        holder.eType.setText(current.getContactType());
        holder.eName.setText(current.getName());
        holder.eView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != eListener) {

                    eListener.onListFragmentInteraction(holder.eItem);
                }
            }
        });
        holder.eImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + current.getContactNo()));
                if (ActivityCompat.checkSelfPermission(v.getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions((Activity) v.getContext(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            10);
                    return;
                } else {     //have got permission
                    try {
                        startActivity(v.getContext(), intent, Bundle.EMPTY);

                        //call activity and make phone call
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(v.getContext(), "yourActivity is not founded",
                                Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
        holder.eSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sending message from Description which user has entered

                String smsText = current.getDescription().toString();

                Uri uri = Uri.parse("smsto:" + current.getContactNo());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body",smsText);
                startActivity(v.getContext(),intent,Bundle.EMPTY);
            }});

    }


    @Override
    public int getItemCount() {
        return emergencyContacts.size();
    }


    public void refresh(Context context) {
        emergencyContacts = findAllEmergencyContact(context);
        notifyDataSetChanged();
    }

    private List<EmergencyContact> findAllEmergencyContact(Context context) {
        List<EmergencyContact> records = null;

        try {
            FindAllQuery query = new FindAllQuery(context);
            query.execute();
            records = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (records == null) {
            records = new ArrayList<>();
        }
        return records;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View eView;
        public final TextView eType;
        public final TextView eName;
        public final ImageButton eImage;
        public final ImageButton eSms;
        public EmergencyContact eItem;


        public ViewHolder(final View view) {
            super(view);
            eView = view;
            eType = (TextView) view.findViewById(R.id.type);
            eName = (TextView) view.findViewById(R.id.name);
            eImage=(ImageButton) view.findViewById(R.id.call);
            eSms=(ImageButton)view.findViewById(R.id.msg);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + eName.getText() + "'";
        }
    }
}