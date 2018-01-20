package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_TEMPERATURE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class FindAllQueryTemperature extends BaseAllQuery<Temperature> {
    public FindAllQueryTemperature(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<Temperature> executeQuery() {

        ArrayList<Temperature> records = new ArrayList<Temperature>();

        String[] columns = new String[]{
                ID,
                MEASUREMENT_TEMPERATURE,
                MEASUREMENT_MEASURED_ON
        };

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            Temperature t = new Temperature();
            t.setMeasurementId(c.getInt(i));
            t.setTemperature(c.getInt(++i));
            t.setMeasuredOn(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));

            records.add(t);

        }

        return records;
    }
}
