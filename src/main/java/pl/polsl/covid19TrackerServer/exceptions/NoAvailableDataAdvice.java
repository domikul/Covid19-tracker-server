package pl.polsl.covid19TrackerServer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoAvailableDataAdvice {

    @ExceptionHandler(NoAvailableDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noAvailableDataHandler(NoAvailableDataException ex) {
        return ex.getMessage();
    }
}