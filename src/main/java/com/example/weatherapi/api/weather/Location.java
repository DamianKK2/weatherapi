package com.example.weatherapi.api.weather;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Location(double lat, double lon, String name) {
    
}
