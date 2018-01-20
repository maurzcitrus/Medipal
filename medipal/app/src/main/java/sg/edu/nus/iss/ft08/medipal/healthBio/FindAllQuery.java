package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.core.HealthBio;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.HEALTH_BIO_CONDITION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.HEALTH_BIO_CONDITION_TYPE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.HEALTH_BIO_START_DATE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.TABLE_NAME;

/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class FindAllQuery extends BaseAllQuery<HealthBio> {
    public FindAllQuery(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<HealthBio> executeQuery() {
        ArrayList<HealthBio> records = new ArrayList<HealthBio>();

        String[] columns = new String[]{
                ID,
                HEALTH_BIO_CONDITION,
                HEALTH_BIO_START_DATE,
                HEALTH_BIO_CONDITION_TYPE
        };

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            HealthBio h = new HealthBio();
            h.setId(c.getInt(i));
            h.setCondition(c.getString(++i));
            h.setStartDate(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));
            h.setConditionType(c.getString(++i));
            records.add(h);
        }

        return records;
    }
    }

