package sg.edu.nus.iss.ft08.medipal.medication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.reminder.ReminderService;
import sg.edu.nus.iss.ft08.medipal.reminder.ReminderServiceManager;

public class MedicationDetailActivity
        extends AppCompatActivity {

    private boolean isNewRecord;
    private Medication currentRecord;
    private boolean isRemindToTakeEnabled;
    private boolean isRemindToTopupEnabled;
    private boolean isCategoryReminderEnabled;

    private Toolbar toolbar;
    private FloatingActionButton btnSave, btnDelete;
    private Spinner spnrCategory;
    private AutoCompleteTextView txtMedicineName;
    private AutoCompleteTextView txtMedicineDescription;
    private AutoCompleteTextView txtIssueDate;
    private AutoCompleteTextView txtExpiryFactor;
    private AutoCompleteTextView txtQuantity;
    private AutoCompleteTextView txtFrequency;
    private AutoCompleteTextView txtDosage;
    private Switch swchRemindToTake;
    private Switch swchRemindToTopup;
    private AutoCompleteTextView txtRemindToTakeStartTime;
    private AutoCompleteTextView txtRemindToTakeInterval;
    private AutoCompleteTextView txtRemindToTopupThreshold;

    private View pnlRemindToTake;
    private View pnlRemindToTopup;
    private View pnlAllReminder;

    private DatePickerDialog.OnDateSetListener issueDateSetListener;
    private View.OnClickListener issueDateActivator;

    private TimePickerDialog.OnTimeSetListener startTimeSetListener;
    private View.OnClickListener startTimeActivator;

    private CompoundButton.OnCheckedChangeListener remindToTakeSwitchListener;
    private CompoundButton.OnCheckedChangeListener remindToTopupSwitchListener;

    private AdapterView.OnItemSelectedListener categorySelectedListener;

    private Toolbar getToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        return toolbar;
    }

    private FloatingActionButton getSaveButton() {
        if (btnSave == null) {
            btnSave = (FloatingActionButton) findViewById(R.id.btn_save);
        }
        return btnSave;
    }

    private FloatingActionButton getDeleteButton() {
        if (btnDelete == null) {
            btnDelete = (FloatingActionButton) findViewById(R.id.btn_delete);
        }
        return btnDelete;
    }

    private Spinner getSpnrCategory() {
        if (spnrCategory == null) {
            spnrCategory = (Spinner) findViewById(R.id.nav_medicine_category);
        }
        return spnrCategory;
    }

    private AutoCompleteTextView getTxtMedicineName() {
        if (txtMedicineName == null) {
            txtMedicineName = (AutoCompleteTextView) findViewById(R.id.medicine_name);
        }
        return txtMedicineName;
    }

    private AutoCompleteTextView getTxtMedicineDescription() {
        if (txtMedicineDescription == null) {
            txtMedicineDescription = (AutoCompleteTextView) findViewById(R.id.medicine_description);
        }
        return txtMedicineDescription;
    }

    private AutoCompleteTextView getTxtIssueDate() {
        if (txtIssueDate == null) {
            txtIssueDate = (AutoCompleteTextView) findViewById(R.id.medicine_issue_date);
        }
        return txtIssueDate;
    }

    private AutoCompleteTextView getTxtExpiryFactor() {
        if (txtExpiryFactor == null) {
            txtExpiryFactor = (AutoCompleteTextView) findViewById(R.id.medicine_expiry_factor);
        }
        return txtExpiryFactor;
    }

    private AutoCompleteTextView getTxtQuantity() {
        if (txtQuantity == null) {
            txtQuantity = (AutoCompleteTextView) findViewById(R.id.medicine_quantity);
        }
        return txtQuantity;
    }

    private AutoCompleteTextView getTxtFrequency() {
        if (txtFrequency == null) {
            txtFrequency = (AutoCompleteTextView) findViewById(R.id.medicine_frequency);
        }
        return txtFrequency;
    }

    private AutoCompleteTextView getTxtDosage() {
        if (txtDosage == null) {
            txtDosage = (AutoCompleteTextView) findViewById(R.id.medicine_dosage);
        }
        return txtDosage;
    }

    public Switch getSwchRemindToTake() {
        if (swchRemindToTake == null) {
            swchRemindToTake = (Switch) findViewById(R.id.medicine_remind_to_take);
        }
        return swchRemindToTake;
    }

    public Switch getSwchRemindToTopup() {
        if (swchRemindToTopup == null) {
            swchRemindToTopup = (Switch) findViewById(R.id.medicine_remind_to_topup);
        }
        return swchRemindToTopup;
    }

    public AutoCompleteTextView getTxtRemindToTakeStartTime() {
        if (txtRemindToTakeStartTime == null) {
            txtRemindToTakeStartTime = (AutoCompleteTextView) findViewById(R.id.medicine_remind_to_take_start_time);
        }
        return txtRemindToTakeStartTime;
    }

    public AutoCompleteTextView getTxtRemindToTakeInterval() {
        if (txtRemindToTakeInterval == null) {
            txtRemindToTakeInterval = (AutoCompleteTextView) findViewById(R.id.medicine_remind_to_take_interval);
        }
        return txtRemindToTakeInterval;
    }

    public AutoCompleteTextView getTxtRemindToTopupThreshold() {
        if (txtRemindToTopupThreshold == null) {
            txtRemindToTopupThreshold = (AutoCompleteTextView) findViewById(R.id.medicine_remind_to_topup_threshold);
        }
        return txtRemindToTopupThreshold;
    }

    public View getPnlAllReminder() {
        if (pnlAllReminder == null) {
            pnlAllReminder = findViewById(R.id.medicine_all_reminder_panel);
        }
        return pnlAllReminder;
    }

    public View getPnlRemindToTake() {
        if (pnlRemindToTake == null) {
            pnlRemindToTake = findViewById(R.id.medicine_remind_to_take_panel);
        }
        return pnlRemindToTake;
    }

    public View getPnlRemindToTopup() {
        if (pnlRemindToTopup == null) {
            pnlRemindToTopup = findViewById(R.id.medicine_remind_to_topup_panel);
        }
        return pnlRemindToTopup;
    }

    private String getMedicineName() {
        return getTxtMedicineName()
                .getText()
                .toString();
    }

    private String getMedicineDescription() {
        return getTxtMedicineDescription()
                .getText()
                .toString();
    }

    private Date getMedicineIssueDate() {
        String formattedString = getTxtIssueDate()
                .getText()
                .toString();

        return MediPalUtils.toDate_from_dd_MMM_yyyy(formattedString);
    }

    private int getMedicineExpiryFactor() {
        return MediPalUtils.toInt(getTxtExpiryFactor().getText().toString());
    }

    private int getMedicineQuantity() {
        return MediPalUtils.toInt(getTxtQuantity().getText().toString());
    }

    private int getMedicineFrequency() {
        return MediPalUtils.toInt(getTxtFrequency().getText().toString());
    }

    private int getMedicineDosage() {
        return MediPalUtils.toInt(getTxtDosage().getText().toString());
    }

    private Date getMedicineRemindToTakeStartTime() {
        return MediPalUtils.toTime_from_hh_mm_a(getTxtRemindToTakeStartTime().getText().toString());
    }

    private int getMedicineRemindToTakeInterval() {
        return MediPalUtils.toInt(getTxtRemindToTakeInterval().getText().toString());
    }

    private int getMedicineRemindToTopupThreshold() {
        return MediPalUtils.toInt(getTxtRemindToTopupThreshold().getText().toString());
    }

    public int getMedicineCategoryId() {
        MedicineCategory selected = (MedicineCategory) getSpnrCategory().getSelectedItem();
        if (selected != null) {
            return selected.getCategoryId();
        } else {
            return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_medication_detail);
        setSupportActionBar(getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initSpnrCategory(); // important: spinner must initialized first so that dropdown list value can be set at selected item
        initFromIntent();
        initIssueDatePicker();
        initStartTimePicker();
        initRemindToTakeSwitch();
        initRemindToTopupSwitch();
        initBtnSave();
        initBtnDelete();

        showOrHideReminderPanel();
    }


    private void bindValues(Medication medication) {
        Medicine m = medication.getMedicine();
        List<MedicineCategory> categories = medication.getCategories();
        int selectedCategoryIndex = medication.getMedicineCategoryIndex();

        getTxtMedicineName().setText(m.getName());
        getTxtMedicineDescription().setText(m.getDescription());
        getTxtIssueDate().setText(MediPalUtils.toString_dd_MMM_yyyy(m.getDateIssued()));
        getTxtExpiryFactor().setText(Integer.toString(m.getExpiryFactor()));
        getTxtQuantity().setText(Integer.toString(m.getQuantity()));
        getTxtFrequency().setText(Integer.toString(m.getFrequency()));
        getTxtDosage().setText(Integer.toString(m.getDosage()));

        isRemindToTopupEnabled = (m.isTopupReminderEnabled());
        if (isRemindToTopupEnabled) {
            getTxtRemindToTopupThreshold().setText(Integer.toString(m.getThreshold()));
            getPnlRemindToTopup().setVisibility(View.VISIBLE);
            getSwchRemindToTopup().setChecked(true);
        } else {
            getTxtRemindToTopupThreshold().setText("");
            getPnlRemindToTopup().setVisibility(View.GONE);
            getSwchRemindToTopup().setChecked(false);
        }


        isRemindToTakeEnabled = m.isReminderEnabled();
        if (isRemindToTakeEnabled) {
            Reminder r = m.getReminder();
            getTxtRemindToTakeStartTime().setText(MediPalUtils.toString_hh_mm_a(r.getStartTime()));
            getTxtRemindToTakeInterval().setText(Integer.toString(r.getInterval()));

            getPnlRemindToTake().setVisibility(View.VISIBLE);
            getSwchRemindToTake().setChecked(true);
        } else {
            getTxtRemindToTakeStartTime().setText("");
            getTxtRemindToTakeInterval().setText("");

            getPnlRemindToTake().setVisibility(View.GONE);
            getSwchRemindToTake().setChecked(false);
        }

        getSpnrCategory().setSelection(selectedCategoryIndex);
    }

    private void setMedicineValues(Medicine medicine) {
        medicine.setName(getMedicineName());
        medicine.setDescription(getMedicineDescription());
        medicine.setDateIssued(getMedicineIssueDate());
        medicine.setExpiryFactor(getMedicineExpiryFactor());
        medicine.setQuantity(getMedicineQuantity());
        medicine.setFrequency(getMedicineFrequency());
        medicine.setDosage(getMedicineDosage());

        if (isCategoryReminderEnabled) {
            if (isRemindToTopupEnabled) {
                medicine.setThreshold(getMedicineRemindToTopupThreshold());
            } else {
                medicine.disableReminderToTopup();
            }

            if (isRemindToTakeEnabled) {
                medicine.enableReminderToTake(getMedicineRemindToTakeStartTime(),
                        getMedicineFrequency(),
                        getMedicineRemindToTakeInterval());
            } else {
                medicine.disableReminderToTake();
            }
        } else {
            medicine.disableReminderToTopup();
            medicine.disableReminderToTake();
        }


        medicine.setCategoryId(getMedicineCategoryId());

    }


    private void initFromIntent() {
        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentRecord = getMedication(parameters);
            bindValues(currentRecord);
        } else {
            currentRecord = new Medication();
            currentRecord.setCategories(getMedicineCategories());
            isNewRecord = true;
        }

    }

    private void initIssueDatePicker() {
        issueDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateValue = MediPalUtils.toString_dd_MMM_yyyy(year, month, dayOfMonth);
                getTxtIssueDate().setText(dateValue);
            }
        };

        issueDateActivator = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(MedicationDetailActivity.this, issueDateSetListener, year, month, dayOfMonth);
                d.show();
            }
        };

        getTxtIssueDate().setOnClickListener(issueDateActivator);
    }

    private void initStartTimePicker() {
        startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeValue = MediPalUtils.toString_hh_mm_a(hourOfDay, minute);
                getTxtRemindToTakeStartTime().setText(timeValue);
            }
        };

        startTimeActivator = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                TimePickerDialog d = new TimePickerDialog(MedicationDetailActivity.this, startTimeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                d.show();
            }
        };

        getTxtRemindToTakeStartTime().setOnClickListener(startTimeActivator);
    }

    private void initRemindToTopupSwitch() {
        remindToTopupSwitchListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isRemindToTopupEnabled = isChecked;

                View pnl = findViewById(R.id.medicine_remind_to_topup_panel);
                if (isChecked) {
                    pnl.setVisibility(View.VISIBLE);
                } else {
                    pnl.setVisibility(View.GONE);
                }

            }
        };

        getSwchRemindToTopup().setOnCheckedChangeListener(remindToTopupSwitchListener);

        if (isNewRecord) {
            getPnlRemindToTopup().setVisibility(View.GONE);
            getSwchRemindToTopup().setChecked(false);
        }
    }

    private void initRemindToTakeSwitch() {
        remindToTakeSwitchListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isRemindToTakeEnabled = isChecked;

                View pnl = findViewById(R.id.medicine_remind_to_take_panel);
                if (isChecked) {
                    pnl.setVisibility(View.VISIBLE);
                } else {
                    pnl.setVisibility(View.GONE);
                }
            }
        };

        getSwchRemindToTake().setOnCheckedChangeListener(remindToTakeSwitchListener);

        if (isNewRecord) {
            getPnlRemindToTake().setVisibility(View.GONE);
            getSwchRemindToTake().setChecked(false);
        }
    }

    private void initBtnDelete() {
        if (isNewRecord) {
            getDeleteButton().setVisibility(View.INVISIBLE);
        } else {
            getDeleteButton().setVisibility(View.VISIBLE);

            getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMedication(v);
                }
            });
        }
    }

    private void initBtnSave() {
        getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedication(v);
            }
        });
    }

    private void initSpnrCategory() {

        SpinnerAdapter categoryAdapter = new ArrayAdapter<MedicineCategory>(this,
                R.layout.support_simple_spinner_dropdown_item,
                getMedicineCategories());

        getSpnrCategory().setAdapter(categoryAdapter);

        categorySelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showOrHideReminderPanel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        };

        spnrCategory.setOnItemSelectedListener(categorySelectedListener);
    }

    private void showOrHideReminderPanel() {
        MedicineCategory c = (MedicineCategory) getSpnrCategory().getSelectedItem();
        if(c == null){
            isCategoryReminderEnabled = false;
            return;
        }

        if (c.isReminderEnabled()) {
            getPnlAllReminder().setVisibility(View.VISIBLE);
            isCategoryReminderEnabled = true;
        } else {
            getPnlAllReminder().setVisibility(View.GONE);
            isCategoryReminderEnabled = false;
        }
    }


    private List<MedicineCategory> getMedicineCategories() {
        FindAllMedicineCategoryQuery query = new FindAllMedicineCategoryQuery(this);
        List<MedicineCategory> categories = new ArrayList<>();

        try {
            query.execute();
            categories = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // todo: to remove when category module is ready
        if (categories.isEmpty()) {
            MedicineCategory d1 = new MedicineCategory();
            d1.setCategoryId(9001);
            d1.setCategoryName("dummy 1");
            d1.enableReminder();
            categories.add(d1);

            MedicineCategory d2 = new MedicineCategory();
            d2.setCategoryId(9002);
            d2.setCategoryName("dummy 2");
            d2.disableReminder();
            categories.add(d2);

        }

        // add dummy 'Choose Category' text
        MedicineCategory selectOne = new MedicineCategory();
        selectOne.setCategoryId(-1);
        selectOne.setCategoryName("Choose Category");

        categories.add(0, selectOne);

        return categories;

    }

    private Medication getMedication(Bundle parameters) {
        Medication m = null;
        FindOneQuery query = null;

        if (parameters == null) {
            return new Medication();
        }

        int medicineId = parameters.getInt(MedicationFragment.ARG_MEDICINE_ID);
        query = new FindOneQuery(this, medicineId);
        try {
            query.execute();
            m = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        m.setCategories(getMedicineCategories());

        return m;
    }

    private void deleteMedication(View v) {
        ReminderServiceManager.cancelConsumptionReminder(this, currentRecord.getMedicine());
        DeleteCommand command = new DeleteCommand(v.getContext());
        command.execute(currentRecord);
        Toast.makeText(this, "Delete successful.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveMedication(View v) {
        if (isNewRecord) {
            createMedication(v);
        } else {
            updateMedication(v);
        }
    }

    private void updateMedication(View v) {
        Medicine medicine, oldMedicine;
        UpdateCommand command;
        boolean isValid;

        Bundle b = new Bundle();
        b.putInt(MedicationFragment.ARG_MEDICINE_ID, currentRecord.getMedicineId());
        oldMedicine = getMedication(b).getMedicine();

        medicine = currentRecord.getMedicine();
        setMedicineValues(medicine);

        isValid = checkMedicine(medicine);
        if (isValid) {
            ReminderServiceManager.cancelConsumptionReminder(this, oldMedicine);
            command = new UpdateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Update successful.", Toast.LENGTH_SHORT).show();
            ReminderServiceManager.startConsumptionReminder(this, medicine);
            finish();
        }
    }

    private void createMedication(View v) {
        Medicine medicine;
        CreateCommand command;
        boolean isValid;
        long remindMedicineId = 0;
        currentRecord = new Medication();

        medicine = currentRecord.getMedicine();
        setMedicineValues(medicine);

        isValid = checkMedicine(medicine);

        if (isValid) {
            command = new CreateCommand(v.getContext());
            command.execute(currentRecord);
            try {
                remindMedicineId = command.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Save successful.", Toast.LENGTH_SHORT).show();
            medicine.setId((int) remindMedicineId);
            ReminderServiceManager.startConsumptionReminder(this, medicine);
            finish();
        }
    }

    private boolean checkMedicine(Medicine medicine) {
        boolean isValid = true;

        if (!medicine.isValidName()) {
            getTxtMedicineName().setError("Medicine name is required.  It should be between 1 to 50 characters.");
            isValid = false;
        }

        if (!medicine.isValidDescription()) {
            getTxtMedicineDescription().setError("Description should be between 1 to 255 characters.");
            isValid = false;
        }

        if (!medicine.isValidRemindToTakeStartTime()) {
            getTxtRemindToTakeStartTime().setError("Start time is required.");
            isValid = false;
        }

        if (!medicine.isValidRemindToTakeFrequency()) {
            getTxtFrequency().setError("Frequency is required.  It should be at least 1.");
            isValid = false;
        }

        if (!medicine.isValidRemindToTakeInterval()) {
            getTxtRemindToTakeInterval().setError("Interval is required.  It should be at least 1.");
            isValid = false;
        }

        if (isNewRecord) {

            if (hasDuplicate(medicine.getName())) {
                getTxtMedicineName().setError("Medicine name already exists.");
                isValid = false;
            }

        }

        return isValid;
    }

    private boolean hasDuplicate(String medicineName) {
        Medication m = null;

        if (medicineName == null || medicineName.isEmpty())
            return false;

        FindOneQuery query = new FindOneQuery(MedicationDetailActivity.this, medicineName);
        try {
            query.execute();
            m = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (m != null)
            return true;
        else
            return false;
    }

}
