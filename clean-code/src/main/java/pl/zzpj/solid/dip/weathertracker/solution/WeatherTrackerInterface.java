package pl.zzpj.solid.dip.weathertracker.solution;

public interface WeatherTrackerInterface {
    void setCurrentConditions(String weatherDescription, WeatherReceiver receiver);
}
