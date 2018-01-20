package sg.edu.nus.iss.ft08.medipal.personal;

import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Person;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.PersonalTable;

/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class DeleteCommand extends BaseCommand<Person> {
    public DeleteCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Person... params) {
        Person medication = params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(medication.getId())
        };

        int rowsUpdated = db.delete(PersonalTable.TABLE_NAME, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
