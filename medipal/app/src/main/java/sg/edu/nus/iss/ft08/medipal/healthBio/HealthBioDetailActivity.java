package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.HealthBio;
import sg.edu.nus.iss.ft08.medipal.core.MediPalConstants;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;

public class HealthBioDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton btnSave;
    private FloatingActionButton btnDelete;
    private boolean isNewRecord;
    private HealthBio currentRecord;
    private int mYear, mMonth, mDay;
    private AutoCompleteTextView txtHealthBioCondition;
    private AutoCompleteTextView txtHealthBioStartDate;
    private RadioButton allergic;
    private RadioButton diagonised;
    Calendar selectedDate = Calendar.getInstance();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    private AutoCompleteTextView getTxtHealthBioCondition(){
        if(txtHealthBioCondition == null){
            txtHealthBioCondition = (AutoCompleteTextView) findViewById(R.id.healthbio_condition);
        }
        return txtHealthBioCondition;
    }

    private String getHealthBioCondition(){
        return getTxtHealthBioCondition()
                .getText()
                .toString();
    }

    private AutoCompleteTextView getTxtHealthBioStartDate(){
        if(txtHealthBioStartDate == null){
            txtHealthBioStartDate = (AutoCompleteTextView) findViewById(R.id.healthbio_startdate);
        }
        return txtHealthBioStartDate;
    }

    private Date getHealthBioStartDate(){
        String formattedString = getTxtHealthBioStartDate()
                .getText()
                .toString();

        return MediPalUtils.toDate_from_dd_MMM_yyyy(formattedString);
    }

    private FloatingActionButton getSaveButton() {
        if (btnSave == null) {
            btnSave = (FloatingActionButton) findViewById(R.id.healthbio_save);
        }
        return btnSave;
    }

    private FloatingActionButton getDeleteButton() {
        if (btnDelete == null) {
            btnDelete = (FloatingActionButton) findViewById(R.id.healthbio_delete);
        }
        return btnDelete;
    }

    public String getRadioButton() {

        if (diagonised == null) {
            diagonised = (RadioButton) findViewById(R.id.diagonised);
        }

        if (allergic == null) {
            allergic = (RadioButton) findViewById(R.id.Allergic);
        }

        boolean conditionvalue = diagonised.isChecked();

        boolean allergicvalue = allergic.isChecked();

        HealthBio healthBio = new HealthBio();
        if (conditionvalue) {
            healthBio.setConditionType(MediPalConstants.CONDITION);
        }
        else if(allergicvalue){
            healthBio.setConditionType(MediPalConstants.ALLERGIC);
        }
        return healthBio.getConditionType();
    }

    public void setRadioButton(String conditionType){
        if (diagonised == null) {
            diagonised = (RadioButton) findViewById(R.id.diagonised);
        }

        if (allergic == null) {
            allergic = (RadioButton) findViewById(R.id.Allergic);
        }

        if (conditionType.equals("C")){
            diagonised.setChecked(true);
            allergic.setChecked(false);
        } else {
            diagonised.setChecked(false);
            allergic.setChecked(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_health_bio_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.healthbiodetail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTxtHealthBioStartDate().setOnClickListener(this);

        initfromActivity();
        initBtnSave();
        initBtnDelete();

    }

    @Override
    public void onClick(View v) {
        if (v == txtHealthBioStartDate) {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            selectedDate = calendar;
                            txtHealthBioStartDate.setText(dateFormatter.format(calendar.getTime()));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private void initfromActivity(){
        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentRecord = getHealthBio(parameters);
            bindValues(currentRecord);
        } else {
            currentRecord = new HealthBio();
            isNewRecord = true;
        }
    }

    private void initBtnDelete(){
        if (isNewRecord) {
            getDeleteButton().setVisibility(View.INVISIBLE);
        } else {
            getDeleteButton().setVisibility(View.VISIBLE);

            getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteHealthBio(v);
                }
            });
        }
    }

    private void initBtnSave() {
        getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHealthBio(v);
            }
        });
    }

    private void bindValues(HealthBio person) {
        getTxtHealthBioCondition().setText(person.getCondition());
        getTxtHealthBioStartDate().setText(MediPalUtils.toString_dd_MMM_yyyy(person.getStartDate()));
        setRadioButton(person.getConditionType());
    }

    private void saveHealthBio(View v) {
        if (isNewRecord) {
            createHealthBio(v);
        } else {
            updateHealthBio(v);
        }
    }

    private void createHealthBio(View v) {
        CreateCommand command;
        boolean isValid;

        currentRecord = new HealthBio();
        setHealthBioValues(currentRecord);

        isValid = checkHealthBio(currentRecord);

        if (isValid) {
            command = new CreateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Save successful.", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    private void updateHealthBio(View v){
        UpdateCommand command;
        boolean isValid;

        setHealthBioValues(currentRecord);

        isValid = checkHealthBio(currentRecord);

        if (isValid) {
            command = new UpdateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Save successful.", Toast.LENGTH_SHORT).show();
            //finish();
            Intent intent = new Intent(HealthBioDetailActivity.this,HealthBioListActivity.class);
            startActivity(intent);
        }
    }

    private void deleteHealthBio(View v){
        DeleteCommand command = new DeleteCommand(v.getContext());
        command.execute(currentRecord);
        Toast.makeText(this, "Delete successful.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setHealthBioValues(HealthBio healthBio) {
        healthBio.setCondition(getHealthBioCondition());
        Calendar selHealthStartDate = Calendar.getInstance();
        selHealthStartDate.set(Calendar.YEAR, selectedDate.get(Calendar.YEAR));
        selHealthStartDate.set(Calendar.MONTH, selectedDate.get(Calendar.MONTH));
        selHealthStartDate.set(Calendar.DATE, selectedDate.get(Calendar.DATE));
        healthBio.setStartDate(getHealthBioStartDate());
        healthBio.setConditionType(getRadioButton());
    }

    private boolean checkHealthBio(HealthBio healthBio) {
        boolean isValid = true;

        if (!healthBio.isValidCondition()) {
            getTxtHealthBioCondition().setError("Condition is required.  It should be between 1 to 50 characters.");
            isValid = false;
        }

        return isValid;
    }

    private HealthBio getHealthBio(Bundle parameters) {
        HealthBio h = null;

        if (parameters == null) {
            return new HealthBio();
        }

        int healthID = parameters.getInt(HealthBioFragment.ARG_HEALTHBIO_ID);
        FindOneQuery query = new FindOneQuery(HealthBioDetailActivity.this, healthID);
        try {
            query.execute();
            h = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return h;
    }
}
