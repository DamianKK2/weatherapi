package com.example.weatherapi.model.impl;

import com.example.weatherapi.model.InsolationInterpreter;
import com.example.weatherapi.model.InsolationName;

public class PercentageInsolationInterpreter implements InsolationInterpreter{

    private int thresholdValue;

    public PercentageInsolationInterpreter(int thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    @Override
    public InsolationName interpret(int cloudPercentage) {
        return cloudPercentage >= thresholdValue ? InsolationName.cloudy : InsolationName.bright;
    }
    
}
