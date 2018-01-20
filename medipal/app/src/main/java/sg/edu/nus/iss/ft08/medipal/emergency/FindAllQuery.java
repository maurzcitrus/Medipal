package sg.edu.nus.iss.ft08.medipal.emergency;


import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_DESCRIPTION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_NO;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_PRIORITY;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_TYPE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.TABLE_NAME;

public class FindAllQuery extends BaseAllQuery<EmergencyContact> {

    public FindAllQuery(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<EmergencyContact> executeQuery() {
        ArrayList<EmergencyContact> records = new ArrayList<EmergencyContact>();

        String[] columns = new String[]{
                ID,
                EMERGENCY_CONTACT_NAME,
                EMERGENCY_CONTACT_NO,
                EMERGENCY_CONTACT_TYPE,
                EMERGENCY_CONTACT_DESCRIPTION,
                EMERGENCY_CONTACT_PRIORITY,
        };

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null,
                EMERGENCY_CONTACT_PRIORITY + " ASC");

        while (c.moveToNext()) {
            int i = 0;

            EmergencyContact m = new EmergencyContact();
            m.setId(c.getInt(i));
            m.setName(c.getString(++i));
            m.setContactNo(c.getString(++i));
            m.setContactType(c.getString(++i));
            m.setDescription(c.getString(++i));
            m.setPriority(c.getInt(++i));
            records.add(m);
        }

        return records;
    }
}
