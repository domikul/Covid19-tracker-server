package pl.polsl.covid19TrackerServer.exceptions;

public class IncorrectDateException extends RuntimeException {
    public IncorrectDateException() {
        super("The dates are not selected correctly.");
    }
}