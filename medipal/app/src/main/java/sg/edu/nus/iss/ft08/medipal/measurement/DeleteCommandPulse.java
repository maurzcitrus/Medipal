package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;


public class DeleteCommandPulse extends BaseCommand<Pulse> {
    public DeleteCommandPulse(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Pulse... params) {

        Pulse pulse = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(pulse.getMeasurementId())
        };

        int rowsUpdated = db.delete(TABLE_NAME, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
