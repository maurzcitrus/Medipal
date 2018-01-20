package sg.edu.nus.iss.ft08.medipal.medication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Medication;

public class MedicationFragment extends Fragment {

    public static final String ARG_MEDICINE_ID = "medicine-id";
    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnMedicineDetailListener mMedicineListener;
    private OnConsumeListener mConsumptionListener;
    private MedicationRecyclerViewAdapter medicationsAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MedicationFragment() {
    }

    public static MedicationFragment newInstance(int columnCount) {
        MedicationFragment fragment = new MedicationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_medication_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {

            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            medicationsAdapter = new MedicationRecyclerViewAdapter(getContext(), mMedicineListener, mConsumptionListener);
            recyclerView.setAdapter(medicationsAdapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        medicationsAdapter.refresh(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        boolean hasMedicineListener = context instanceof OnMedicineDetailListener;
        if (!hasMedicineListener)
            throw new RuntimeException(context.toString() + " must implement OnMedicineDetailListener");

        boolean hasConsumeListener = context instanceof OnConsumeListener;
        if (!hasConsumeListener)
            throw new RuntimeException(context.toString() + " must implement OnConsumeListener");

        mMedicineListener = (OnMedicineDetailListener) context;
        mConsumptionListener = (OnConsumeListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMedicineListener = null;
        mConsumptionListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnMedicineDetailListener {
        void onViewMedicineDetail(Medication item);
    }

    public interface OnConsumeListener {
        void onConsumeMedicine(Medication item);

    }
}
