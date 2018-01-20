package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_WEIGHT;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class CreateCommandWeight extends BaseCommand<Weight>{
    public CreateCommandWeight(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Weight... params) {
        Weight weight = params[0];

        ContentValues contentValues = new ContentValues();

        contentValues.put(MEASUREMENT_WEIGHT, weight.getWeight());
        contentValues.put(MEASUREMENT_NOTES, weight.getMeasurementNotes());
        contentValues.put(MEASUREMENT_MEASURED_ON, toString_d_MMM_yyyy_H_mm(weight.getMeasuredOn()));

        return db.insert(TABLE_NAME, null, contentValues);

    }
}
