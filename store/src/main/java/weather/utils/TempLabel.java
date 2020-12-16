package weather.utils;

import com.fasterxml.jackson.annotation.JsonTypeName;

import exceptions.IncorrectValuesTemperature;

@JsonTypeName("label")

/**
 * 
 * Enum to get the label according to temperature. 
 * Because sensation of heat is subjective, it can be changed via setRange method
 * Moreover, it varies depending on the units : metric or imperial
 * 
 */
public enum TempLabel {
    HOT(30, 50),
    WARM(23, 30),
    MILD(16, 23),
    CHILLY(5,16),
    COLD(-5, 5),
    FREEZE(-30, -5),
    OTHER(-100, -30);

    public double[] range = new double[2];

    private TempLabel(double min, double max){
        this.range[0] = min;
        this.range[1] = max;
    }

    /**
     * 
     * set new range of temperature according to developer needs.
     * Throws an error if values are bad setted.
     * 
     * @param range
     * @throws IncorrectValuesTemperature
     */
    public void setRange(double[] range) throws IncorrectValuesTemperature {
        if(range[0] >= range[1]){
            throw new IncorrectValuesTemperature("Minimal temperature should be less than maximal temperature when setting values to temperature labels.");
        }
        this.range = range;
    }

    /**
     * 
     * return the temp label according to temperature given in parmeter
     * 
     * @param temp
     * @return
     */
    public static TempLabel getLabel(double temp){
        TempLabel labels[] = TempLabel.values();
        for(TempLabel label: labels){
            if(temp > label.range[0] && temp <= label.range[1]){
                return label;
            }
        }
        return OTHER;
    }
}
