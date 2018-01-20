package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.measurement.weightFragment.OnListFragmentInteractionListener;
import sg.edu.nus.iss.ft08.medipal.measurement.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyweightRecyclerViewAdapter extends RecyclerView.Adapter<MyweightRecyclerViewAdapter.ViewHolder> {

    private List<Weight> weights;
    private final OnListFragmentInteractionListener mListener;

    public MyweightRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        weights = findAllWeights(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weight_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Weight currentweights = weights.get(position);

        holder.mItem = currentweights;
        holder.mValue.setText(currentweights.getValueDesription());
        holder.mDate.setText(MediPalUtils.toString_d_MMM_yyyy_H_mm(currentweights.getMeasuredOn()));

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
        return weights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mValue;
        public final TextView mDate;
        public Weight mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mValue =  (TextView) view.findViewById(R.id.wt_value);
            mDate = (TextView) view.findViewById(R.id.wt_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mValue.getText() + "'";
        }
    }

    public void refresh(Context context){
        weights = findAllWeights(context);
        notifyDataSetChanged();
    }

    private List<Weight> findAllWeights(Context context){
        List<Weight> records = null;

        try {
            FindAllQueryWeight query = new FindAllQueryWeight(context);
            query.execute();
            records = query.get();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        if (records == null){
            records = new ArrayList<Weight>();
        }
        return records;
    }
}
