package sg.edu.nus.iss.ft08.medipal.measurement;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;


public class MeasurementDetailWeightActivity extends AppCompatActivity {

    private Weight currentrecord;
    private boolean isNewRecord;
    private FloatingActionButton saveweight;
    private FloatingActionButton deleteweight;
    private AutoCompleteTextView WeightW;
    private AutoCompleteTextView wtnotes;

    private FloatingActionButton getSaveButton(){
        if (saveweight == null){
            saveweight = (FloatingActionButton) findViewById(R.id.weight_save);
        }
        return saveweight;
    }

    private FloatingActionButton getDeletButton(){
        if(deleteweight == null){
            deleteweight = (FloatingActionButton) findViewById(R.id.weight_delete);
        }
        return deleteweight;
    }

    private AutoCompleteTextView getTxtWeightweight(){
        if (WeightW == null) {
            WeightW = (AutoCompleteTextView) findViewById(R.id.weight_id);
        }
        return WeightW;
    }

    private AutoCompleteTextView getTxtWeightNotes(){
        if (wtnotes == null){
            wtnotes =(AutoCompleteTextView) findViewById(R.id.weight_notes);
        }
        return wtnotes;
    }

    private int getWeightInKgs(){
        return MediPalUtils.toInt(getTxtWeightweight().getText().toString());
    }

    private String getWeightNotes(){
        return getTxtWeightNotes().getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_measurement_weight_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBtnSave();
        initBtnDelete();

        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentrecord = getWeightParameter(parameters);
            BindValues(currentrecord);
        } else {
            currentrecord = new Weight();
            isNewRecord = true;
        }
    }

    private void BindValues(Weight w){
        getTxtWeightweight().setText(Integer.toString(w.getWeight()));
        getTxtWeightNotes().setText(w.getMeasurementNotes());
    }

    private Weight getWeightParameter(Bundle parameters) {
        Weight w = null;

        if (parameters == null) {
            return new Weight();
        }

        int weightid = parameters.getInt(weightFragment.ARG_MEASUREMENT_ID);
        FindOneQueryWeight query = new FindOneQueryWeight(MeasurementDetailWeightActivity.this, weightid);
        try {
            query.execute();
            w = query.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return w;
    }

    private void initBtnSave(){
        getSaveButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveweight(v);
            }
        });
    }

    private void saveweight(View v){
        if (isNewRecord){
            createWeight(v);
        } else
        {
            updateWeight(v);
        }
    }

    private void setWeightValues(Weight weight){
        weight.setWeight(getWeightInKgs());
        weight.setMeasurementNotes(getWeightNotes());
        weight.setMeasuredOn(MediPalUtils.getToday());
    }

    private void createWeight(View v){

        CreateCommandWeight command;

        boolean isValid;
        currentrecord = new Weight();

//        weight = currentrecord.getWeightvalidation();
        setWeightValues(currentrecord);
        isValid = checkWeight(currentrecord);

        if (isValid){
            command = new CreateCommandWeight(v.getContext());
            command.execute(currentrecord);

            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateWeight(View v){

        UpdateCommandWeight command;

        boolean isValid;

        setWeightValues(currentrecord);
        isValid = checkWeight(currentrecord);

        if (isValid){
            command = new UpdateCommandWeight(v.getContext());
            command.execute(currentrecord);

            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean checkWeight(Weight weight){
        boolean isValid = true;

        if (!weight.inValidWeight()){
            getTxtWeightweight().setError("Weight value is required, Weight can not be more than 200");
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
                deleteweight(v);
            }
        });
    }

    private void deleteweight(View v){
        DeleteCommandWeight command = new DeleteCommandWeight(v.getContext());
        command.execute(currentrecord);
        Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
        finish();
    }
}
