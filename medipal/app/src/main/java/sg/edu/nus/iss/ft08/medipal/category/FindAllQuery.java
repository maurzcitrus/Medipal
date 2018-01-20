package sg.edu.nus.iss.ft08.medipal.category;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.core.Category;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;

import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_CODE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_DESCRIPTION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.CATEGORY_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.CategoryTable.TABLE_NAME;


public class FindAllQuery extends BaseAllQuery<Category> {

    public FindAllQuery(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<Category> executeQuery() {

        ArrayList<Category> records = new ArrayList<Category>();

        String[] columns = new String []{ID, CATEGORY_NAME};

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while(c.moveToNext()){
            int i =0;

            Category cat = new Category();
            cat.setId(c.getInt(i));
            cat.setName(c.getString(++i));

            records.add(cat);
        }
        return records;
    }
}
