package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.HealthBio;

public class HealthBioListActivity extends AppCompatActivity implements HealthBioFragment.OnListFragmentInteractionListener {
    private boolean mTwoPane;
    private HealthBioRecyclerViewAdapter healthBioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthbio_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.healthbio_list_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.healthbio_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HealthBioListActivity.this, HealthBioDetailActivity.class);
                startActivity(intent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.healthbio_list_id);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

//        if (findViewById(R.id.healthbio_detail_container) != null) {
//            mTwoPane = true;
//        }
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

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        healthBioAdapter = new HealthBioRecyclerViewAdapter(HealthBioListActivity.this, this);
        recyclerView.setAdapter(healthBioAdapter);

    }

    @Override
    public void onListFragmentInteraction(HealthBio item) {
        Bundle parameters = new Bundle();
        parameters.putInt(HealthBioFragment.ARG_HEALTHBIO_ID, item.getId());

        Intent i = new Intent(HealthBioListActivity.this, HealthBioDetailActivity.class);
        i.putExtras(parameters);
        startActivity(i);

    }

    public void onResume() {
        super.onResume();
        healthBioAdapter.refresh(this);
    }
}
