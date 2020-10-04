package pl.polsl.covid19TrackerServer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IncorrectDateAdvice {

    @ExceptionHandler(IncorrectDateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String incorrectDateHandler(IncorrectDateException ex) {
        return ex.getMessage();
    }
}