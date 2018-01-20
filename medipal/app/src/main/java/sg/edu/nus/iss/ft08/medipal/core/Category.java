package sg.edu.nus.iss.ft08.medipal.core;

public class Category {

    private int id;
    private String code;
    private String name;
    private String description;
    private String reminderFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.isEmpty())
            this.code = code;
        else
            this.code = code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty())
            this.name = name;
        else
            this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty())
            this.description = description;
        else
            this.description = description.trim();
    }

    public String getReminderFlag() {
        return reminderFlag;
    }

    public void setReminderFlag(String reminderFlag) {
        this.reminderFlag = reminderFlag;
    }

    public boolean isValidCode() {
        if (code == null)
            return false;

        if (code.isEmpty())
            return false;

        if (code.length() > 4)
            return false;

        return true;
    }

    public boolean isValidName() {
        if (name == null)
            return false;

        if (name.isEmpty())
            return false;

        if (name.length() > 50)
            return false;

        return true;
    }

    public boolean isValidDescription() {
        if (description != null && description.length() > 255)
            return false;

        return true;
    }
}
