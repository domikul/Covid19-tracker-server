package pl.polsl.covid19TrackerServer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class WrongCsvFileUrlAdvice {
    @ExceptionHandler(WrongCsvFileUrlException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String wrongCsvFileUrlHandler(WrongCsvFileUrlException ex){
        return ex.getMessage();
    }
}
