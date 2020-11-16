package pl.polsl.covid19TrackerServer.exceptions;

public class IncorrectDateException extends RuntimeException {
    public IncorrectDateException() {
        super("Daty są wybrane niepoprawnie.");
    }
}
