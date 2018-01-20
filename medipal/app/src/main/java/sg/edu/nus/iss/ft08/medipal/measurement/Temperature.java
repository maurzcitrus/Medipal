package sg.edu.nus.iss.ft08.medipal.measurement;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;


public class Temperature extends Measurement {

    private float temp;

    public float getTemperature(){
        return temp;
    }

    public void setTemperature(float temp){
        this.temp= temp;
    }

    public boolean isValidTemperature(){
        if (temp == 0.0f){
            return false;
        }
        else
            return true;
    }
    public boolean isValidTemperatureRange(){
        if (temp > 42.0f){
            return false;
        }
        else
            return true;
    }

    @Override
    public String getMeasurementTypeName() {
        return "Body Temperature";
    }

    @Override
    public String getValueDesription() {
        String description = null;
        if(measuredOn == null)
        {
            description = "Click to record your temperature.";
        } else {

            StringBuilder sb = new StringBuilder();
            sb.append(getTemperature());
            sb.append(" Degree Celsius");

            description = sb.toString();
        }
        return description;
    }
}
