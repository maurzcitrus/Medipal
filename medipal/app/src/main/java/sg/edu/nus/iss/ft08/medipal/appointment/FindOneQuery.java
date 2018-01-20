package sg.edu.nus.iss.ft08.medipal.appointment;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.core.Appointment;
import sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

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

public class FindOneQuery extends BaseOneQuery<Appointment> {
    private int appointmentID;

    public FindOneQuery(Context context, int appointmentID){
        super(context);
        this.appointmentID=appointmentID;
    }

    @Override
    protected Appointment executeQuery() {
        Appointment record=null;

        String[] columns = new String[]{
                ID,
                APPOINTMENT_LOCATION,
                APPOINTMENT_DATE_TIME,
                APPOINTMENT_DESCRIPTION,
                APPOINTMENT_REMINDER_FLAG

        };

        String selection = "ID = ?";

        String[] selectionArgs = new String[]{
                Integer.toString(appointmentID)
        };

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            record = new Appointment();
            record.setId(c.getInt(i));
            record.setLocation(c.getString(++i));
            record.setAppointmentDateTime(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));
            record.setDescription(c.getString(++i));
            record.setReminderFlag(c.getString(++i));


            // take only the first row
            break;
        }
        return record;
    }
}
