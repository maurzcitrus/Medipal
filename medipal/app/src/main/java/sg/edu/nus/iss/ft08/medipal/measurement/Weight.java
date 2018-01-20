package sg.edu.nus.iss.ft08.medipal.measurement;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;


public class Weight extends Measurement {


    private int weight;

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public boolean inValidWeight(){
        if (weight == 0){
            return false;
        }
        if(weight > 150){
            return false;
        }
        else
            return true;
    }

    @Override
    public String getMeasurementTypeName() {
        return "Body Weight";
    }

    @Override
    public String getValueDesription() {
        String description = null;
        if (measuredOn == null) {
            description = "Click to record your weight.";
        } else {

            StringBuilder sb = new StringBuilder();
            sb.append(getWeight());
            sb.append(" kg");

            description = sb.toString();
        }

        return description;
    }

}
