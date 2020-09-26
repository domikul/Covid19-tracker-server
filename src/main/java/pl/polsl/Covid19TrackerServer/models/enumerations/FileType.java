package pl.polsl.Covid19TrackerServer.models.enumerations;

import pl.polsl.Covid19TrackerServer.exceptions.WrongCsvFileUrlException;

public enum FileType {

    CONFIRMED, DEATHS, RECOVERED;

    public String returnURL(FileType typeOfFile){
        if(typeOfFile.equals(FileType.CONFIRMED))
            return "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
        else if(typeOfFile.equals(FileType.DEATHS))
            return "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
        else if(typeOfFile.equals(FileType.RECOVERED))
            return "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
        else
            throw new WrongCsvFileUrlException();
    }

}
