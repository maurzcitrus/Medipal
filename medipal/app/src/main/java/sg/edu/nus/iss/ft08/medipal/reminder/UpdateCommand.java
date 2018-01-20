package sg.edu.nus.iss.ft08.medipal.reminder;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;

import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable.TABLE_NAME;

public class UpdateCommand extends BaseCommand<Reminder> {

    public UpdateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Reminder... params) {
        Reminder reminder=params[0];
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(reminder.getId())
        };

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(ReminderTable.REMINDER_START_TIME, dateFormat.format(reminder.getStartTime()));
        values.put(ReminderTable.REMINDER_FREQUENCY, reminder.getFrequency());
        values.put(ReminderTable.REMINDER_INTERVAL, reminder.getInterval());

        int rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs);
        Long result = new Long(rowsUpdated);
        return result;
    }
}
