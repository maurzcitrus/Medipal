package sg.edu.nus.iss.ft08.medipal.emergency;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;


public class EmergencyContactListActivity extends AppCompatActivity implements
        EmergencyContactDetailFragment.OnListFragmentInteractionListener {


    private boolean mTwoPane;
    private EmergencyRecyclerViewAdapter emergencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencycontact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyContactListActivity.this,
                        EmergencyContactDetailActivity.class);
                startActivity(intent);
            }
        });
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.list1);
        if (recyclerView != null)
            setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.emergencycontact_detail_container) != null) {
            mTwoPane = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        emergencyAdapter = new EmergencyRecyclerViewAdapter(EmergencyContactListActivity.this,this);
        recyclerView.setAdapter(emergencyAdapter);

    }

    @Override
    public void onListFragmentInteraction(EmergencyContact item) {
        Bundle parameters = new Bundle();
        parameters.putInt(EmergencyContactDetailFragment.ARG_EMERGENCY_ID, item.getId());
        Intent intent = new Intent(EmergencyContactListActivity.this,
                EmergencyContactDetailActivity.class);
        intent.putExtras(parameters);
        startActivity(intent);
    }
    public void onResume() {
        super.onResume();
        emergencyAdapter.refresh(this);
    }
}