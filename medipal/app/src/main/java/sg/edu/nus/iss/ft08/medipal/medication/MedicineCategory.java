package sg.edu.nus.iss.ft08.medipal.medication;


import sg.edu.nus.iss.ft08.medipal.core.MediPalConstants;

public class MedicineCategory {
    private int categoryId;
    private String categoryCode;
    private String categoryName;
    private String categoryReminderFlag;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryReminderFlag() {
        return categoryReminderFlag;
    }

    public void setCategoryReminderFlag(String categoryReminderFlag) {
        this.categoryReminderFlag = categoryReminderFlag;
    }

    public boolean isReminderEnabled(){
        if(categoryReminderFlag == null || categoryReminderFlag.isEmpty())
            return false;

        return MediPalConstants.FLAG_YES.equalsIgnoreCase(categoryReminderFlag);
    }

    public void enableReminder(){
        categoryReminderFlag = MediPalConstants.FLAG_YES;
    }

    public void disableReminder(){
        categoryReminderFlag = MediPalConstants.FLAG_NO;
    }

    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MedicineCategory) {
            MedicineCategory that = (MedicineCategory) obj;
            return this.getCategoryId() == that.getCategoryId();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Integer.toString(categoryId).hashCode();
    }
}
