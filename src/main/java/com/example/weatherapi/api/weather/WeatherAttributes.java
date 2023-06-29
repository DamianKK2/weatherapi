package com.example.weatherapi.api.weather;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherAttributes(double temp_c, int cloud, int is_day, 
        double wind_kph, int pressure_mb) {
     
}
