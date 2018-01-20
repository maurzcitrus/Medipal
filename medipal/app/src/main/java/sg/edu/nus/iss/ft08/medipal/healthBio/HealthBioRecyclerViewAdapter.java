package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.HealthBio;

/**
 * Created by MuraliKrishnaB on 25/3/2017.
 */

public class HealthBioRecyclerViewAdapter extends RecyclerView.Adapter<HealthBioRecyclerViewAdapter.ViewHolder> {
    private List<HealthBio> healbiolist;
    private final HealthBioFragment.OnListFragmentInteractionListener hListener;

    public HealthBioRecyclerViewAdapter(Context context, HealthBioFragment.OnListFragmentInteractionListener listener) {
        healbiolist = findAllhealth(context);
        hListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.healthbio_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HealthBio current = healbiolist.get(position);
        holder.hItem = current;
        holder.hConditionName.setText(current.getCondition());

        holder.hView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != hListener) {

                    hListener.onListFragmentInteraction (holder.hItem);
                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return healbiolist.size();
    }

    public void refresh(Context context) {
        healbiolist = findAllhealth(context);
        notifyDataSetChanged();
    }

    private List<HealthBio> findAllhealth(Context context) {
        List<HealthBio> records = null;

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
            records = new ArrayList<HealthBio>();
        }

        return records;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public final TextView hConditionName;
        public HealthBio hItem;

        public ViewHolder(View view) {
            super(view);
            hView = view;
            hConditionName = (TextView) view.findViewById(R.id.healthbio_list_condition);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + hConditionName.getText() + "'";
        }
    }
}
