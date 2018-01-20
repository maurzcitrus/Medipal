package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_TEMPERATURE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class CreateCommandTemperature extends BaseCommand<Temperature> {
    public CreateCommandTemperature(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Temperature... params) {
        Temperature temperature = params[0];

        ContentValues contentValues = new ContentValues();

        contentValues.put(MEASUREMENT_TEMPERATURE, temperature.getTemperature());
        contentValues.put(MEASUREMENT_NOTES, temperature.getMeasurementNotes());
        contentValues.put(MEASUREMENT_MEASURED_ON, toString_d_MMM_yyyy_H_mm(temperature.getMeasuredOn()));

        return db.insert(TABLE_NAME, null, contentValues);
    }
}
