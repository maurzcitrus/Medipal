package sg.edu.nus.iss.ft08.medipal.medication;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseOneQuery;
import sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.*;
import sg.edu.nus.iss.ft08.medipal.sqlite.MedicineTable;

public class FindOneQuery extends BaseOneQuery<Medication> {

    private int medicineId;
    private boolean isById;

    private String medicineName;
    private boolean isByName;

    private String[] columns;
    private String tables;
    private String selection;
    private String[] selectionArgs;

    public FindOneQuery(Context context, int medicineId) {
        super(context);
        this.medicineId = medicineId;
        isById = true;
    }

    public FindOneQuery(Context context, String medicineName) {
        super(context);
        this.medicineName = medicineName;
        isByName = true;
    }

    private void prepareColumns() {
        columns = new String[]{
                "m." + MedicineTable.ID,
                "m." + MedicineTable.MEDICINE_CATEGORY_ID,
                "m." + MedicineTable.MEDICINE_NAME,
                "m." + MedicineTable.MEDICINE_DESCRIPTION,
                "m." + MedicineTable.MEDICINE_DATE_ISSUED,
                "m." + MedicineTable.MEDICINE_EXPIRE_FACTOR,
                "m." + MedicineTable.MEDICINE_QUANTITY,
                "m." + MedicineTable.MEDICINE_FREQUENCY,
                "m." + MedicineTable.MEDICINE_DOSAGE,
                "m." + MedicineTable.MEDICINE_REMINDER_ID,
                "m." + MedicineTable.MEDICINE_REMINDER_FLAG,
                "m." + MedicineTable.MEDICINE_THRESHOLD,
                "r." + ReminderTable.ID,
                "r." + ReminderTable.REMINDER_START_TIME,
                "r." + ReminderTable.REMINDER_FREQUENCY,
                "r." + ReminderTable.REMINDER_INTERVAL
        };
    }

    private void prepareTables() {
        tables = " medicine m LEFT OUTER JOIN reminder r ON m.medicine_reminder_id = r.id ";
    }

    private void prepareCriteria() {
        if (isByName) {
            selection = "UPPER(m.MEDICINE_NAME) = UPPER(?)";
            selectionArgs = new String[]{
                    medicineName
            };
        } else {
            selection = "m.ID = ?";
            selectionArgs = new String[]{
                    Integer.toString(medicineId)
            };
        }
    }

    @Override
    protected Medication executeQuery() {
        Medication medication = null;

        prepareTables();
        prepareColumns();
        prepareCriteria();

        Cursor c = db.query(tables, columns, selection, selectionArgs, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            Medicine m = new Medicine();
            m.setId(c.getInt(i));
            m.setCategoryId(c.getInt(++i));
            m.setName(c.getString(++i));
            m.setDescription(c.getString(++i));
            m.setDateIssued(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));
            m.setExpiryFactor(c.getInt(++i));
            m.setQuantity(c.getInt(++i));
            m.setFrequency(c.getInt(++i));
            m.setDosage(c.getInt(++i));
            m.setReminderId(c.getInt(++i));
            m.setReminderFlag(c.getString(++i));
            m.setThreshold(c.getInt(++i));

            Reminder r = new Reminder();
            r.setId(c.getInt(++i));
            r.setStartTime(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));
            r.setFrequency(c.getInt(++i));
            r.setInterval(c.getInt(++i));

            m.setReminder(r);

            medication = new Medication();
            medication.setMedicine(m);

            // take only the first row
            break;
        }


        return medication;
    }
}
