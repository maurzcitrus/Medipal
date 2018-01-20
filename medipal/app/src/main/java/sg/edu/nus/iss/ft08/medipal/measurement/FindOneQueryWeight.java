package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_WEIGHT;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class FindOneQueryWeight extends BaseOneQuery<Weight> {

    private int measurementID;

    public FindOneQueryWeight(Context context, int measurementID) {
        super(context);
        this.measurementID = measurementID;
    }

    @Override
    protected Weight executeQuery() {
        Weight record = null;

        String[] columns = new String[]{
                ID,
                MEASUREMENT_WEIGHT,
                MEASUREMENT_NOTES
        };

        String selection = "ID = ?";

        String[] selectionArgs = new String[]{
                Integer.toString(measurementID)
        };

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            record = new Weight();
            record.setMeasurementId(c.getInt(i));
            record.setWeight(c.getInt(++i));
            record.setMeasurementNotes(c.getString(++i));
            // take only the first row
            break;
        }

        return record;
    }


}
