package sg.edu.nus.iss.ft08.medipal.medication;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.MedicineTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

public class CreateCommand extends BaseCommand<Medication> {

    public CreateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Medication... params) {

        Medication medication = params[0];
        Medicine medicine = medication.getMedicine();
        Reminder reminder = medicine.getReminder();

        db.beginTransaction();
        try {

            long reminderId = createReminder(reminder);

            medicine.setReminderId((int) reminderId);

            long medicineId = createMedicine(medicine);

            db.setTransactionSuccessful();

            return medicineId;
        } finally {
            db.endTransaction();
        }
    }

    private long createMedicine(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(MedicineTable.MEDICINE_NAME, medicine.getName());
        values.put(MedicineTable.MEDICINE_CATEGORY_ID, medicine.getCategoryId());
        values.put(MedicineTable.MEDICINE_DESCRIPTION, medicine.getDescription());
        values.put(MedicineTable.MEDICINE_DATE_ISSUED, toString_d_MMM_yyyy_H_mm(medicine.getDateIssued()));
        values.put(MedicineTable.MEDICINE_EXPIRE_FACTOR, medicine.getExpiryFactor());
        values.put(MedicineTable.MEDICINE_QUANTITY, medicine.getQuantity());
        values.put(MedicineTable.MEDICINE_FREQUENCY, medicine.getFrequency());
        values.put(MedicineTable.MEDICINE_DOSAGE, medicine.getDosage());
        values.put(MedicineTable.MEDICINE_REMINDER_ID, medicine.getReminderId());
        values.put(MedicineTable.MEDICINE_REMINDER_FLAG, medicine.getReminderFlag());
        values.put(MedicineTable.MEDICINE_THRESHOLD, medicine.getThreshold());

        long medicineId = db.insert(MedicineTable.TABLE_NAME, null, values);
        return medicineId;
    }

    private long createReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(ReminderTable.REMINDER_FREQUENCY, reminder.getFrequency());
        values.put(ReminderTable.REMINDER_START_TIME, toString_d_MMM_yyyy_H_mm(reminder.getStartTime()));
        values.put(ReminderTable.REMINDER_INTERVAL, reminder.getInterval());

        long reminderId = db.insert(ReminderTable.TABLE_NAME, null, values);
        return reminderId;
    }
}
