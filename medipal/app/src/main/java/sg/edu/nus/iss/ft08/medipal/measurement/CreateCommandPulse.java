package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_PULSE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class CreateCommandPulse extends BaseCommand<Pulse>{


    public CreateCommandPulse(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Pulse... params) {
        Pulse pulse = params[0];

        ContentValues contentValues = new ContentValues();

        contentValues.put(MEASUREMENT_PULSE, pulse.getPulse());
        contentValues.put(MEASUREMENT_NOTES, pulse.getMeasurementNotes());
        contentValues.put(MEASUREMENT_MEASURED_ON, toString_d_MMM_yyyy_H_mm(pulse.getMeasuredOn()));

        return db.insert(TABLE_NAME, null, contentValues);
    }
}
