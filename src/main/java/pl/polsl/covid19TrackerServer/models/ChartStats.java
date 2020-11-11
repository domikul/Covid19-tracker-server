package pl.polsl.covid19TrackerServer.models;

import pl.polsl.covid19TrackerServer.models.enumerations.FileType;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;


public class ChartStats {

    private String country;
    private FileType status;
    private Map<LocalDate, Integer> casesMap;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChartStats that = (ChartStats) o;
        return country.equals(that.country) &&
                status == that.status &&
                casesMap.equals(that.casesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, status, casesMap);
    }
}
