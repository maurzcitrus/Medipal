package sg.edu.nus.iss.ft08.medipal.category;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.core.Category;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_CODE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_DESCRIPTION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_REMINDER_FLAG;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.TABLE_NAME;


public class FindOneQuery extends BaseOneQuery<Category> {

    private int categoryID;

    public FindOneQuery(Context context, int categoryID) {
        super(context);
        this.categoryID = categoryID;
    }

    @Override
    protected Category executeQuery() {

        Category record = null;

        String [] columns = new String[]{ID, CATEGORY_CODE, CATEGORY_NAME, CATEGORY_DESCRIPTION, CATEGORY_REMINDER_FLAG};

        String selection = "ID = ?";

        String [] selectionArgs = new String[] {Integer.toString(categoryID)};

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()){
            int i =0;
            record = new Category();
            record.setId(categoryID);
            record.setCode(c.getString(++i));
            record.setName(c.getString(++i));
            record.setDescription(c.getString(++i));
            record.setReminderFlag(c.getString(++i));                                           //only first row will get (latest record)
        }
        return record;
    }
}
