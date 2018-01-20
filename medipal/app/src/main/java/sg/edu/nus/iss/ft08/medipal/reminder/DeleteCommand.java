package sg.edu.nus.iss.ft08.medipal.reminder;

import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;

import static sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable.TABLE_NAME;

public class DeleteCommand extends BaseCommand<Reminder> {
    public DeleteCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Reminder... params) {

        Reminder reminder=params[0];
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(reminder.getId())
        };

        int rowsUpdated = db.delete(TABLE_NAME, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
