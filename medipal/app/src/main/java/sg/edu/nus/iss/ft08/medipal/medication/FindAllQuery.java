package sg.edu.nus.iss.ft08.medipal.medication;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.ft08.medipal.core.Consumption;
import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Reminder;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;
import sg.edu.nus.iss.ft08.medipal.sqlite.ConsumptionTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.MedicineTable;
import sg.edu.nus.iss.ft08.medipal.sqlite.ReminderTable;

import static sg.edu.nus.iss.ft08.medipal.core.MediPalUtils.toDate_from_d_MMM_yyyy_H_mm;

public class FindAllQuery extends BaseAllQuery<Medication> {

    public FindAllQuery(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<Medication> executeQuery() {

        ArrayList<Medication> medications;
        ArrayList<Consumption> consumptions;

        medications = findMedicines();
        consumptions = findConsumptions();

        setConsumptions(medications, consumptions);

        return medications;
    }

    private void setConsumptions(ArrayList<Medication> records, ArrayList<Consumption> consumptions) {

        for (Medication m :
                records) {

            Medicine current = m.getMedicine();
            List<Consumption> currentConsumptions = current.getConsumptions();

            int medicineId = current.getId();

            for (Consumption c :
                    consumptions) {

                if(medicineId == c.getMedicineId()){
                    currentConsumptions.add(c);
                }
            }
        }

    }

    private ArrayList<Consumption> findConsumptions() {
        ArrayList<Consumption> records = new ArrayList<>();

        String[] columns = new String[]{
                ConsumptionTable.ID,
                ConsumptionTable.CONSUMPTION_MEDICINE_ID,
                ConsumptionTable.CONSUMPTION_MEDICINE_QUANTITY,
                ConsumptionTable.CONSUMPTION_CONSUMED_ON
        };

        String orderBy = " CONSUMPTION_MEDICINE_ID, ID ASC ";

        Cursor c = db.query(ConsumptionTable.TABLE_NAME, columns, null, null, null, null, orderBy);

        while(c.moveToNext()){
            int i = 0;

            Consumption p = new Consumption();
            p.setId(c.getInt(i));
            p.setMedicineId(c.getInt(++i));
            p.setQuantity(c.getInt(++i));
            p.setConsumedOn(toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));

            records.add(p);
        }

        return records;
    }

    private ArrayList<Medication> findMedicines() {
        ArrayList<Medication> records = new ArrayList<Medication>();

        String[] columns = new String[]{
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

        String tables = " medicine m LEFT OUTER JOIN reminder r ON m.medicine_reminder_id = r.id ";

        Cursor c = db.query(tables, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            int i = 0;

            Medicine m = new Medicine();
            m.setId(c.getInt(i));
            m.setCategoryId(++i);
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

            Medication medication = new Medication();
            medication.setMedicine(m);

            records.add(medication);
        }

        return records;
    }

}
