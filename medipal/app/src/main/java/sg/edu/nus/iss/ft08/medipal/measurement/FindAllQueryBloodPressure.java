package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_DIASTOLIC;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_SYSTOLIC;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class FindAllQueryBloodPressure extends BaseAllQuery<BloodPressure> {

    public FindAllQueryBloodPressure(Context context) {
        super(context);
    }

    @Override
    protected ArrayList executeQuery() {

        ArrayList<BloodPressure> records = new ArrayList<BloodPressure>();

        String[] columns = new String[]{
                ID,
                MEASUREMENT_SYSTOLIC,
                MEASUREMENT_DIASTOLIC,
                MEASUREMENT_MEASURED_ON
        };

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            BloodPressure bp = new BloodPressure();
            bp.setMeasurementId(c.getInt(i));
            bp.setSystolic(c.getInt(++i));
            bp.setDiastolic(c.getInt(++i));
            String x = c.getString(++i);
            bp.setMeasuredOn(toDate_from_d_MMM_yyyy_H_mm(x));

            records.add(bp);

        }
        return records;
    }
}
