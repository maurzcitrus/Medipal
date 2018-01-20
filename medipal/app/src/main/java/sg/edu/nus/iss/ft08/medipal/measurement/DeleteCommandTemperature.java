package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class DeleteCommandTemperature extends BaseCommand<Temperature> {
    public DeleteCommandTemperature(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Temperature... params) {

        Temperature temperature = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(temperature.getMeasurementId())
        };

        int rowsUpdated = db.delete(TABLE_NAME, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
