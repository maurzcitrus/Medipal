package sg.edu.nus.iss.ft08.medipal.emergency;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Arrays;
import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

/**
 * An activity representing a single EmergencyContact detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link EmergencyContactListActivity}.
 */
public class EmergencyContactDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton btnSave, btnDelete;
    private AutoCompleteTextView txtEmergencyName, txtContactNbr, txtDesc;
    private Spinner txtContactType, txtPriority;
    private boolean isNewRecord;
    private Context context;
    private EmergencyRecyclerViewAdapter emergencyAdapter;
    private EmergencyContact currentRecord;

    private static Integer[] intArr = {1, 2, 3, 4, 5};
    private static String[] strArr = {"Next of Kin", "General practicioner", "Friend",
            "Neighbour", "others"};

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

    private AutoCompleteTextView getTxtEmergencyName() {
        if (txtEmergencyName == null) {
            txtEmergencyName = (AutoCompleteTextView) findViewById(R.id.contact_name);
        }
        return txtEmergencyName;
    }

    private AutoCompleteTextView getTxtDesc() {
        if (txtDesc == null) {
            txtDesc = (AutoCompleteTextView) findViewById(R.id.desc);
        }
        return txtDesc;
    }

    private AutoCompleteTextView getTxtContactNbr() {
        if (txtContactNbr == null) {
            txtContactNbr = (AutoCompleteTextView) findViewById(R.id.contact_no);
        }
        return txtContactNbr;
    }


    private String getEmergencyName() {
        return getTxtEmergencyName()
                .getText()
                .toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencycontact_detail);
        setSupportActionBar(getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //context = this;
        txtEmergencyName = (AutoCompleteTextView) findViewById(R.id.contact_name);
        txtContactNbr = (AutoCompleteTextView) findViewById(R.id.contact_no);
        txtContactType = (Spinner) findViewById(R.id.spinner2);
        txtPriority = (Spinner) findViewById(R.id.spinner1);
        txtDesc = (AutoCompleteTextView) findViewById(R.id.desc);

        initfromActivity();
        initBtnSave();
        initBtnDelete();
    }

    private void initfromActivity() {
        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentRecord = getEmergencyContact(parameters);
            bindValues(currentRecord);
        } else {
            currentRecord = new EmergencyContact();
            isNewRecord = true;
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
                    deleteDetails(v);
                    finish();

                }
            });
        }
    }

    private void initBtnSave() {
        getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetail(v);
            }
        });
    }


    private void bindValues(EmergencyContact m) {
        txtEmergencyName.setText(m.getName());
        txtContactNbr.setText(m.getContactNo());
        txtDesc.setText(m.getDescription());
        txtContactType.setSelection(Arrays.asList(strArr).indexOf(m.getContactType()));
        txtPriority.setSelection(Arrays.asList(intArr).indexOf(m.getPriority()));

    }

    private void saveDetail(View v) {
        if (isNewRecord) {
            createEmergencyDetail(v);
        } else {
            updateEmergencyDetail(v);
        }
    }

    private void createEmergencyDetail(View v) {
        EmergencyContact emergencyContact;
        CreateCommand command;
        boolean isValid;

        currentRecord = new EmergencyContact();
        setEmergencyValues(currentRecord);


        isValid = checkEmergency(currentRecord);

        if (isValid) {
            command = new CreateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    private void updateEmergencyDetail(View v) {
        EmergencyContact emergencyContact;
        UpdateCommand command;
        boolean isValid;

        setEmergencyValues(currentRecord);

        isValid = checkEmergency(currentRecord);

        if (isValid) {
            command = new UpdateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    private void deleteDetails(View v) {
        DeleteCommand command = new DeleteCommand(v.getContext());
        command.execute(currentRecord);
        Toast.makeText(this, "Deleted successfully.", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void setEmergencyValues(EmergencyContact emergencycontact) {
        emergencycontact.setName(getEmergencyName());
        emergencycontact.setContactNo(txtContactNbr.getText().toString());
        emergencycontact.setDescription(txtDesc.getText().toString());
        emergencycontact.setContactType(txtContactType.getSelectedItem().toString());
        emergencycontact.setPriority(Integer.valueOf(txtPriority.getSelectedItem().toString().
                replaceAll("\\s", "")));

    }

    private boolean checkEmergency(EmergencyContact emergencyContact) {
        boolean isValid = true;

        if (!currentRecord.isValidName()) {
            getTxtEmergencyName().setError("Name is required. It should be between 1 to 20 characters");
            isValid = false;
        }

        if (!currentRecord.isValidDescription()) {
            getTxtDesc().setError("Description is mandatory");
            isValid = false;
        }
        if (!currentRecord.isValidContact()) {
            getTxtContactNbr().setError("Contact Number should be 8 digits");
            isValid = false;
        }

        return isValid;
    }

    private EmergencyContact getEmergencyContact(Bundle parameters) {
        EmergencyContact m = null;

        if (parameters == null) {
            return new EmergencyContact();
        }

        int emergencyId = parameters.getInt(EmergencyContactDetailFragment.ARG_EMERGENCY_ID);
        FindOneQuery query = new FindOneQuery(EmergencyContactDetailActivity.this, emergencyId);

        try {
            query.execute();
            m = query.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }
}
