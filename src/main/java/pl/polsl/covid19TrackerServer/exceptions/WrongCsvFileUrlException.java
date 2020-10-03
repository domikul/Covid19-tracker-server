package pl.polsl.covid19TrackerServer.exceptions;

public class WrongCsvFileUrlException extends RuntimeException{

    public WrongCsvFileUrlException(){
        super("Couldn't read CSV file. Check is URL correct.");
    }

}

