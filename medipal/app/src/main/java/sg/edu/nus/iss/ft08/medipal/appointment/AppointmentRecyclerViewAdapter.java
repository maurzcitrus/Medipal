package sg.edu.nus.iss.ft08.medipal.appointment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.appointment.AppointmentFragment.OnListFragmentInteractionListener;
import sg.edu.nus.iss.ft08.medipal.core.Appointment;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

public class AppointmentRecyclerViewAdapter extends RecyclerView.Adapter<AppointmentRecyclerViewAdapter.ViewHolder> {

    private List<Appointment> appointments;
    private final OnListFragmentInteractionListener mListener;

    public AppointmentRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        appointments = findAllAppointment(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Appointment current = appointments.get(position);
        holder.mItem = current;
        holder.mAppointmentLocation.setText(current.getLocation());
        holder.mAppointmentDate.setText(toString_d_MMM_yyyy_H_mm(current.getAppointmentDateTime()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void refresh(Context context) {
        appointments = findAllAppointment(context);
        notifyDataSetChanged();
    }

    private List<Appointment> findAllAppointment(Context context){
        List<Appointment> records = null;

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
            records = new ArrayList<Appointment>();
        }

        return records;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAppointmentLocation;
        public final TextView mAppointmentDate;
        public Appointment mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAppointmentLocation = (TextView) view.findViewById(R.id.fragmentlocation);
            mAppointmentDate = (TextView) view.findViewById(R.id.fragmentappointmentdate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAppointmentLocation.getText() + "'"+mAppointmentDate+ "'";
        }
    }
}
