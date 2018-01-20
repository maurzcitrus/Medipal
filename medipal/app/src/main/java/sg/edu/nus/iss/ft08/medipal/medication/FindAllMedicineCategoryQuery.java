package sg.edu.nus.iss.ft08.medipal.medication;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;
import sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable;

public class FindAllMedicineCategoryQuery extends BaseAllQuery<MedicineCategory> {

    public FindAllMedicineCategoryQuery(Context context){
        super(context);
    }

    @Override
    protected ArrayList<MedicineCategory> executeQuery() {
        ArrayList<MedicineCategory> categories = new ArrayList<>();

        String[] columns = new String[]{
                CategoryTable.ID,
                CategoryTable.CATEGORY_CODE,
                CategoryTable.CATEGORY_NAME,
                CategoryTable.CATEGORY_REMINDER_FLAG
        };

        String orderBy = " CATEGORY_NAME ASC ";

        Cursor c = db.query(CategoryTable.TABLE_NAME, columns, null, null, null, null, orderBy);

        while(c.moveToNext()){
            int i = 0;

            MedicineCategory category = new MedicineCategory();
            category.setCategoryId(c.getInt(i));
            category.setCategoryCode(c.getString(++i));
            category.setCategoryName(c.getString(++i));
            category.setCategoryReminderFlag(c.getString(++i));

            categories.add(category);
        }

        return categories;
    }
}
