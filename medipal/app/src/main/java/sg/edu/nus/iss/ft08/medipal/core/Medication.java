package sg.edu.nus.iss.ft08.medipal.core;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.ft08.medipal.medication.MedicineCategory;

public class Medication {
    private Medicine medicine;
    private List<MedicineCategory> categories;

    public Medication() {
        medicine = new Medicine();
    }

    public Medicine getMedicine() {
        if (medicine == null) {
            medicine = new Medicine();
        }
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getMedicineName() {
        return medicine.getName();
    }

    public void setMedicineName(String medicineName) {
        medicine.setName(medicineName);
    }

    public int getMedicineId() {
        return medicine.getId();
    }

    public String getMedicineStatus() {
        return medicine.getStatus();
    }

    public List<MedicineCategory> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories;
    }

    public void setCategories(List<MedicineCategory> categories) {
        this.categories = categories;
    }

    public int getMedicineCategoryIndex(){
        if(medicine == null)
            return -1;

        if(categories == null || categories.isEmpty())
            return -1;

        MedicineCategory temp = new MedicineCategory();
        temp.setCategoryId(medicine.getCategoryId());

        return categories.indexOf(temp);
    }

}
