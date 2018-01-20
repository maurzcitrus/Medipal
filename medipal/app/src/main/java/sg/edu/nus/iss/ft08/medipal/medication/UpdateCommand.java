package sg.edu.nus.iss.ft08.medipal.medication;

import android.content.ContentValues;
import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Consumption;
import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.ConsumptionTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.MedicineTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toString_d_MMM_yyyy_H_mm;

public class UpdateCommand extends BaseCommand<Medication> {

    public UpdateCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Medication... params) {
        Medication medication = params[0];
        Medicine medicine = medication.getMedicine();
        Reminder reminder = medicine.getReminder();
        Consumption consumption;

        db.beginTransaction();
        try {

            int medicineRowsUpdated = updateMedicine(medicine);
            int reminderRowsUpdated = updateReminder(reminder);

            if(medicine.hasNewConsumption()){
                consumption = medicine.getNewConsumption();
                createConsumption(consumption);

                medicine.resetConsumptionStatus();
            }

            if(medicine.isLowQuantity()){
                // todo: karthik to set alarm for top-up
            }

            db.setTransactionSuccessful();

            Long result = new Long(medicineRowsUpdated);
            return result;
        } finally {
            db.endTransaction();
        }
    }

    private long createConsumption(Consumption consumption) {
        ContentValues values = new ContentValues();
        values.put(ConsumptionTable.CONSUMPTION_MEDICINE_ID, consumption.getMedicineId());
        values.put(ConsumptionTable.CONSUMPTION_MEDICINE_QUANTITY, consumption.getQuantity());
        values.put(ConsumptionTable.CONSUMPTION_CONSUMED_ON,  toString_d_MMM_yyyy_H_mm(consumption.getConsumedOn()));

        long consumptionId = db.insert(ConsumptionTable.TABLE_NAME, null, values);
        return consumptionId;
    }

    private int updateReminder(Reminder reminder) {
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(reminder.getId())
        };

        ContentValues values = new ContentValues();
        values.put(ReminderTable.REMINDER_FREQUENCY, reminder.getFrequency());
        values.put(ReminderTable.REMINDER_START_TIME, toString_d_MMM_yyyy_H_mm(reminder.getStartTime()));
        values.put(ReminderTable.REMINDER_INTERVAL, reminder.getInterval());

        int rowsUpdated = db.update(ReminderTable.TABLE_NAME, values, whereClause, whereArgs);

        return rowsUpdated;
    }

    private int updateMedicine(Medicine medicine) {
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(medicine.getId())
        };

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

        int rowsUpdated = db.update(MedicineTable.TABLE_NAME, values, whereClause, whereArgs);

        return rowsUpdated;
    }
}
