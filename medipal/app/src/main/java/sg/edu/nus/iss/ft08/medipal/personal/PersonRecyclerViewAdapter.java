package sg.edu.nus.iss.ft08.medipal.personal;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Person;
import sg.edu.nus.iss.ft08.medipal.emergency.*;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by MuraliKrishnaB on 27/3/2017.
 */

public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder> {
    private final PersonBioDetailFragment.OnListFragmentInteractionListener pListener;
    private List<Person> personValues;
    private Person person;

    public PersonRecyclerViewAdapter(Context context, PersonBioDetailFragment.OnListFragmentInteractionListener listener) {
        personValues = findAllPerson(context);
        pListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.person_list_content, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Person current = personValues.get(position);

        holder.pItem = current;
        holder.pName.setText(current.getName());

        holder.pView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != pListener) {

                    pListener.onListFragmentInteraction(holder.pItem);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return personValues.size();
    }


    public void refresh(Context context) {
        personValues = findAllPerson(context);
        notifyDataSetChanged();
    }

    private List<Person> findAllPerson(Context context) {
        List<Person> records = null;

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
            records = new ArrayList<Person>();
        }
        return records;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View pView;
        public final TextView pName;
        public Person pItem;

        public ViewHolder(final View view) {
            super(view);
            pView = view;
            pName = (TextView) view.findViewById(R.id.person_drawer_name);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + pName.getText() + "'";
        }
    }
}
