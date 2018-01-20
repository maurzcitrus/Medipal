package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.measurement.pulseFragment.OnListFragmentInteractionListener;
import sg.edu.nus.iss.ft08.medipal.measurement.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MypulseRecyclerViewAdapter extends RecyclerView.Adapter<MypulseRecyclerViewAdapter.ViewHolder> {

    private List<Pulse> pulses;
    private final OnListFragmentInteractionListener mListener;

    public MypulseRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        pulses = findAllPulses(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pulse_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Pulse currentpulse = pulses.get(position);

        holder.mItem = currentpulse;
        holder.mValue.setText(currentpulse.getValueDesription());
        holder.mDate.setText(MediPalUtils.toString_d_MMM_yyyy_H_mm(currentpulse.getMeasuredOn()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pulses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mValue;
        public final TextView mDate;
        public Pulse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mValue = (TextView) view.findViewById(R.id.pulse_value);
            mDate = (TextView) view.findViewById(R.id.pulse_date);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mValue.getText() + "'";
        }
    }

    public void refresh(Context context) {
        pulses = findAllPulses(context);
        notifyDataSetChanged();
    }

    private List<Pulse> findAllPulses(Context context){
        List<Pulse> records = null;

        try {
            FindAllQueryPulse query = new FindAllQueryPulse(context);
            query.execute();
            records = query.get();   //Write FindAllQuery for pulse
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        if (records == null){
            records = new ArrayList<Pulse>();
        }
        return records;
    }
}
