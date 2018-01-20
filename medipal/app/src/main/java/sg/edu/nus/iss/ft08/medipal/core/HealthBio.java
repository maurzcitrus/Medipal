package sg.edu.nus.iss.ft08.medipal.core;

import java.util.Date;

public class HealthBio {
    private int id;
    private String condition;
    private Date startDate;
    private String conditionType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public boolean isValidCondition(){
        if (condition == null)
            return false;

        if (condition.isEmpty())
            return false;

        if (condition.length() > 50)
            return false;

        return true;
    }
}
