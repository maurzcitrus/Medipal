package sg.edu.nus.iss.ft08.medipal.personal;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.core.Person;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_ADDRESS;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_BLOOD_TYPE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_DOB;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_HEIGHT;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_ID_NO;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_POSTAL_CODE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.TABLE_NAME;


/**
 * Created by MuraliKrishnaB on 23/3/2017.
 */

public class FindOneQuery extends BaseOneQuery<Person> {

    private int personID;
    private boolean isById;
    private boolean isByName;
    private String personName;

    public FindOneQuery(Context context){
        super(context);
    }
    public FindOneQuery(Context context, int personID){
        super(context);
        this.personID=personID;
    }

    public FindOneQuery(Context context, String personName) {
        super(context);
        this.personName = personName;
        isByName = true;
    }

    @Override
    protected Person executeQuery() {
        Person record=null;

        String[] columns = new String[]{
                ID,
                PERSON_NAME,
                PERSON_DOB,
                PERSON_ID_NO,
                PERSON_ADDRESS,
                PERSON_POSTAL_CODE,
                PERSON_HEIGHT,
                PERSON_BLOOD_TYPE

        };

       // Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            record = new Person();
            record.setId(c.getInt(i));
            record.setName(c.getString(++i));
            record.setDateOfBirth(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));
            record.setPersonIdNo(c.getString(++i));
            record.setAddress(c.getString(++i));
            record.setPostalCode(c.getString(++i));
            record.setHeight(c.getInt(++i));
            record.setBloodType(c.getString(++i));

            // take only the first row
            break;
        }

        return record;
    }
}
