package sg.edu.nus.iss.ft08.medipal.medication;

import android.content.Context;

import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseCommand;
import sg.edu.nus.iss.ft08.medipal.sqlite.ConsumptionTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable;

import sg.edu.nus.iss.ft08.medipal.sqlite.MedicineTable;

public class DeleteCommand extends BaseCommand<Medication> {

    public DeleteCommand(Context context) {
        super(context);
    }

    @Override
    protected Long executeCommand(Medication... params) {
        Medication medication = params[0];
        Medicine medicine = medication.getMedicine();
        Reminder reminder = medicine.getReminder();

        db.beginTransaction();
        try {
            deleteConsumptions(medicine);
            deleteReminder(reminder);
            long medicineRowsDeleted = deleteMedicine(medicine);
            db.setTransactionSuccessful();
            return medicineRowsDeleted;
        } finally {
            db.endTransaction();
        }
    }

    private long deleteConsumptions(Medicine medicine) {
        String whereClause = " CONSUMPTION_MEDICINE_ID = ? ";
        String[] whereArgs = new String[]{
                Integer.toString(medicine.getId())
        };

        int rowsUpdated = db.delete(ConsumptionTable.TABLE_NAME, whereClause, whereArgs);
        return new Long(rowsUpdated);
    }

    private long deleteMedicine(Medicine medicine) {
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(medicine.getId())
        };

        int rowsUpdated = db.delete( MedicineTable.TABLE_NAME, whereClause, whereArgs);

        return new Long(rowsUpdated);
    }

    private long deleteReminder(Reminder reminder) {
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{
                Integer.toString(reminder.getId())
        };

        int rowsUpdated = db.delete(ReminderTable.TABLE_NAME, whereClause, whereArgs);

        return new Long(rowsUpdated);

    }
}
