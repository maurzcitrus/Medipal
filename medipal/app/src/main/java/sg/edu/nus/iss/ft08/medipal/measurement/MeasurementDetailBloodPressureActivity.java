package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;


public class MeasurementDetailBloodPressureActivity extends AppCompatActivity {

    private BloodPressure currentRecord;
    private boolean isNewRecord;
    private FloatingActionButton saveBP;
    private FloatingActionButton deletebp;
    private AutoCompleteTextView systolicBP;
    private AutoCompleteTextView diastolicBP;
    private AutoCompleteTextView bpnotes;


    private FloatingActionButton getSaveButton(){
        if (saveBP == null){
            saveBP = (FloatingActionButton) findViewById(R.id.bp_save);
        }

        return saveBP;
    }

    private FloatingActionButton getDeleteButton(){
        if (deletebp == null){
            deletebp = (FloatingActionButton) findViewById(R.id.bp_delete);
        }
        return deletebp;
    }

    private AutoCompleteTextView getTxtBloodpressureSystolic(){
        if (systolicBP == null){
            systolicBP = (AutoCompleteTextView) findViewById(R.id.systolic_id);
        }
        return systolicBP;
    }

    private AutoCompleteTextView getTxtBloodpressureDiastolic(){
        if (diastolicBP == null){
            diastolicBP =(AutoCompleteTextView) findViewById(R.id.diastolic_id);
        }
        return diastolicBP;
    }

    private AutoCompleteTextView getTxtBloodpressureNotes(){
        if (bpnotes == null){
            bpnotes =(AutoCompleteTextView) findViewById(R.id.bp_notes);
        }
        return bpnotes;
    }

    private int getBloodpressureSystolic(){
        return MediPalUtils.toInt(getTxtBloodpressureSystolic().getText().toString());
    }

    private int getBloodpressureDiastolic(){
        return MediPalUtils.toInt(getTxtBloodpressureDiastolic().getText().toString());
    }

    private String getBloodpressureNotes(){
        return getTxtBloodpressureNotes().getText().toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_measurement_bloodpressure_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        initBtnSave();
        initBtnDelete();

        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentRecord = getBloodPressure(parameters);
            BindValues(currentRecord);
        } else {
            currentRecord = new BloodPressure();
            isNewRecord = true;
        }
    }

    private void BindValues(BloodPressure bp) {
        getTxtBloodpressureSystolic().setText(Integer.toString(bp.getSystolic()));
        getTxtBloodpressureDiastolic().setText(Integer.toString(bp.getDiastolic()));
        getTxtBloodpressureNotes().setText(bp.getMeasurementNotes());
    }

    private BloodPressure getBloodPressure(Bundle parameters){
        BloodPressure bp = null;

        if (parameters == null){
            return new BloodPressure();
        }


        int bloodpressureid = parameters.getInt(bloodpressureFragment.ARG_MEASUREMENT_ID);
        FindOneQueryBloodPressure query = new FindOneQueryBloodPressure(MeasurementDetailBloodPressureActivity.this, bloodpressureid);
        try {
            query.execute();
            bp = query.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return bp;
    }

    private void initBtnSave(){
        getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBloodpressure(v);
            }
        });
    }

    private void saveBloodpressure(View v){
        if (isNewRecord) {
            createBloodPressure(v);
        } else {
            updateBloodPressure(v);
        }
    }

    private void createBloodPressure(View v) {
        BloodPressure bloodPressure;
        CreateCommandBloodPressure command;
        boolean isValid;

        currentRecord = new BloodPressure();
        setBloodpressureValues(currentRecord);

        isValid = checkBloodpressure(currentRecord);

        if (isValid) {
            command = new CreateCommandBloodPressure(v.getContext());
            command.execute(currentRecord);

            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateBloodPressure(View v){
        BloodPressure bloodPressure;
        UpdateCommandBloodPressure command;
        boolean isValid;

        setBloodpressureValues(currentRecord);

        isValid = checkBloodpressure(currentRecord);
        if(isValid){
            command = new UpdateCommandBloodPressure(v.getContext());
            command.execute(currentRecord);

            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setBloodpressureValues(BloodPressure bloodpressure){
        bloodpressure.setSystolic(getBloodpressureSystolic());
        bloodpressure.setDiastolic(getBloodpressureDiastolic());
        bloodpressure.setMeasurementNotes(getBloodpressureNotes());
        bloodpressure.setMeasuredOn(MediPalUtils.getToday());
    }

    private void initBtnDelete(){
        if (isNewRecord){
            getDeleteButton().setVisibility(View.INVISIBLE);
        } else {
            getDeleteButton().setVisibility(View.VISIBLE);
        }

        getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBloodpressure(v);
            }
        });
    }

    private void deleteBloodpressure(View v){
        DeleteCommandBloodPressure command = new DeleteCommandBloodPressure(v.getContext());
        command.execute(currentRecord);
        Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean checkBloodpressure(BloodPressure bloodpressure){
        boolean isValid = true;

        if (!bloodpressure.isValidSystolic()) {
            getTxtBloodpressureSystolic().setError("Systolic BloodPressure is required, It should be between 90 to 240 mmHG");
            isValid = false;
        }

        if (!bloodpressure.isValidDiastolic()){
            getTxtBloodpressureDiastolic().setError("Diastolic BloodPressure is required, It should bet between 40 to 160 mmHG");
            isValid = false;
        }
         return isValid;
    }
}

