package sg.edu.nus.iss.ft08.medipal.core;

import java.util.Date;

public class Appointment {
    private int id;
    private String location;
    private String description;
    private Date appointmentDateTime;
    private String reminderFlag;

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {return location;}

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(Date appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;}

    public void setReminderFlag(String reminderFlag) {this.reminderFlag = reminderFlag;}

    public String getReminderFlag() {
        return reminderFlag;
    }

    public boolean isValidLocation() {
        if (location == null)
            return false;

        if (location.isEmpty())
            return false;

        if (location.length() > 50)
            return false;

        return true;
    }

    public boolean isReminderEnabled() {
        if (reminderFlag == null || reminderFlag.isEmpty())
            return false;

        if (reminderFlag.equalsIgnoreCase(MediPalConstants.FLAG_YES))
            return true;
        else if (reminderFlag.equalsIgnoreCase(MediPalConstants.FLAG_NO))
            return false;
        else
            return false;
    }

    public boolean isValidDescription(){
        if (description == null)
            return false;

        if (description.isEmpty())
            return false;

        if (description.length() > 255)
            return false;

        return true;
    }
}
