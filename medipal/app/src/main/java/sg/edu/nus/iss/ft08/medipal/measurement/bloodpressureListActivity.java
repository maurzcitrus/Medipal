package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.iss.ft08.medipal.R;

import sg.edu.nus.iss.ft08.medipal.measurement.dummy.DummyContent;

import java.util.List;

/**
 * An activity representing a list of bloodpressure. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link bloodpressureDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class bloodpressureListActivity extends AppCompatActivity implements bloodpressureFragment.OnListFragmentInteractionListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<BloodPressure> bloodPressureList;
    private MybloodpressureRecyclerViewAdapter bloodpressureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodpressure_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(bloodpressureListActivity.this, MeasurementDetailBloodPressureActivity.class);
                startActivity(intent);
                
            }
        });

        View recyclerView = findViewById(R.id.bp_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.bloodpressure_detail_container) != null) {

            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        bloodpressureAdapter = new MybloodpressureRecyclerViewAdapter(bloodpressureListActivity.this, this);
        recyclerView.setAdapter(bloodpressureAdapter);
    }

    @Override
    public void onListFragmentInteraction(BloodPressure item) {
        Bundle parameters = new Bundle();
        parameters.putInt(bloodpressureFragment.ARG_MEASUREMENT_ID, item.getMeasurementId());

        Intent i = new Intent(bloodpressureListActivity.this, MeasurementDetailBloodPressureActivity.class);
        i.putExtras(parameters);
        startActivity(i);
    }

    public void onResume() {
        super.onResume();
        bloodpressureAdapter.refresh(this);
    }

}

