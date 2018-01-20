package sg.edu.nus.iss.ft08.medipal.personal;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.core.Person;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;
import sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_ADDRESS;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_BLOOD_TYPE;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_DOB;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_HEIGHT;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_ID_NO;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_NAME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable.PERSON_POSTAL_CODE;

/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class FindAllQuery extends BaseAllQuery<Person> {


    public FindAllQuery(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<Person> executeQuery() {
        ArrayList<Person> records = new ArrayList<Person>();

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

        Cursor c = db.query(PersonalTable.TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            Person p = new Person();
            p.setId(c.getInt(i));
            p.setName(c.getString(++i));
            p.setDateOfBirth(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));
            p.setPersonIdNo(c.getString(++i));
            p.setAddress(c.getString(++i));
            p.setPostalCode(c.getString(++i));
            p.setHeight(c.getInt(++i));
            p.setBloodType(c.getString(++i));

            records.add(p);
        }

        return records;
    }
}
