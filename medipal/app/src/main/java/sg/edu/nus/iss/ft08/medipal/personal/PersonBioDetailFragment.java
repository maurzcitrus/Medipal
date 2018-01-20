package sg.edu.nus.iss.ft08.medipal.personal;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Person;

/**
 * Created by MuraliKrishnaB on 27/3/2017.
 */

public class PersonBioDetailFragment extends Fragment {

    public static final String ARG_PERSON_ID = "emergency_id";
    private OnListFragmentInteractionListener pListener;
    private PersonRecyclerViewAdapter personAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {

            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;
            personAdapter = new PersonRecyclerViewAdapter(getContext(), pListener);
            recyclerView.setAdapter(personAdapter);
        }
        return view;
    }

    public void onResume() {
        super.onResume();
        personAdapter.refresh(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            pListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        pListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Person item);
    }
}
