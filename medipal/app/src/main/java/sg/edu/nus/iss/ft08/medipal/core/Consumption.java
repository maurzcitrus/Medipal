package sg.edu.nus.iss.ft08.medipal.core;

import java.util.Date;

public class Consumption {
    private int id;
    private int medicineId;
    private int quantity; // aka dosage
    private Date consumedOn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getConsumedOn() {
        return consumedOn;
    }

    public void setConsumedOn(Date consumedOn) {
        this.consumedOn = consumedOn;
    }
}
