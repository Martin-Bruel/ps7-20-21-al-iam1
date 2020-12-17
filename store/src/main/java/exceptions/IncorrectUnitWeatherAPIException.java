package exceptions;

public class IncorrectUnitWeatherAPIException extends Exception {

    private static final long serialVersionUID = -1391970055637503307L;

    public IncorrectUnitWeatherAPIException(String errorMessage) {
        super(errorMessage);
    }
}
