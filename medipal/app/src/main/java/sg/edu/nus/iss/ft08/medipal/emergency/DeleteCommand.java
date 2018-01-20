package sg.edu.nus.iss.ft08.medipal.emergency;

import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.TABLE_NAME;


public class DeleteCommand extends BaseCommand<EmergencyContact> {

    public DeleteCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(EmergencyContact... params) {
        EmergencyContact emergencyContact = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(emergencyContact.getId())
        };

        int rowsUpdated = db.delete(TABLE_NAME, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
