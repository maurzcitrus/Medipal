package sg.edu.nus.iss.ft08.medipal.emergency;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;


/**
 * A fragment representing a single EmergencyContact detail screen.
 * This fragment is either contained in a {@link EmergencyContactListActivity}
 * in two-pane mode (on tablets) or a {@link EmergencyContactDetailActivity}
 * on handsets.
 */
public class EmergencyContactDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_EMERGENCY_ID = "emergency_id";
    private OnListFragmentInteractionListener eListener;
    private TextView number;
    private EmergencyRecyclerViewAdapter emergencyAdapter;

    public EmergencyContactDetailFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emergency_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {

            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;
            emergencyAdapter = new EmergencyRecyclerViewAdapter(context, eListener);
            recyclerView.setAdapter(emergencyAdapter);
        }
        return view;
    }

    public void onResume() {
        super.onResume();
        emergencyAdapter.refresh(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            eListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        eListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(EmergencyContact item);
    }
}