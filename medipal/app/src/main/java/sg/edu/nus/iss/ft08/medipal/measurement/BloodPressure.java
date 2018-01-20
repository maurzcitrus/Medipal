package sg.edu.nus.iss.ft08.medipal.measurement;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;


public class BloodPressure extends Measurement {
    // Subclass of measurement class

    private int systolic;
    private int diastolic;

    public int getSystolic(){
        return systolic;
    }

    public void setSystolic(int systolic){
        this.systolic = systolic;
    }

    public int getDiastolic(){
        return  diastolic;
    }


    public void setDiastolic(int diastolic){
        this.diastolic = diastolic;
    }

    @Override
    public String getMeasurementTypeName() {
        return "Blood Pressure";
    }

    @Override
    public String getValueDesription() {
        String description = null;
        if(measuredOn == null)
        {
            description = "Click to record your blood pressure.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Systolic: ");
            sb.append(getSystolic());
            sb.append(" mmHg, ");
            sb.append("Diastolic: ");
            sb.append(getDiastolic());
            sb.append(" mmHg");



            description = sb.toString();
        }

        return description;
    }

    public boolean isValidSystolic(){
        if (systolic == 0){
            return false;
        }
        if(systolic < 90){
            return false;
        }
        if (systolic > 240) {
            return false;
        }
        return true;
    }

    public boolean isValidDiastolic(){
        if (diastolic == 0){
            return false;
        }
        if(diastolic < 40){
            return false;
        }
        if (diastolic > 160) {
            return false;
        }
        else
            return true;
    }
}

