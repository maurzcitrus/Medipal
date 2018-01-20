package sg.edu.nus.iss.ft08.medipal.category;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Category;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_CODE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_DESCRIPTION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_REMINDER_FLAG;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.TABLE_NAME;


public class UpdateCommand extends BaseCommand<Category> {
    public UpdateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Category... params) {
        Category category = params [0];

        String whereClause = "ID = ?";
        String [] whereArgs = new String [] {
                Integer.toString(category.getId())
        };

        ContentValues contentValues = new ContentValues();

        contentValues.put(CATEGORY_NAME, category.getName());
        contentValues.put(CATEGORY_CODE, category.getCode());
        contentValues.put(CATEGORY_DESCRIPTION, category.getDescription());
        contentValues.put(CATEGORY_REMINDER_FLAG, category.getReminderFlag());


        int rowsUpdated = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;

    }
}
