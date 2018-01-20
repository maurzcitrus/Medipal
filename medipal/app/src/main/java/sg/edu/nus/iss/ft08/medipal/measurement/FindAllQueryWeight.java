package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_WEIGHT;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class FindAllQueryWeight extends BaseAllQuery<Weight> {
    public FindAllQueryWeight(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<Weight> executeQuery() {

        ArrayList<Weight> records = new ArrayList<Weight>();

        String[] columns = new String[]{
                ID,
                MEASUREMENT_WEIGHT,
                MEASUREMENT_MEASURED_ON

        };

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            Weight w = new Weight();
            w.setMeasurementId(c.getInt(i));
            w.setWeight(c.getInt(++i));
            w.setMeasuredOn(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));

            records.add(w);
        }

        return records;
    }
}
