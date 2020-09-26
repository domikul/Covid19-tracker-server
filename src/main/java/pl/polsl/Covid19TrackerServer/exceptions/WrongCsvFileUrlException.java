package pl.polsl.Covid19TrackerServer.exceptions;

import java.time.LocalDate;

public class WrongCsvFileUrlException extends RuntimeException{

    public WrongCsvFileUrlException(){
        super("Couldn't read CSV file. Check is URL correct.");
    }

}

