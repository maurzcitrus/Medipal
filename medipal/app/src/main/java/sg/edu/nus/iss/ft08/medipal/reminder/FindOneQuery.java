package sg.edu.nus.iss.ft08.medipal.reminder;

import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;

import static sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable.ID;
import static sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable.REMINDER_FREQUENCY;
import static sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable.REMINDER_INTERVAL;
import static sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable.REMINDER_START_TIME;
import static sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable.TABLE_NAME;

public class FindOneQuery extends BaseOneQuery<Reminder> {

    private int reminderId;

    public FindOneQuery(Context context, int reminderId) {
        super(context);
        this.reminderId = reminderId;
    }

    @Override
    protected Reminder executeQuery() {
        Reminder record = null;

        String[] columns = new String[]{
                ID,
                REMINDER_START_TIME,
                REMINDER_INTERVAL,
                REMINDER_FREQUENCY
        };

        String selection = "ID = ?";

        String[] selectionArgs = new String[]{
                Integer.toString(reminderId)
        };

        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        while (c.moveToNext()) {
            record = new Reminder();
            record.setId(c.getInt(0));
            try {
                record.setStartTime(dateFormat.parse(c.getString(1)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            record.setInterval(Integer.parseInt(c.getString(2)));
            record.setFrequency(Integer.parseInt(c.getString(3)));
            break;
        }
        return record;
    }
}
