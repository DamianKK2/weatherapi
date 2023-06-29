package com.example.weatherapi.model.impl;

import com.example.weatherapi.model.TemperatureInterpreter;
import com.example.weatherapi.model.TemperatureName;

public class ThresholdTemperatureInterpreter implements TemperatureInterpreter {

    private double thresholdValue;

    public ThresholdTemperatureInterpreter(double thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    @Override
    public TemperatureName interpret(double temperature) {
        
        return temperature > thresholdValue
                ? TemperatureName.warm
                : TemperatureName.cold;
    }
}
