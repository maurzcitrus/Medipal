package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.core.HealthBio;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.HEALTH_BIO_CONDITION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.HEALTH_BIO_CONDITION_TYPE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.HEALTH_BIO_START_DATE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.TABLE_NAME;

/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class FindOneQuery extends BaseOneQuery<HealthBio> {

    private int id;

    public FindOneQuery(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    protected HealthBio executeQuery() {
        HealthBio record = null;

        String[] columns = new String[]{
                ID,
                HEALTH_BIO_CONDITION,
                HEALTH_BIO_START_DATE,
                HEALTH_BIO_CONDITION_TYPE
        };

        String selection = "ID = ?";

        String[] selectionArgs = new String[]{
                Integer.toString(id)
        };

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            record = new HealthBio();
            record.setId(c.getInt(i));
            record.setCondition(c.getString(++i));
            record.setStartDate(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));
            record.setConditionType(c.getString(++i));

            // take only the first row
            break;
        }

        return record;
    }
}
