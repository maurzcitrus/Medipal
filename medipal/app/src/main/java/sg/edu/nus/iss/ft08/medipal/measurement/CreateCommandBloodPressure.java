package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_dd_MMM_yyyy;
import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_DIASTOLIC;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_SYSTOLIC;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class CreateCommandBloodPressure extends BaseCommand<BloodPressure> {
    public CreateCommandBloodPressure(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(BloodPressure... params) {

        BloodPressure bloodpressure = params[0];

        ContentValues contentValues = new ContentValues();

        contentValues.put(MEASUREMENT_SYSTOLIC, bloodpressure.getSystolic());
        contentValues.put(MEASUREMENT_DIASTOLIC, bloodpressure.getDiastolic());
        contentValues.put(MEASUREMENT_NOTES, bloodpressure.getMeasurementNotes());
        contentValues.put(MEASUREMENT_MEASURED_ON, toString_d_MMM_yyyy_H_mm(bloodpressure.getMeasuredOn()));


        return db.insert(TABLE_NAME, null, contentValues);
    }
}
