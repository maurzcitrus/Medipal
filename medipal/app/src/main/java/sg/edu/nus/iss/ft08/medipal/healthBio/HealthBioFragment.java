package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.HealthBio;

/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class HealthBioFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    private int hColumnCount =1;
    public static final String ARG_HEALTHBIO_ID = "healthbio_id";
    private OnListFragmentInteractionListener hListener;
    private HealthBioRecyclerViewAdapter healthBioAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HealthBioFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* if (getArguments() != null) {
            hColumnCount = getArguments().getInt(ARG_HEALTHBIO_ID);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.healthbio_list, container, false);

        if (rootView instanceof RecyclerView) {

            Context context = rootView.getContext();

            RecyclerView recyclerView = (RecyclerView) rootView;
            if (hColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, hColumnCount));
            }

            healthBioAdapter = new HealthBioRecyclerViewAdapter(getContext(), hListener);
            recyclerView.setAdapter(healthBioAdapter);
        }
        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            hListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hListener = null;
    }

    public void onResume() {
        super.onResume();
        healthBioAdapter
                .refresh(getContext());
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(HealthBio item);
    }
}
