package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_PULSE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class FindOneQueryPulse extends BaseOneQuery<Pulse> {

    private int measurementID;

    public FindOneQueryPulse(Context context, int measurementID) {
        super(context);
        this.measurementID = measurementID;
    }

    @Override
    protected Pulse executeQuery() {
        Pulse record = null;

        String[] columns = new String[]{
                ID,
                MEASUREMENT_PULSE,
                MEASUREMENT_NOTES
        };

        String selection = "ID = ?";

        String[] selectionArgs = new String[]{
                Integer.toString(measurementID)
        };

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            record = new Pulse();
            record.setMeasurementId(c.getInt(i));
            record.setPulse(c.getInt(++i));
            record.setMeasurementNotes(c.getString(++i));
            // take only the first row
            break;
        }

        return record;
    }


}
