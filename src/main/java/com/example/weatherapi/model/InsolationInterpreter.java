package com.example.weatherapi.model;

public interface InsolationInterpreter {
    public InsolationName interpret(int cloudPercentage);
}
