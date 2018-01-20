package sg.edu.nus.iss.ft08.medipal.measurement;

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


public class MeasurementDetailTemperatureActivity extends AppCompatActivity {

    private Temperature currentrecord;
    private boolean isNewRecord;
    private FloatingActionButton savetemp;
    private FloatingActionButton deletetemp;
    private AutoCompleteTextView temperatureT;
    private AutoCompleteTextView tempnotes;

    private FloatingActionButton getSaveButton(){
        if (savetemp == null){
            savetemp = (FloatingActionButton) findViewById(R.id.temperature_save);
        }
        return savetemp;
    }

    private FloatingActionButton getDeletButton(){
        if(deletetemp == null){
            deletetemp = (FloatingActionButton) findViewById(R.id.temperature_delete);
        }
        return deletetemp;
    }

    private AutoCompleteTextView getTxtTemperaturetemp(){
        if (temperatureT == null) {
            temperatureT = (AutoCompleteTextView) findViewById(R.id.temperature_id);
        }
        return temperatureT;
    }

    private float getTemperaturecelcius(){
        return MediPalUtils.toFloat(getTxtTemperaturetemp().getText().toString());
    }

    private AutoCompleteTextView getTxtTemperatureNotes(){
        if (tempnotes == null){
            tempnotes =(AutoCompleteTextView) findViewById(R.id.temp_notes);
        }
        return tempnotes;
    }

    private String getTemperatureNotes(){
        return getTxtTemperatureNotes().getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_temperature_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBtnSave();
        initBtnDelete();

        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentrecord = getTempParameter(parameters);
            BindValues(currentrecord);
        } else {
            currentrecord = new Temperature();
            isNewRecord = true;
        }
    }
        private void BindValues(Temperature t) {
            getTxtTemperaturetemp().setText(Float.toString(t.getTemperature()));
            getTxtTemperatureNotes().setText(t.getMeasurementNotes());

        }

    private Temperature getTempParameter(Bundle parameters) {
        Temperature t = null;

        if (parameters == null) {
            return new Temperature();
        }

        int tempid = parameters.getInt(temperatureFragment.ARG_MEASUREMENT_ID);
        FindOneQueryTemperature query = new FindOneQueryTemperature(MeasurementDetailTemperatureActivity.this, tempid);
        try {
            query.execute();
            t = query.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return t;


    }

    private void initBtnSave(){
        getSaveButton().setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                savetemperature(v);
            }
        });
    }

    private void savetemperature(View v){
        if (isNewRecord){
            createTemperature(v);
        } else
        {
            updateTemperature(v);
        }
    }

    private void setTemperatureValues(Temperature temp){
        temp.setTemperature(getTemperaturecelcius());
        temp.setMeasurementNotes(getTemperatureNotes());
        temp.setMeasuredOn(MediPalUtils.getToday());
    }

    private void createTemperature(View v){
        Temperature temp;
        CreateCommandTemperature command;
        boolean isValid;

        currentrecord = new Temperature();

//        temp = currentrecord.getTempvalidation();
        setTemperatureValues(currentrecord);
        isValid = checkTemperature(currentrecord);

        if (isValid){
            command = new CreateCommandTemperature(v.getContext());
            command.execute(currentrecord);

            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateTemperature(View v){

        UpdateCommandTemperature command;

        boolean isValid;

        setTemperatureValues(currentrecord);
        isValid = checkTemperature(currentrecord);

        if (isValid){
            command = new UpdateCommandTemperature(v.getContext());
            command.execute(currentrecord);

            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean checkTemperature(Temperature temp){
        boolean isValid = true;

        if (!temp.isValidTemperature()){
            getTxtTemperaturetemp().setError("Temperature value is required");
            isValid = false;
        }

        if(!temp.isValidTemperatureRange()){
            getTxtTemperaturetemp().setError("Invalid Temperature in degree Celcius");
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
                deletetemperature(v);
            }
        });
    }

    private void deletetemperature(View v){
        DeleteCommandTemperature command = new DeleteCommandTemperature(v.getContext());
        command.execute(currentrecord);
        Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
        finish();
    }

}
