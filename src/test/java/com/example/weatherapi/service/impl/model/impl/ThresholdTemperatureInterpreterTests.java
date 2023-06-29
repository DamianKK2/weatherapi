package com.example.weatherapi.service.impl.model.impl;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.weatherapi.model.TemperatureInterpreter;
import com.example.weatherapi.model.TemperatureName;
import com.example.weatherapi.model.impl.ThresholdTemperatureInterpreter;

@RunWith(SpringRunner.class)
public class ThresholdTemperatureInterpreterTests {

    private static final double AVERAGE_TEMPERATURE = 9.5;

    @TestConfiguration
    static class NinjasWeatherServiceImplTestContextConfiguration {
 
        @Bean
        public TemperatureInterpreter interpreter() {
            return new ThresholdTemperatureInterpreter(AVERAGE_TEMPERATURE);
        }
    }

    @Autowired
    private TemperatureInterpreter interpreter;

    @Test
    public void whenTemperatureLowerThanAverage_thenCold() {
        TemperatureName tName = interpreter.interpret(5.0);

        Assert.assertEquals(TemperatureName.cold, tName);
    }

    @Test
    public void whenTemperatureGreaterThanAverage_thenWarm() {
        TemperatureName tName = interpreter.interpret(15.0);

        Assert.assertEquals(TemperatureName.warm, tName);
    }
}
