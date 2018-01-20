package sg.edu.nus.iss.ft08.medipal.measurement;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.sqlite.BaseAllQuery;
import sg.edu.nus.iss.ft08.medipal.sqlite.MeasurementTable;

public class FindLatestMeasurementQuery extends BaseAllQuery<Measurement> {

    public FindLatestMeasurementQuery(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<Measurement> executeQuery() {

        Measurement bloodPressure = findLatestBloodPressure();
        Measurement pulse = findLatestPulse();
        Measurement temperature = findLatestTemperature();
        Measurement weight = findLatestWeight();

        ArrayList<Measurement> measurements = new ArrayList<>();

        if (bloodPressure == null) {
            measurements.add(new BloodPressure());
        } else {
            measurements.add(bloodPressure);
        }

        if (pulse == null) {
            measurements.add(new Pulse());
        } else {
            measurements.add(pulse);
        }

        if (temperature == null) {
            measurements.add(new Temperature());
        } else {
            measurements.add(temperature);
        }

        if (weight == null) {
            measurements.add(new Weight());
        } else {
            measurements.add(weight);
        }

        return measurements;
    }

    private Measurement findLatestWeight() {
        Weight result = null;

        String selection = " MEASUREMENT_WEIGHT IS NOT NULL ";

        String[] columns = new String[]
                {
                        MeasurementTable.ID,
                        MeasurementTable.MEASUREMENT_WEIGHT,
                        MeasurementTable.MEASUREMENT_NOTES,
                        MeasurementTable.MEASUREMENT_MEASURED_ON
                };

        String orderAndLimit = " MEASUREMENT_MEASURED_ON DESC LIMIT 1 ";

        Cursor c = db.query(MeasurementTable.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                orderAndLimit);

        while (c.moveToNext()) {
            int i = 0;

            result = new Weight();
            result.setMeasurementId(c.getInt(i));
            result.setWeight(c.getInt(++i));
            result.setMeasurementNotes(c.getString(++i));
            result.setMeasuredOn(MediPalUtils.toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));

            break;
        }

        return result;
    }

    private Measurement findLatestTemperature() {
        Temperature result = null;

        String selection = " MEASUREMENT_TEMPERATURE IS NOT NULL ";

        String[] columns = new String[]
                {
                        MeasurementTable.ID,
                        MeasurementTable.MEASUREMENT_TEMPERATURE,
                        MeasurementTable.MEASUREMENT_NOTES,
                        MeasurementTable.MEASUREMENT_MEASURED_ON
                };

        String orderAndLimit = " MEASUREMENT_MEASURED_ON DESC LIMIT 1 ";

        Cursor c = db.query(MeasurementTable.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                orderAndLimit);

        while (c.moveToNext()) {
            int i = 0;

            result = new Temperature();
            result.setMeasurementId(c.getInt(i));
            result.setTemperature(c.getFloat(++i));
            result.setMeasurementNotes(c.getString(++i));
            result.setMeasuredOn(MediPalUtils.toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));

            break;
        }

        return result;
    }

    private Measurement findLatestPulse() {
        Pulse result = null;

        String selection = " MEASUREMENT_PULSE IS NOT NULL ";

        String[] columns = new String[]
                {
                        MeasurementTable.ID,
                        MeasurementTable.MEASUREMENT_PULSE,
                        MeasurementTable.MEASUREMENT_NOTES,
                        MeasurementTable.MEASUREMENT_MEASURED_ON
                };

        String orderAndLimit = " MEASUREMENT_MEASURED_ON DESC LIMIT 1 ";

        Cursor c = db.query(MeasurementTable.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                orderAndLimit);

        while (c.moveToNext()) {
            int i = 0;

            result = new Pulse();
            result.setMeasurementId(c.getInt(i));
            result.setPulse(c.getInt(++i));
            result.setMeasurementNotes(c.getString(++i));
            result.setMeasuredOn(MediPalUtils.toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));

            break;
        }

        return result;
    }

    private Measurement findLatestBloodPressure() {
        BloodPressure result = null;

        String selection = " MEASUREMENT_SYSTOLIC IS NOT NULL AND MEASUREMENT_DIASTOLIC IS NOT NULL";

        String[] columns = new String[]
                {
                        MeasurementTable.ID,
                        MeasurementTable.MEASUREMENT_SYSTOLIC,
                        MeasurementTable.MEASUREMENT_DIASTOLIC,
                        MeasurementTable.MEASUREMENT_NOTES,
                        MeasurementTable.MEASUREMENT_MEASURED_ON
                };

        String orderAndLimit = " MEASUREMENT_MEASURED_ON DESC LIMIT 1 ";

        Cursor c = db.query(MeasurementTable.TABLE_NAME,
                columns,
                selection,
                null,
                null,
                null,
                orderAndLimit);

        while (c.moveToNext()) {
            int i = 0;

            result = new BloodPressure();
            result.setMeasurementId(c.getInt(i));
            result.setSystolic(c.getInt(++i));
            result.setDiastolic(c.getInt(++i));
            result.setMeasurementNotes(c.getString(++i));
            result.setMeasuredOn(MediPalUtils.toDate_from_d_MMM_yyyy_H_mm(c.getString(++i)));

            break;
        }

        return result;
    }
}
