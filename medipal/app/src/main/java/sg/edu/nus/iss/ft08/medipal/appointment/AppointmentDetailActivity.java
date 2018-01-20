package sg.edu.nus.iss.ft08.medipal.appointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Appointment;
import sg.edu.nus.iss.ft08.medipal.core.MediPalConstants;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.reminder.ReminderServiceManager;

public class AppointmentDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Switch remindme;
    private boolean isNewRecord;
    private Appointment currentRecord;
    private FloatingActionButton btnSave;
    private FloatingActionButton btnDelete;
    private AutoCompleteTextView txtAppointmentDate;
    private AutoCompleteTextView txtAppointmentTime;
    private AutoCompleteTextView txtAppointmentLocation;
    private AutoCompleteTextView txtAppointmentDescription;
    private int mYear, mMonth, mDay, mHour, mMinute;


    Calendar selectedDate = Calendar.getInstance();
    Calendar selectedTime = Calendar.getInstance();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    private Toolbar getToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.appointmenttoolbar);
        }
        return toolbar;
    }

    private String setSwitchReminderFlag() {

        if (remindme == null) {
            remindme = (Switch) findViewById(R.id.remindme);
        }

        boolean reminderValue = remindme.isChecked();
        Appointment appointment = new Appointment();


        if (reminderValue) {
            appointment.setReminderFlag(MediPalConstants.FLAG_YES);
        }
        else {
            appointment.setReminderFlag(MediPalConstants.FLAG_NO);
        }
        return appointment.getReminderFlag();
    }

    private FloatingActionButton getSaveButton() {
        if (btnSave == null) {
            btnSave = (FloatingActionButton) findViewById(R.id.appointmentbtn_save);
        }
        return btnSave;
    }

    private FloatingActionButton getDeleteButton() {
        if (btnDelete == null) {
            btnDelete = (FloatingActionButton) findViewById(R.id.appointmentbtn_delete);
        }
        return btnDelete;
    }

    private AutoCompleteTextView gettxtAppintmentDate(){
        if (txtAppointmentDate==null){
            txtAppointmentDate = (AutoCompleteTextView) findViewById(R.id.txt_appointmentdate);
        }
        return txtAppointmentDate;
    }

    private AutoCompleteTextView gettxtAppointmentTime(){
        if (txtAppointmentTime==null){
            txtAppointmentTime = (AutoCompleteTextView) findViewById(R.id.txt_appointmenttime);
        }
        return txtAppointmentTime;
    }

    private AutoCompleteTextView getTxtAppointmentLocation() {
        if (txtAppointmentLocation == null) {
            txtAppointmentLocation = (AutoCompleteTextView) findViewById(R.id.location);
        }
        return txtAppointmentLocation;
    }
    private String getAppointmentLocation() {
        return getTxtAppointmentLocation()
                .getText()
                .toString();
    }

    private AutoCompleteTextView getTxtAppointmentDescription() {
        if (txtAppointmentDescription == null) {
            txtAppointmentDescription = (AutoCompleteTextView) findViewById(R.id.description);
        }
        return txtAppointmentDescription;
    }
    private String getAppointmentDescription() {
        return getTxtAppointmentDescription()
                .getText()
                .toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appointment_detail);
        setSupportActionBar(getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gettxtAppintmentDate().setOnClickListener(this);
        gettxtAppointmentTime().setOnClickListener(this);

        initFromIntent();
        initBtnSave();
        initBtnDelete();


    }

    @Override
    public void onClick(View v) {
        if (v == txtAppointmentDate) {

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
                            txtAppointmentDate.setText(dateFormatter.format(calendar.getTime()));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == txtAppointmentTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            selectedTime = calendar;
                            txtAppointmentTime.setText(timeFormatter.format(calendar.getTime()));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    private void initFromIntent() {
        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentRecord = getAppointment(parameters);
            bindValues(currentRecord);
        } else {
            currentRecord = new Appointment();
            isNewRecord = true;
        }
    }

    private void bindValues(Appointment appointment) {
        getTxtAppointmentLocation().setText(appointment.getLocation());
        gettxtAppintmentDate().setText(MediPalUtils.toString_dd_MMM_yyyy(appointment.getAppointmentDateTime()));
        gettxtAppointmentTime().setText(MediPalUtils.toString_hh_mm_a(appointment.getAppointmentDateTime()));
        getTxtAppointmentDescription().setText(appointment.getDescription());

        remindme = (Switch) findViewById(R.id.remindme);
        if(appointment.getReminderFlag().equals( MediPalConstants.FLAG_YES)){
            remindme.setChecked(true);
        }else{
            remindme.setChecked(false);
        }
    }


    private void initBtnDelete() {
        getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderServiceManager.cancelAppointmentReminder(AppointmentDetailActivity.this,
                        currentRecord);
                DeleteCommand command = new DeleteCommand(v.getContext());
                command.execute(currentRecord);
                finish();
            }
        });
    }

    private void initBtnSave() {

        getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              saveAppointment(v);
            }
        });
    }

    private void saveAppointment(View v) {
        if (isNewRecord) {
            createAppointment(v);
        } else {
            updateAppointment(v);
        }
    }

    private void createAppointment(View v){
        CreateCommand command;
        boolean isValid;
        long remindAppointmentId = 0;

        currentRecord = new Appointment();
        setAppointmentValues(currentRecord);

        isValid = checkAppointment(currentRecord);

        if (isValid) {
            command = new CreateCommand(v.getContext());
            command.execute(currentRecord);
            try {
                remindAppointmentId = command.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            currentRecord.setId((int) remindAppointmentId);
            if (currentRecord.isReminderEnabled()) {
                ReminderServiceManager.startAppointmentReminder(this, currentRecord);
            }
            Toast.makeText(this, "Save successful.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void updateAppointment(View v){
        UpdateCommand command;
        boolean isValid;

       // currentRecord = new Appointment();
        setAppointmentValues(currentRecord);

        Bundle b =  new Bundle();
        b.putInt(AppointmentFragment.ARG_APPOINTMENT_ID, currentRecord.getId());
        Appointment oldAppointment = getAppointment(b);

        isValid = checkAppointment(currentRecord);

        if (isValid) {
            ReminderServiceManager.cancelAppointmentReminder(this, oldAppointment);
            command = new UpdateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Update successful.", Toast.LENGTH_SHORT).show();
            if (currentRecord.isReminderEnabled()) {
                ReminderServiceManager.startAppointmentReminder(this, currentRecord);
            }
            finish();
        }
    }

    private void setAppointmentValues(Appointment appointment){
        appointment.setLocation(getAppointmentLocation());
        Calendar selAppointmentDate = Calendar.getInstance();
        selAppointmentDate.set(Calendar.YEAR, selectedDate.get(Calendar.YEAR));
        selAppointmentDate.set(Calendar.MONTH, selectedDate.get(Calendar.MONTH));
        selAppointmentDate.set(Calendar.DATE, selectedDate.get(Calendar.DATE));
        selAppointmentDate.set(Calendar.HOUR, selectedTime.get(Calendar.HOUR));
        selAppointmentDate.set(Calendar.MINUTE, selectedTime.get(Calendar.MINUTE));
        selAppointmentDate.set(Calendar.AM_PM, selectedTime.get(Calendar.AM_PM));
        appointment.setAppointmentDateTime(selAppointmentDate.getTime());
        appointment.setDescription(getAppointmentDescription());
        appointment.setReminderFlag(setSwitchReminderFlag());
    }

    private boolean checkAppointment(Appointment appointment) {
        boolean isValid = true;

        if (!appointment.isValidLocation()) {
            getTxtAppointmentLocation().setError("Location is required.  It should be between 1 to 50 characters.");
            isValid = false;
        }

        if (appointment.getDescription() != null) {
            if (!appointment.isValidDescription()) {
                getTxtAppointmentDescription().setError("Description should be less than 255 characters");
                isValid = false;
            }
        }


        return isValid;
    }

    private Appointment getAppointment(Bundle parameters) {
        Appointment a = null;

        if (parameters == null) {
            return new Appointment();
        }

        int appointmentId = parameters.getInt(AppointmentFragment.ARG_APPOINTMENT_ID);
        FindOneQuery query = new FindOneQuery(AppointmentDetailActivity.this, appointmentId);
        try {
            query.execute();
            a = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return a;
    }
}
