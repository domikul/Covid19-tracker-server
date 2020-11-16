package pl.polsl.covid19TrackerServer.exceptions;

import java.time.LocalDate;

public class NoAvailableDataException extends RuntimeException {
    public NoAvailableDataException(LocalDate date) {
        super("Dane na dzień " + date + " nie są jeszcze dostępne. Proszę wybrać inny dzień.");
    }
}
