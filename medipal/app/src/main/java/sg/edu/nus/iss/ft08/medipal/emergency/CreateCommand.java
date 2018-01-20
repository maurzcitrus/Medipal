package sg.edu.nus.iss.ft08.medipal.emergency;


import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable;

import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_DESCRIPTION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_NO;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_PRIORITY;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.EMERGENCY_CONTACT_TYPE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.EmergencyContactTable.TABLE_NAME;


public class CreateCommand extends BaseCommand<EmergencyContact> {

    public CreateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(EmergencyContact... params) {

        EmergencyContact emergencyContact = params[0];

        ContentValues values = new ContentValues();
        values.put(EMERGENCY_CONTACT_NAME, emergencyContact.getName());
        values.put(EMERGENCY_CONTACT_NO,emergencyContact.getContactNo());
        values.put(EMERGENCY_CONTACT_PRIORITY,emergencyContact.getPriority());
        values.put(EMERGENCY_CONTACT_TYPE,emergencyContact.getContactType());
        values.put(EMERGENCY_CONTACT_DESCRIPTION,emergencyContact.getDescription());

        return db.insert(TABLE_NAME, null, values);
    }
}
