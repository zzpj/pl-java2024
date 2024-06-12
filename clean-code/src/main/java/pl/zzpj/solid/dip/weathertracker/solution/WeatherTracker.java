package pl.zzpj.solid.dip.weathertracker.solution;

import java.util.Objects;

public class WeatherTracker implements WeatherTrackerInterface {
    String currentConditions;

    @Override
    public void setCurrentConditions(String weatherDescription, WeatherReceiver receiver) {
        this.currentConditions = weatherDescription;
        if (Objects.equals(weatherDescription, "rainy")) {
            String alert = receiver.generateWeatherAlert(weatherDescription);
            System.out.print(alert);
        }
        if (Objects.equals(weatherDescription, "sunny")) {
            String alert = receiver.generateWeatherAlert(weatherDescription);
            System.out.print(alert);
        }
    }
}
