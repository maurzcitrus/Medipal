package sg.edu.nus.iss.ft08.medipal.emergency;

/**
 * Created by cmani on 26/3/2017.
 */

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_DESCRIPTION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_NO;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_PRIORITY;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_TYPE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.TABLE_NAME;

public class FindOneQuery extends BaseOneQuery<EmergencyContact> {

    private int Id;

    public FindOneQuery(Context context, int Id)
    {
        super(context);
        this.Id = Id;
    }
    @Override
    protected EmergencyContact executeQuery() {
        EmergencyContact record = null;

        String[] columns = new String[]{
                ID,
                EMERGENCY_CONTACT_NAME,
                EMERGENCY_CONTACT_NO,
                EMERGENCY_CONTACT_TYPE,
                EMERGENCY_CONTACT_DESCRIPTION,
                EMERGENCY_CONTACT_PRIORITY,
        };

        String selection = "ID = ?";

        String[] selectionArgs = new String[]{
                Integer.toString(Id)
        };

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            record = new EmergencyContact();
            record.setId(c.getInt(i));
            record.setName(c.getString(++i));
            record.setContactNo(c.getString(++i));
            record.setContactType(c.getString(++i));
            record.setDescription(c.getString(++i));
            record.setPriority(c.getInt(++i));
            // take only the first row
            break;
        }

        return record;
    }
}

