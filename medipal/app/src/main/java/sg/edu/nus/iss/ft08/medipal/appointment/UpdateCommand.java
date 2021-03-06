package sg.edu.nus.iss.ft08.medipal.appointment;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;

import sg.edu.nus.iss.ft08.medipal.core.Appointment;
import sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

/**
 * Created by MuraliKrishnaB on 19/3/2017.
 */

public class UpdateCommand extends BaseCommand<Appointment> {

    public UpdateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Appointment... params) {
        Appointment appointment=params[0];
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(appointment.getId())
        };

        ContentValues values = new ContentValues();
        values.put(AppointmentTable.APPOINTMENT_LOCATION, appointment.getLocation());
        values.put(AppointmentTable.APPOINTMENT_DATE_TIME, toString_d_MMM_yyyy_H_mm(appointment.getAppointmentDateTime()));
        values.put(AppointmentTable.APPOINTMENT_DESCRIPTION, appointment.getDescription());
        values.put(AppointmentTable.APPOINTMENT_REMINDER_FLAG,appointment.getReminderFlag());

        int rowsUpdated = db.update(AppointmentTable.TABLE_NAME, values, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
