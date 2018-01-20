package sg.edu.nus.iss.ft08.medipal.core;

import java.util.Comparator;

public class EmergencyContact {
    private int id;
    private String name;
    private String contactNo;
    private String contactType;
    private String description;
    private int priority;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isValidName() {
        if (name == null)
            return false;

        if (name.isEmpty())
            return false;

        if (name.length() > 20)
            return false;

            return true;
    }
    public boolean isValidDescription() {

        if ( description!=null && description.equals(""))
            return false;

        if (description != null && description.length() > 255)
            return false;

            return true;
    }
    public boolean isValidContact() {
        if (contactNo != null && contactNo.length()==8)
            return true;

        return false;
    }

}
