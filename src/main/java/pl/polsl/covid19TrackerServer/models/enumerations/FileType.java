package pl.polsl.covid19TrackerServer.models.enumerations;

public enum FileType {

    CONFIRMED("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv"),
    DEATHS("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv"),
    RECOVERED("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv");

    private String url;

    FileType(String url) {
        this.url = url;
    }

    public String returnURL() {
        return url;
    }

}
