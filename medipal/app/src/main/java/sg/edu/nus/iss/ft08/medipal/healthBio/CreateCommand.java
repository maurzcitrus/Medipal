package sg.edu.nus.iss.ft08.medipal.healthBio;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;

import sg.edu.nus.iss.ft08.medipal.core.HealthBio;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.HealthBioTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

/**
 * Created by MuraliKrishnaB on 19/3/2017.
 */

public class CreateCommand extends BaseCommand<HealthBio> {
    public CreateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(HealthBio... params) {
        HealthBio healthBio = params[0];

        ContentValues values = new ContentValues();
        values.put(HealthBioTable.HEALTH_BIO_CONDITION, healthBio.getCondition());
        values.put(HealthBioTable.HEALTH_BIO_START_DATE,toString_d_MMM_yyyy_H_mm(healthBio.getStartDate()));
        values.put(HealthBioTable.HEALTH_BIO_CONDITION_TYPE, healthBio.getConditionType());

        return db.insert(HealthBioTable.TABLE_NAME, null, values);
    }
}
