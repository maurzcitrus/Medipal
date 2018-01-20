package sg.edu.nus.iss.ft08.medipal.appointment;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;

import sg.edu.nus.iss.ft08.medipal.core.Appointment;
import sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.AppointmentTable.TABLE_NAME;

/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class DeleteCommand extends BaseCommand<Appointment> {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public DeleteCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Appointment... params) {

        Appointment appointment=params[0];
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(appointment.getId())
        };

        int rowsUpdated = db.delete(TABLE_NAME, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
