package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable.TABLE_NAME;

/**
 * Created by JAYENDRA on 3/21/2017.
 */

public class DeleteCommand extends BaseCommand<Measurement> {
    public DeleteCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Measurement... params) {

        Measurement medication = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(medication.getMeasurementId())
        };

        int rowsUpdated = db.delete(TABLE_NAME, whereClause, whereArgs);
        Long result = new Long(rowsUpdated);

        return result;
    }
}
