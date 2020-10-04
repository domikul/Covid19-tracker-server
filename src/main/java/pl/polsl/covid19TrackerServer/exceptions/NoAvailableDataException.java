package pl.polsl.covid19TrackerServer.exceptions;

import java.time.LocalDate;

public class NoAvailableDataException extends RuntimeException {
    public NoAvailableDataException(LocalDate date) {
        super("The data for " + date + " is not yet registered. Please choose another day.");
    }
}