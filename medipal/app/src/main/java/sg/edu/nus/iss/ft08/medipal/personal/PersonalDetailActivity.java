package sg.edu.nus.iss.ft08.medipal.personal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.security.AuthProvider;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.MainActivity;
import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.MediPalConstants;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Person;
import sg.edu.nus.iss.ft08.medipal.personal.FindOneQuery;
import sg.edu.nus.iss.ft08.medipal.reminder.ReminderService;
import sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable;

public class PersonalDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isFirstTime;
    private boolean isNewRecord;
    private Person currentRecord;
    private Toolbar personToolbar;
    private FloatingActionButton btnSave;
    private AutoCompleteTextView txtPersonName;
    private AutoCompleteTextView txtPersonDOB;
    private AutoCompleteTextView txtPersonIDNO;
    private AutoCompleteTextView txtPersonAddress;
    private AutoCompleteTextView txtPersonPostalCode;
    private AutoCompleteTextView txtPersonHeight;
    private AutoCompleteTextView txtPersonBloodType;
    private int mYear, mMonth, mDay;

    Calendar selectedDate = Calendar.getInstance();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    private AutoCompleteTextView getTxtPersonName() {
        if (txtPersonName == null) {
            txtPersonName = (AutoCompleteTextView) findViewById(R.id.person_name_detail);
        }
        return txtPersonName;
    }

    private String getPersonName() {
        return getTxtPersonName()
                .getText()
                .toString();
    }

    private AutoCompleteTextView getTxtPersonIDNO() {
        if (txtPersonIDNO == null) {
            txtPersonIDNO = (AutoCompleteTextView) findViewById(R.id.person__idno);
        }
        return txtPersonIDNO;
    }

    private String getPersonIDNO() {
        return getTxtPersonIDNO()
                .getText()
                .toString();
    }

    private AutoCompleteTextView getTxtPersonDOB() {
        if (txtPersonDOB == null) {
            txtPersonDOB = (AutoCompleteTextView) findViewById(R.id.person__dob);
        }
        return txtPersonDOB;
    }

    private Date getPersonDob() {
        String formattedString = getTxtPersonDOB()
                .getText()
                .toString();

        return MediPalUtils.toDate_from_dd_MMM_yyyy(formattedString);
    }

    private AutoCompleteTextView gettxtPersonAddress() {
        if (txtPersonAddress == null) {
            txtPersonAddress = (AutoCompleteTextView) findViewById(R.id.person__address);
        }
        return txtPersonAddress;
    }

    private String getPersonAddress() {
        return gettxtPersonAddress()
                .getText()
                .toString();
    }

    private AutoCompleteTextView getTxtPersonPostalCode() {
        if (txtPersonPostalCode == null) {
            txtPersonPostalCode = (AutoCompleteTextView) findViewById(R.id.person_postalcode);
        }
        return txtPersonPostalCode;
    }

    private String getPersonPostalCode() {
        return getTxtPersonPostalCode()
                .getText()
                .toString();
    }

    private AutoCompleteTextView getTxtPersonHeight() {
        if (txtPersonHeight == null) {
            txtPersonHeight = (AutoCompleteTextView) findViewById(R.id.person_height);
        }
        return txtPersonHeight;
    }

    private int getPersonHeight() {
        return MediPalUtils.toInt(getTxtPersonHeight().getText().toString());
    }

    private AutoCompleteTextView getTxtPersonBloodType() {
        if (txtPersonBloodType == null) {
            txtPersonBloodType = (AutoCompleteTextView) findViewById(R.id.person_bloodtype);
        }
        return txtPersonBloodType;
    }

    private String getPersonBloodType() {
        return getTxtPersonBloodType()
                .getText()
                .toString();
    }

    private FloatingActionButton getSaveButton() {
        if (btnSave == null) {
            btnSave = (FloatingActionButton) findViewById(R.id.person_save);
        }
        return btnSave;
    }


    private Toolbar getToolbar() {
        if (personToolbar == null) {
            personToolbar = (Toolbar) findViewById(R.id.person_toolbar);
        }
        return personToolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkIsFirstTimeFromIntent();

        setContentView(R.layout.activity_personal_detail);
        setSupportActionBar(getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTxtPersonDOB().setOnClickListener(this);

        currentRecord = findPerson();
        if (currentRecord != null) {
            initfromActivity(currentRecord);
            initupdate();
        } else {
            initcreate();
        }
    }

    private void initupdate() {
        getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePerson(v);
            }
        });
    }

    private void initcreate() {
        currentRecord = new Person();
        getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPerson(v);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == txtPersonDOB) {

            // Get Current Date
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
                            txtPersonDOB.setText(dateFormatter.format(calendar.getTime()));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private Person findPerson() {
        Person p = null;
        FindOneQuery query = null;

        query = new FindOneQuery(this);
        try {
            query.execute();
            p = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return p;

    }

    private void initfromActivity(Person p) {
        bindValues(p);
    }

    private void bindValues(Person person) {
        getTxtPersonName().setText(person.getName());
        getTxtPersonDOB().setText(MediPalUtils.toString_dd_MMM_yyyy(person.getDateOfBirth()));
        getTxtPersonIDNO().setText(person.getPersonIdNo());
        gettxtPersonAddress().setText(person.getAddress());
        getTxtPersonPostalCode().setText(person.getPostalCode());
        getTxtPersonHeight().setText(Integer.toString(person.getHeight()));
        getTxtPersonBloodType().setText(person.getBloodType());
    }

    private void createPerson(View v) {
        Person person;
        CreateCommand command;
        boolean isValid;

        currentRecord = new Person();
        setPersonValues(currentRecord);

        isValid = checkPerson(currentRecord);

        if (isValid) {
            command = new CreateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Save successful.", Toast.LENGTH_SHORT).show();
            finish();
            if (isFirstTime) {
                gotoMainActivity();
            }
        }
    }

    private void updatePerson(View v) {
        UpdateCommand command;
        boolean isValid;

        setPersonValues(currentRecord);

        isValid = checkPerson(currentRecord);

        if (isValid) {
            command = new UpdateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Update successful.", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    private void setPersonValues(Person person) {
        person.setName(getPersonName());
        Calendar selPersonDate = Calendar.getInstance();
        selPersonDate.set(Calendar.YEAR, selectedDate.get(Calendar.YEAR));
        selPersonDate.set(Calendar.MONTH, selectedDate.get(Calendar.MONTH));
        selPersonDate.set(Calendar.DATE, selectedDate.get(Calendar.DATE));
        person.setDateOfBirth(selPersonDate.getTime());
        person.setPersonIdNo(getPersonIDNO());
        person.setAddress(getPersonAddress());
        person.setPostalCode(getPersonPostalCode());
        person.setHeight(getPersonHeight());
        person.setBloodType(getPersonBloodType());
    }

    private boolean checkPerson(Person person) {
        boolean isValid = true;

        if (!person.isValidName()) {
            getTxtPersonName().setError("Person name is required.  It should be between 1 to 50 characters.");
            isValid = false;
        }

        if (!person.isValidDob()) {
            getTxtPersonDOB().setError("DOB is required");
            isValid = false;
        }

        if(!person.isValidPostalCode()){
            getTxtPersonPostalCode().setError("Postal Code is required");
            isValid = false;
        }

        return isValid;
    }


    private void gotoMainActivity() {
        Intent i = new Intent(PersonalDetailActivity.this, MainActivity.class);
        startActivity(i);
    }


    private void checkIsFirstTimeFromIntent() {
        Bundle parameters = getIntent().getExtras();
        if (parameters != null && parameters.containsKey(MediPalConstants.ARG_IS_FIRST_TIME)) {
            isFirstTime = parameters.getBoolean(MediPalConstants.ARG_IS_FIRST_TIME);
        } else {
            isFirstTime = false;
        }
    }

}
