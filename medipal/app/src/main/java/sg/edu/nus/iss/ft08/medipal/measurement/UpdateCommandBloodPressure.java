package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_DIASTOLIC;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_MEASURED_ON;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_NOTES;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.MEASUREMENT_SYSTOLIC;
import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class UpdateCommandBloodPressure extends BaseCommand<BloodPressure> {
    public UpdateCommandBloodPressure(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(BloodPressure... params) {
        BloodPressure bloodpressure = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(bloodpressure.getMeasurementId())
        };

        ContentValues values = new ContentValues();
        values.put(MEASUREMENT_SYSTOLIC, bloodpressure.getSystolic());
        values.put(MEASUREMENT_DIASTOLIC, bloodpressure.getDiastolic());
        values.put(MEASUREMENT_NOTES, bloodpressure.getMeasurementNotes());
        values.put(MEASUREMENT_MEASURED_ON, toString_d_MMM_yyyy_H_mm(bloodpressure.getMeasuredOn()));

        int rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
