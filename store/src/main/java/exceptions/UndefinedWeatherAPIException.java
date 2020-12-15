package exceptions;

/**
 * Exception that handle if a bad API is put in parameter
 */
public class UndefinedWeatherAPIException extends Exception{
    public UndefinedWeatherAPIException(String errorMessage){
        super(errorMessage);
    }
}
