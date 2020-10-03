package pl.polsl.covid19TrackerServer.models;

import java.util.Objects;

public class CountryStats {

    private String country;
    private int confirmedCases;
    private int recoveredCases;
    private int deathsCases;


    public CountryStats(String country, int confirmedCases, int recoveredCases, int deathsCases) {
        this.country = country;
        this.confirmedCases = confirmedCases;
        this.recoveredCases = recoveredCases;
        this.deathsCases = deathsCases;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public int getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(int recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public int getDeathsCases() {
        return deathsCases;
    }

    public void setDeathsCases(int deathsCases) {
        this.deathsCases = deathsCases;
    }

    @Override
    public String toString() {
        return "CountryStats{" +
                "country='" + country + '\'' +
                ", confirmedCases=" + confirmedCases +
                ", recoveredCases=" + recoveredCases +
                ", deathsCases=" + deathsCases +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryStats that = (CountryStats) o;
        return Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, confirmedCases, recoveredCases, deathsCases);
    }
}