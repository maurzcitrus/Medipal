package sg.edu.nus.iss.ft08.medipal.category;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Category;
import sg.edu.nus.iss.ft08.medipal.core.MediPalConstants;

import static sg.edu.nus.iss.ft08.medipal.R.id.category_name;

public class CategoryDetailActivity extends AppCompatActivity {

    private FloatingActionButton btnSave;
    private boolean isNewRecord;
    private Category currentRecord;
    private AutoCompleteTextView txtCategoryName, txtCategoryCode, txtCategoryDescription;
    private Switch setReminderFlag;

    private FloatingActionButton getSaveButton() {
        if (btnSave == null) {
            btnSave = (FloatingActionButton) findViewById(R.id.btn_save);
        }
        return btnSave;
    }

    private AutoCompleteTextView getTxtCategoryName() {
        if (txtCategoryName == null) {
            txtCategoryName = (AutoCompleteTextView) findViewById(R.id.category_name);
        }
        return txtCategoryName;
    }

    private AutoCompleteTextView getTxtCategoryCode() {
        if (txtCategoryCode == null) {
            txtCategoryCode = (AutoCompleteTextView) findViewById(R.id.category_code);
        }
        return txtCategoryCode;
    }

    private AutoCompleteTextView getTxtCategoryDescription() {
        if (txtCategoryDescription == null) {
            txtCategoryDescription = (AutoCompleteTextView) findViewById(R.id.category_description);
        }
        return txtCategoryDescription;
    }

    private String getCategoryName() {
        return getTxtCategoryName()
                .getText()
                .toString();
    }

    private String getCategoryCode() {
        return getTxtCategoryCode()
                .getText()
                .toString();
    }

    private String getCategoryDescription() {
        return getTxtCategoryDescription()
                .getText()
                .toString();
    }

    public String getSwchReminderFlag() {

        if (setReminderFlag == null) {
            setReminderFlag = (Switch) findViewById(R.id.set_reminder_category);
        }

        boolean reminderValue = setReminderFlag.isChecked();
        Category c = new Category();

        if (reminderValue) {
            c.setReminderFlag(MediPalConstants.FLAG_YES);
        }
        else {
            c.setReminderFlag(MediPalConstants.FLAG_NO);
        }
        return c.getReminderFlag();
    }

    public void setReminderFlagOnBind(String reminderFlag){
        setReminderFlag = (Switch) findViewById(R.id.set_reminder_category);
        if (reminderFlag.equals(MediPalConstants.FLAG_YES)){
            setReminderFlag.setChecked(true);
        } else {
            setReminderFlag.setChecked(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initBtnSave(); // method to perform update action after clicking on save button

        Bundle parameters = getIntent().getExtras();
        if (parameters != null) {
            isNewRecord = false;
            currentRecord = getCategory(parameters);
            bindValues(currentRecord);
        } else {
            currentRecord = new Category();
            isNewRecord = true;
        }
    }

    private void bindValues(Category m) {
        getTxtCategoryName().setText(m.getName());
        getTxtCategoryCode().setText(m.getCode());
        getTxtCategoryDescription().setText(m.getDescription());
        setReminderFlagOnBind(m.getReminderFlag());
    }

    private void initBtnSave() {
        getSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategory(v);
            }
        });
    }

    private void saveCategory(View v) {
        if (isNewRecord) {
            createCategory(v);
        } else {
            updateCategory(v);
        }
    }

    private void updateCategory(View v){
        UpdateCommand command;
        boolean isValid;

        setCategoryValues(currentRecord);

        isValid = checkCategory(currentRecord);
        if(isValid){
            command = new UpdateCommand(v.getContext());
            command.execute(currentRecord);

            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void createCategory(View v) {
        CreateCommand command;

        boolean isValid;

        currentRecord = new Category();

        setCategoryValues(currentRecord);

        isValid = checkCategory(currentRecord);

        if (isValid) {
            command = new CreateCommand(v.getContext());
            command.execute(currentRecord);
            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setCategoryValues(Category category){
        category.setCode(getCategoryCode());
        category.setName(getCategoryName());
        category.setDescription(getCategoryDescription());
        category.setReminderFlag(getSwchReminderFlag());
    }

    private Category getCategory(Bundle parameters) {
        Category category = null;

        if (parameters == null) {
            return new Category();
        }

        int categoryId = parameters.getInt(CategoryDetailFragment.ARG_CATEGORY_ID);
        FindOneQuery query = new FindOneQuery(CategoryDetailActivity.this, categoryId);
        try {
            query.execute();
            category = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return category;
    }

    private boolean checkCategory(Category category) {
        boolean isValid = true;

        if (!category.isValidCode()) {
            getTxtCategoryCode().setError("Category code is required.  It should be between 1 to 4 characters.");
            isValid = false;
        }

        if (!category.isValidName()) {
            getTxtCategoryName().setError("Category name is required.  It should be between 1 to 50 characters.");
            isValid = false;
        }

        if (!category.isValidDescription()) {
            getTxtCategoryDescription().setError("Description should be between 1 to 255 characters.");
            isValid = false;
        }

        if (isNewRecord && hasDuplicate(category.getCode())) {

            getTxtCategoryCode().setError("Category Code already exists.");
            isValid = false;

        }

        return isValid;
    }

    private boolean hasDuplicate(String categrycode) {
        Category cat = null;

        if (categrycode == null || categrycode.isEmpty())
            return false;

        FindOneQuery query = new FindOneQuery(CategoryDetailActivity.this, category_name);
        try {
            query.execute();
            cat = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (cat != null)
            return true;
        else
            return false;
    }
}
