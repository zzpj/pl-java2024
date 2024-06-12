package pl.zzpj.solid.dip.weathertracker.solution;

public abstract class WeatherReceiver implements WeatherReceiverInterface {
    @Override
    public String generateWeatherAlert(String weatherConditions) {
        String alert = "It is " + weatherConditions;
        return alert;
    }

}
