package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.HealthBio;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

public class UpdateCommand extends BaseCommand<HealthBio> {

    public UpdateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(HealthBio... params) {
        HealthBio healthBio=params[0];

        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(healthBio.getId())
        };

        ContentValues values = new ContentValues();
        values.put(HealthBioTable.HEALTH_BIO_CONDITION, healthBio.getCondition());
        values.put(HealthBioTable.HEALTH_BIO_START_DATE, toString_d_MMM_yyyy_H_mm(healthBio.getStartDate()));
        values.put(HealthBioTable.HEALTH_BIO_CONDITION_TYPE, healthBio.getConditionType());

        int rowsUpdated = db.update(HealthBioTable.TABLE_NAME, values, whereClause, whereArgs);

        Long result = new Long(rowsUpdated);

        return result;
    }
}
