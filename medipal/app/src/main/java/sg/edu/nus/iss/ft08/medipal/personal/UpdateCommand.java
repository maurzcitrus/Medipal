package sg.edu.nus.iss.ft08.medipal.personal;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;

import sg.edu.nus.iss.ft08.medipal.core.Person;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class UpdateCommand extends BaseCommand<Person>{
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public UpdateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Person... params) {
        Person person=params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(person.getId())
        };

        ContentValues values = new ContentValues();
        values.put(PersonalTable.PERSON_NAME, person.getName());
        values.put(PersonalTable.PERSON_DOB,toString_d_MMM_yyyy_H_mm( person.getDateOfBirth()));
        values.put(PersonalTable.PERSON_ID_NO, person.getPersonIdNo());
        values.put(PersonalTable.PERSON_ADDRESS, person.getAddress());
        values.put(PersonalTable.PERSON_POSTAL_CODE, person.getPostalCode());
        values.put(PersonalTable.PERSON_HEIGHT, person.getHeight());
        values.put(PersonalTable.PERSON_BLOOD_TYPE, person.getBloodType());


        int rowsUpdated = db.update(PersonalTable.TABLE_NAME, values, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
