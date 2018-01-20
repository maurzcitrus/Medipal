package sg.edu.nus.iss.ft08.medipal.personal;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Person;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

/**
 * Created by MuraliKrishnaB on 19/3/2017.
 */

public class CreateCommand extends BaseCommand<Person> {

    public CreateCommand(Context context) {
        super(context);
    }
    @Override
    protected Long executeCommand(Person... params) {
       Person person = params[0];

        ContentValues values = new ContentValues();
        values.put(PersonalTable.PERSON_NAME, person.getName());
        values.put(PersonalTable.PERSON_DOB, toString_d_MMM_yyyy_H_mm(person.getDateOfBirth()));
        values.put(PersonalTable.PERSON_ID_NO, person.getPersonIdNo());
        values.put(PersonalTable.PERSON_ADDRESS, person.getAddress());
        values.put(PersonalTable.PERSON_POSTAL_CODE, person.getPostalCode());
        values.put(PersonalTable.PERSON_HEIGHT, person.getHeight());
        values.put(PersonalTable.PERSON_BLOOD_TYPE, person.getBloodType());

        return db.insert(PersonalTable.TABLE_NAME,null,values);

    }
}
