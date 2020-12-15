package exceptions;

/**
 * Exception to handle bad parameters put in TempLabel setter
 */
public class IncorrectValuesTemperature extends Exception {

    public IncorrectValuesTemperature(String errorMessage){
        super(errorMessage);
    }
    
}
