package exceptions;

/**
 * Exception to handle bad parameters put in TempLabel setter
 */
public class IncorrectValuesTemperature extends Exception {

    private static final long serialVersionUID = -1166332606496322412L;

    public IncorrectValuesTemperature(String errorMessage) {
        super(errorMessage);
        System.exit(1);
    }
    
}
