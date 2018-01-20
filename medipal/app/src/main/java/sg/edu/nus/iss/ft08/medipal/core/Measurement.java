package sg.edu.nus.iss.ft08.medipal.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Measurement {
    // Measurement is the supper class of 'blood pressure', 'body temperature', 'pulse', 'weight'
    protected int id;
    protected String notes;
    protected Date measuredOn;

    public int getMeasurementId() {
        return id;
    }

    public void setMeasurementId(int id) {
        this.id = id;
    }

    public String getMeasurementNotes() {
        return notes;
    }

    public void setMeasurementNotes(String notes) {
        this.notes = notes;
    }

    public Date getMeasuredOn() {
        return measuredOn;
    }

    public void setMeasuredOn(Date measuredOn) {
        this.measuredOn = measuredOn;
    }

    public String getMeasurementTypeName() { return null;}

    public String getValueDesription(){return null;}
}
