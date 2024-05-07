package pl.zzpj.solid.dip.weathertracker.violation;


public class Emailer {
    public String generateWeatherAlert(String weatherConditions) {
        String alert = "It is " + weatherConditions;
        return alert;
    }
}
