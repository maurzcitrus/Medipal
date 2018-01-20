package sg.edu.nus.iss.ft08.medipal.appointment;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.core.Appointment;
import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable.*;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;
import static sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable.APPOINTMENT_DATE_TIME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable.APPOINTMENT_DESCRIPTION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable.APPOINTMENT_LOCATION;
import static sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable.APPOINTMENT_REMINDER_FLAG;
import static sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable.TABLE_NAME;


/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class FindAllQuery extends BaseAllQuery<Appointment> {

    public FindAllQuery(Context context) {
        super(context);
    }
    @Override
    protected ArrayList<Appointment> executeQuery() {
       ArrayList<Appointment> records= new ArrayList<Appointment>();

        String[] columns = new String[]{
                ID,
                APPOINTMENT_LOCATION,
                APPOINTMENT_DATE_TIME,
                APPOINTMENT_DESCRIPTION,
                APPOINTMENT_REMINDER_FLAG

        };
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            Appointment a=new Appointment();
            a.setId(c.getInt(i));
            a.setLocation(c.getString(++i));
            a.setAppointmentDateTime(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));
            a.setDescription(c.getString(++i));
            a.setReminderFlag(c.getString(++i));

            records.add(a);
        }

        return records;
    }
}
