package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;
import android.icu.util.Measure;

import java.util.ArrayList;
import java.util.Date;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class FindAllQuery extends BaseAllQuery<Measurement> {
    public FindAllQuery(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<Measurement> executeQuery() {
        ArrayList<Measurement> records = new ArrayList<Measurement>();

        String[] columns = new String[]{
                ID,
                MEASUREMENT_NOTES
        };

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            Measurement m = new Measurement();
            m.setMeasurementId(c.getInt(i));
            m.setMeasurementNotes(c.getString(++i));

            records.add(m);
        }
        return records;
    }
}
