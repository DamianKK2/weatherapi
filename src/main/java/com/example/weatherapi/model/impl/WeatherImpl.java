package com.example.weatherapi.model.impl;

import com.example.weatherapi.model.InsolationName;
import com.example.weatherapi.model.TemperatureName;
import com.example.weatherapi.model.Weather;

public class WeatherImpl implements Weather {

    private TemperatureName temperature;
    private InsolationName insolation;

    public WeatherImpl(TemperatureName temperature, InsolationName insolation) {
        this.temperature = temperature;
        this.insolation = insolation;
    }

    @Override
    public TemperatureName getTemperature() {
        return temperature;
    }

    @Override
    public InsolationName getInsolation() {
        return insolation;
    }
    
}