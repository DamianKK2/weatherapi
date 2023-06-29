package com.example.weatherapi.api.ninjas;

public record Response(double wind_speed, int wind_degrees, int temp, int humidity, long sunset, int min_temp, int cloud_pct, int feels_like, long sunrise, int max_temp) {
    
}
