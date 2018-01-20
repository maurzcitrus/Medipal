package sg.edu.nus.iss.ft08.medipal.measurement;

import sg.edu.nus.iss.ft08.medipal.core.Measurement;


public class Pulse extends Measurement {

private int pulse;

    public int getPulse(){
        return pulse;
    }

    public void setPulse(int pulse){
        this.pulse = pulse;
    }

    public boolean inValidPulse(){
        if (pulse == 0){
            return false;
        }
        if(pulse > 200){
            return false;
        }
        else
            return true;
    }

    @Override
    public String getMeasurementTypeName() {
        return "Pulse";
    }

    @Override
    public String getValueDesription() {
        String description = null;
        if(measuredOn == null)
        {
            description = "Click to record your pulse.";
        } else {

            StringBuilder sb = new StringBuilder();
            sb.append(getPulse());
            sb.append(" bpm");

            description = sb.toString();
        }

        return description;

    }

}
