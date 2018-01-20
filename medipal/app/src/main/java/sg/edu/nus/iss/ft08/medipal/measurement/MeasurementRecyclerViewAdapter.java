package sg.edu.nus.iss.ft08.medipal.measurement;

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
import sg.edu.nus.iss.ft08.medipal.core.Measurement;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.measurement.MeasurementFragment.OnListFragmentInteractionListener;

public class MeasurementRecyclerViewAdapter extends RecyclerView.Adapter<MeasurementRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private List<Measurement> latestMasurements;
    private Context context;


    public MeasurementRecyclerViewAdapter(Context context,
                                          OnListFragmentInteractionListener listener){
        this.context = context;
        latestMasurements = findLatestMeasurements(context);
        this.mListener = listener;
    }

    private List<Measurement> findLatestMeasurements(Context context) {
        List<Measurement> records = new ArrayList<>();

        try {
            FindLatestMeasurementQuery query = new FindLatestMeasurementQuery(context);
            query.execute();
            records = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (records == null) {
            records = new ArrayList<Measurement>();
        }

        return records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_measurement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Measurement current = latestMasurements.get(position);
        holder.mItem = current;
        holder.mTxtTypeName.setText(current.getMeasurementTypeName());
        holder.mTxtValueDescription.setText(current.getValueDesription());
        holder.mTxtMeasureDateTime.setText(MediPalUtils.toString_d_MMM_yyyy_H_mm(current.getMeasuredOn()));

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
        return latestMasurements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTxtTypeName;
        public final TextView mTxtValueDescription;
        public final TextView mTxtMeasureDateTime;
        public Measurement mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTxtTypeName = (TextView) view.findViewById(R.id.measurement_type_name);
            mTxtValueDescription = (TextView) view.findViewById(R.id.measurement_value_description);
            mTxtMeasureDateTime = (TextView) view.findViewById(R.id.measurement_date_and_time);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTxtTypeName.getText() + "' " + " '" + mTxtValueDescription.getText() + "'";
        }
    }
}
