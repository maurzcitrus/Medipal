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
 * An activity representing a list of weight. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link weightDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class weightListActivity extends AppCompatActivity implements weightFragment.OnListFragmentInteractionListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<Weight> weightList;
    private MyweightRecyclerViewAdapter weightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(weightListActivity.this, MeasurementDetailWeightActivity.class);
                startActivity(intent);

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        View recyclerView = findViewById(R.id.weight_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.weight_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        weightAdapter = new MyweightRecyclerViewAdapter(weightListActivity.this, this);
        recyclerView.setAdapter(weightAdapter);

//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    @Override
    public void onListFragmentInteraction(Weight item) {
        Bundle parameters = new Bundle();
        parameters.putInt(pulseFragment.ARG_MEASUREMENT_ID, item.getMeasurementId());

        Intent i = new Intent(weightListActivity.this, MeasurementDetailWeightActivity.class);
        i.putExtras(parameters);
        startActivity(i);
    }

    public void onResume() {
        super.onResume();
        weightAdapter.refresh(this);
    }


}
