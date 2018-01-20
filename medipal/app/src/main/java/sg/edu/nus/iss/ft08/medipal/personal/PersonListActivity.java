package sg.edu.nus.iss.ft08.medipal.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Person;

/**
 * Created by MuraliKrishnaB on 27/3/2017.
 */

public class PersonListActivity extends AppCompatActivity implements PersonBioDetailFragment.OnListFragmentInteractionListener {

    private boolean mTwoPane;
    private boolean isNewRecord;
    private Person currentRecord;
    private Toolbar persontoolbar;
    private FloatingActionButton personAdd;
    private PersonRecyclerViewAdapter personAdapter;

    private Toolbar getToolbar() {
        if (persontoolbar == null) {
            persontoolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        return persontoolbar;
    }

    private FloatingActionButton getPersonAdd(){
        if(personAdd == null){
            personAdd =  (FloatingActionButton) findViewById(R.id.personadd);
        }
        return personAdd;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        setSupportActionBar(getToolbar());
        getToolbar().setTitle(getTitle());

        initButtonPersonAdd();

       /* getPersonAdd().setVisibility(View.VISIBLE);
        getPersonAdd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonListActivity.this, PersonalDetailActivity.class);
                startActivity(intent);
            }
        });*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.person_list_id);
        if (recyclerView != null)
            setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.person_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void initButtonPersonAdd(){
        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            getPersonAdd().setVisibility(View.INVISIBLE);

        } else {
            getPersonAdd().setVisibility(View.VISIBLE);
            getPersonAdd().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonListActivity.this, PersonalDetailActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    private Person getPerson(Bundle parameters) {
        Person m = null;
        if (parameters == null) {
            return new Person();
        }
        // int id = parameters.getInt(PersonalTable.ID);
        //int personId = parameters.getInt(PersonDetailFragment.ARG_PERSON_ID);
        int personID = parameters.getInt(PersonBioDetailFragment.ARG_PERSON_ID);
        FindOneQuery query = new FindOneQuery(PersonListActivity.this, personID);
        try {
            query.execute();
            m = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return m;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        personAdapter = new PersonRecyclerViewAdapter(PersonListActivity.this, this);
        recyclerView.setAdapter(personAdapter);

    }

    @Override
    public void onListFragmentInteraction(Person item) {
        Bundle parameters = new Bundle();
        parameters.putInt(PersonBioDetailFragment.ARG_PERSON_ID, item.getId());
        Intent intent = new Intent(PersonListActivity.this, PersonalDetailActivity.class);
        intent.putExtras(parameters);
        startActivity(intent);
    }
}
