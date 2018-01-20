package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MedicineTable.MEDICINE_NAME;

/**
 * Created by JAYENDRA on 3/21/2017.
 */

public class FindOneQuery extends BaseOneQuery<Measurement> {

    private int measurementID;
    public FindOneQuery(Context context) {
        super(context);
        this.measurementID = measurementID;
    }

    @Override
    protected Measurement executeQuery() {
        Measurement record = null;

        String[] columns = new String[]{
                ID,
                MEDICINE_NAME
        };

        String selection = "ID = ?";

        String[] selectionArgs = new String[]{
                Integer.toString(measurementID)
        };

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            record = new Measurement();
            record.setMeasurementId(c.getInt(i));
            record.setMeasurementNotes(c.getString(++i));
            // take only the first row
            break;
        }

        return record;
    }
}
