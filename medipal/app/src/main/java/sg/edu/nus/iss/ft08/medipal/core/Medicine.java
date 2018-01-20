package sg.edu.nus.iss.ft08.medipal.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Medicine {
    private static final int UNDEFINED = -1;
    private int id;
    private String name;
    private String description;
    private int categoryId;
    private int reminderId;
    private String reminderFlag;
    private int quantity;
    private int frequency;
    private int dosage;
    private Date dateIssued;
    private int threshold;
    private int expiryFactor;
    private Reminder reminder;
    private List<Consumption> consumptions;
    private boolean hasNewConsumption;

    public Medicine() {
        categoryId = UNDEFINED;
        reminderId = UNDEFINED;
        quantity = 0;
        frequency = UNDEFINED;
        dosage = UNDEFINED;
        threshold = UNDEFINED;
        hasNewConsumption = false;
    }

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderFlag() {
        return reminderFlag;
    }

    public void setReminderFlag(String reminderFlag) {
        this.reminderFlag = reminderFlag;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getExpiryFactor() {
        return expiryFactor;
    }

    public void setExpiryFactor(int expiryFactor) {
        this.expiryFactor = expiryFactor;
    }

    public Reminder getReminder() {
        if (reminder == null) {
            reminder = new Reminder();
        }

        return reminder;
    }

    public void setReminder(Reminder reminder) {
        if (reminder != null) {
            setReminderId(reminder.getId());
        }

        this.reminder = reminder;
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

    public boolean isExpired() {
        if (getDateIssued() == null)
            return false;

        if (expiryFactor <= 0)
            return false;

        Calendar expiryCalendar = Calendar.getInstance();

        int issuedYear = getDateIssued().getYear();
        int issuedMonth = getDateIssued().getMonth();
        int issuedDate = getDateIssued().getDate();

        expiryCalendar.set(1900 + issuedYear, issuedMonth, issuedDate); // why +1900? see https://developer.android.com/reference/java/util/Date.html
        expiryCalendar.add(Calendar.MONTH, expiryFactor);

        Date expiryDate = expiryCalendar.getTime();
        Date today = Calendar.getInstance().getTime();

        if (today.after(expiryDate))
            return true;
        else
            return false;
    }

    public boolean isLowQuantity() {
        if (threshold <= -1)
            return false;

        if (quantity > threshold)
            return false;
        else
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

    public boolean isValidRemindToTakeStartTime() {
        if (!isReminderEnabled())
            return true;

        Reminder r = getReminder();
        if (r.getStartTime() == null)
            return false;
        else
            return true;
    }

    public void enableReminderToTake(Date startTime, int frequency, int interval){
        setReminderFlag(MediPalConstants.FLAG_YES);

        Reminder r = getReminder();
        r.setStartTime(startTime);
        r.setFrequency(frequency);
        r.setInterval(interval);
    }

    public void disableReminderToTake() {
        setReminderFlag(MediPalConstants.FLAG_NO);

        Reminder r = getReminder();
        r.setStartTime(null);
        r.setFrequency(UNDEFINED);
        r.setInterval(UNDEFINED);
    }

    public boolean isValidRemindToTakeFrequency() {
        if (!isReminderEnabled())
            return true;

        Reminder r = getReminder();
        if (r.getFrequency() < 1)
            return false;
        else
            return true;
    }

    public boolean isValidRemindToTakeInterval() {
        if (!isReminderEnabled())
            return true;

        Reminder r = getReminder();
        if (r.getInterval() < 1)
            return false;
        else
            return true;
    }

    public void consume() {
        Consumption c = new Consumption();
        c.setMedicineId(getId());
        c.setQuantity(getDosage());
        c.setConsumedOn(MediPalUtils.getToday());

        getConsumptions().add(c);

        int currentQuantity = getQuantity();
        int dosage = getDosage();
        if(currentQuantity > 0){
            int quantityAfter = currentQuantity - dosage;
            setQuantity(quantityAfter);
        }

        hasNewConsumption = true;
    }

    public List<Consumption> getConsumptions() {
        if(consumptions == null){
            consumptions = new ArrayList<Consumption>();
        }
        return consumptions;
    }

    public List<Consumption> getTodayConsumptions(){
        if(consumptions == null){
            return new ArrayList<Consumption>();
        }

        List<Consumption> todayConsumptions = new ArrayList<Consumption>();
        Date today = MediPalUtils.getToday();
        String today_dd_MMM_yyyy = MediPalUtils.toString_dd_MMM_yyyy(today);

        for (Consumption c :
                consumptions) {

            String consumeDate_dd_MMM_yyyy = MediPalUtils.toString_dd_MMM_yyyy(c.getConsumedOn());

            if(today_dd_MMM_yyyy.equalsIgnoreCase(consumeDate_dd_MMM_yyyy))
            {
                todayConsumptions.add(c);
            }
        }

        return todayConsumptions;
    }

    public void setConsumptions(List<Consumption> consumptions) {
        this.consumptions = consumptions;
    }

    public boolean hasNewConsumption() {
        return hasNewConsumption;
    }

    public Consumption getNewConsumption(){
        if(!hasNewConsumption)
            return null;

        int count = getConsumptions().size();
        if(count == 0)
            return null;

        int last = count - 1;
        return getConsumptions().get(last);
    }

    public void resetConsumptionStatus() {
        hasNewConsumption = false;
    }

    public boolean isOutOfStock() {
        return quantity <= 0;
    }

    public boolean isConsumable(){
        return !isExpired() &&
                !isOutOfStock();
    }

    public String getStatus() {
        if(isExpired()){
            return "This is expired.";
        }

        if(isLowQuantity()){
            return "Need to top-up.";
        }

        if(isOutOfStock())
        {
            return "Out of stock.  Please top-up.";
        }

        return null;
    }

    public void disableReminderToTopup(){
        setThreshold(UNDEFINED);
    }

    public boolean isTopupReminderEnabled(){
        return threshold > UNDEFINED;
    }
}
