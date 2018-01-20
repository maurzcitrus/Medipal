package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


/**
 * Created by JAYENDRA on 3/21/2017.
 */

public class UpdateCommand extends BaseCommand<Measurement> {
    public UpdateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Measurement... params) {

        Measurement measurement = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(measurement.getMeasurementId())
        };

        ContentValues values = new ContentValues();
        values.put(MEASUREMENT_NOTES, measurement.getMeasurementNotes());

        int rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
