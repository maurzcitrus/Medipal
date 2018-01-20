package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class DeleteCommandBloodPressure extends BaseCommand<BloodPressure> {
    public DeleteCommandBloodPressure(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(BloodPressure... params) {

        BloodPressure bloopressure = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(bloopressure.getMeasurementId())
        };

        int rowsUpdated = db.delete(TABLE_NAME, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
