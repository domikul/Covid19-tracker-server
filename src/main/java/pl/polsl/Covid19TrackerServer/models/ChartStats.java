package pl.polsl.Covid19TrackerServer.models;

import pl.polsl.Covid19TrackerServer.models.enumerations.FileType;

import java.time.LocalDate;
import java.util.Map;


public class ChartStats {

    private String country;
    FileType status;
    Map<LocalDate, Integer> casesMap;

    public ChartStats(String country, FileType status, Map<LocalDate, Integer> casesMap) {
        this.country = country;
        this.status = status;
        this.casesMap = casesMap;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public FileType getStatus() {
        return status;
    }

    public void setStatus(FileType status) {
        this.status = status;
    }

    public Map<LocalDate, Integer> getCasesMap() {
        return casesMap;
    }

    public void setCasesMap(Map<LocalDate, Integer> casesMap) {
        this.casesMap = casesMap;
    }
}