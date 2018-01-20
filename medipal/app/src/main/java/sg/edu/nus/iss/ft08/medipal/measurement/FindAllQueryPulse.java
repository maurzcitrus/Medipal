package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;
import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_PULSE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class FindAllQueryPulse extends BaseAllQuery<Pulse> {


    public FindAllQueryPulse(Context context) {
        super(context);
    }


    protected ArrayList<Pulse> executeQuery() {
        ArrayList<Pulse> records = new ArrayList<Pulse>();

        String[] columns = new String[]{
                ID,
                MEASUREMENT_PULSE,
                MEASUREMENT_MEASURED_ON
        };

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            Pulse p = new Pulse();
            p.setMeasurementId(c.getInt(i));
            p.setPulse(c.getInt(++i));
            p.setMeasuredOn(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));

            records.add(p);
        }
        return records;
    }
}
