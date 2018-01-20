package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;

import sg.edu.nus.iss.ft08.medipal.core.HealthBio;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable;

import static sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable.TABLE_NAME;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

/**
 * Created by MuraliKrishnaB on 20/3/2017.
 */

public class DeleteCommand extends BaseCommand<HealthBio> {
    public DeleteCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(HealthBio... params) {
        HealthBio healthBio=params[0];
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(healthBio.getId())
        };

        int rowsUpdated = db.delete(TABLE_NAME, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
