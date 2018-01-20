package sg.edu.nus.iss.ft08.medipal.measurement;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;


public class MeasurementDetailPulseActivity extends AppCompatActivity {


    private Pulse currentrecord;
    private boolean isNewRecord;
    private FloatingActionButton savepulse;
    private FloatingActionButton deletepulse;
    private AutoCompleteTextView pulseP;
    private AutoCompleteTextView pulsenotes;

    private FloatingActionButton getSaveButton(){
        if (savepulse == null){
            savepulse = (FloatingActionButton) findViewById(R.id.pulse_save);
        }
        return savepulse;
    }

    private FloatingActionButton getDeletButton(){
        if(deletepulse == null){
            deletepulse = (FloatingActionButton) findViewById(R.id.pulse_delete);
        }
        return deletepulse;
    }

    private AutoCompleteTextView getTxtPulsepulse(){
        if (pulseP == null) {
            pulseP = (AutoCompleteTextView) findViewById(R.id.pulse_id);
        }
        return pulseP;
    }

    private AutoCompleteTextView getTxtPulseNotes(){
        if (pulsenotes == null){
            pulsenotes =(AutoCompleteTextView) findViewById(R.id.pulse_notes);
        }
        return pulsenotes;
    }

    private int getPulserate(){
        return MediPalUtils.toInt(getTxtPulsepulse().getText().toString());
    }

    private String getPulseNotes(){
        return getTxtPulseNotes().getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_measurement_pulse_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBtnSave();
        initBtnDelete();

        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentrecord = getPulseParameter(parameters);
            BindValues(currentrecord);
        } else {
            currentrecord = new Pulse();
            isNewRecord = true;
        }
    }

    private void BindValues(Pulse p) {
        getTxtPulsepulse().setText(Integer.toString(p.getPulse()));
        getTxtPulseNotes().setText(p.getMeasurementNotes());
    }

    private Pulse getPulseParameter(Bundle parameters){
        Pulse p = null;

        if (parameters == null){
            return new Pulse();
        }

        int pulseid = parameters.getInt(pulseFragment.ARG_MEASUREMENT_ID);
        FindOneQueryPulse query = new FindOneQueryPulse(MeasurementDetailPulseActivity.this, pulseid);
        try {
            query.execute();
            p = query.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return p;

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    private void initBtnSave(){
        getSaveButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                savepulse(v);
            }
        });
    }

    private void savepulse(View v){
        if (isNewRecord){
            createPulse(v);
        } else
        {
            updatePulse(v);
        }
    }

    private void setPulseValues(Pulse pulse){
        pulse.setPulse(getPulserate());
        pulse.setMeasurementNotes(getPulseNotes());
        pulse.setMeasuredOn(MediPalUtils.getToday());
    }

    private void createPulse(View v){


        boolean isValid;
        currentrecord = new Pulse();

        setPulseValues(currentrecord);
        isValid = checkPulse(currentrecord);

        if (isValid){
            CreateCommandPulse command = new CreateCommandPulse(v.getContext());
            command.execute(currentrecord);

            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updatePulse(View v){

        UpdateCommandPulse command;

        boolean isValid;

        setPulseValues(currentrecord);
        isValid = checkPulse(currentrecord);

        if (isValid){
            command = new UpdateCommandPulse(v.getContext());
            command.execute(currentrecord);

            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean checkPulse(Pulse pulse){
        boolean isValid = true;

        if (!pulse.inValidPulse()){
            getTxtPulsepulse().setError("Pulse value is required");
            isValid = false;
        }
        return isValid;
    }

    private void initBtnDelete(){
        if(isNewRecord) {
            getDeletButton().setVisibility(View.INVISIBLE);
        } else {
                getDeletButton().setVisibility(View.VISIBLE);
            }

            getDeletButton().setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    deletepulse(v);
                }
            });
    }

    private void deletepulse(View v){
        DeleteCommandPulse command = new DeleteCommandPulse(v.getContext());
        command.execute(currentrecord);
        Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
        finish();
    }


}
