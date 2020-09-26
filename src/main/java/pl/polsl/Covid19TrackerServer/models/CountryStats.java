package pl.polsl.Covid19TrackerServer.models;

import java.util.Objects;

public class CountryStats {
    private String country;
    private int confirmedCases;
    private int recoveredCases;
    private int deathsCases;
    private double latitude;
    private double longitude;

    public CountryStats() {}

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CountryStats{" +
                "country='" + country + '\'' +
                ", confirmedCases=" + confirmedCases +
                ", recoveredCases=" + recoveredCases +
                ", deathsCases=" + deathsCases +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
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
        return Objects.hash(country, confirmedCases, recoveredCases, deathsCases, latitude, longitude);
    }
}