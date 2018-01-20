package sg.edu.nus.iss.ft08.medipal.reminder;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;

import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable.TABLE_NAME;

public class CreateCommand extends BaseCommand<Reminder> {
    public CreateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Reminder... params) {
        Reminder reminder = params[0];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(ReminderTable.REMINDER_START_TIME, dateFormat.format(reminder.getStartTime()));
        values.put(ReminderTable.REMINDER_FREQUENCY, reminder.getFrequency());
        values.put(ReminderTable.REMINDER_INTERVAL, reminder.getInterval());
        return db.insert(TABLE_NAME, null, values);
    }
}
