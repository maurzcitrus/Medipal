package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_DIASTOLIC;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_SYSTOLIC;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class FindOneQueryBloodPressure extends BaseOneQuery<BloodPressure> {

    private int measurementID;

    public FindOneQueryBloodPressure(Context context, int measurementID) {
        super(context);
        this.measurementID = measurementID;
    }

    @Override
    protected BloodPressure executeQuery() {
        BloodPressure record = null;

        String[] columns = new String[]{
                ID,
                MEASUREMENT_SYSTOLIC,
                MEASUREMENT_DIASTOLIC,
                MEASUREMENT_NOTES
        };

        String selection = "ID = ?";

        String[] selectionArgs = new String[]{
                Integer.toString(measurementID)
        };

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()) {
            int i = 0;
            record = new BloodPressure();
            record.setMeasurementId(c.getInt(i));
            record.setSystolic(c.getInt(++i));
            record.setDiastolic(c.getInt(++i));
            record.setMeasurementNotes(c.getString(++i));
            // take only the first row
            break;
        }

        return record;
    }


}
