package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_PULSE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class UpdateCommandPulse extends BaseCommand<Pulse> {
    public UpdateCommandPulse(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Pulse... params) {
        Pulse pulse = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(pulse.getMeasurementId())
        };

        ContentValues values = new ContentValues();
        values.put(MEASUREMENT_PULSE, pulse.getPulse());
        values.put(MEASUREMENT_NOTES, pulse.getMeasurementNotes());
        values.put(MEASUREMENT_MEASURED_ON, toString_d_MMM_yyyy_H_mm(pulse.getMeasuredOn()));

        int rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
