package com.example.weatherapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.weatherapi.model.City;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.model.impl.CityImpl;
import com.example.weatherapi.service.WeatherService;

@Component
public class WeatherCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherCommandLineRunner.class);
    private static final String DEFAULT_CITY = "Warszawa";

    @Autowired
    private WeatherService weatherService;

    @Override
    public void run(String... args) throws Exception {

        City city;
        if(args.length == 0) {
            city = new CityImpl(DEFAULT_CITY);
        } else {
            city = new CityImpl(args[0]);
        }

        ResponseEntity<Weather> response = weatherService.findCurrentWeather(city);

        if(!(HttpStatus.OK == response.getStatusCode())) {
            LOGGER.info("Application has been stopped.");
            throw new Exception("Weather Rest Api has been failed.");
        }

        Weather weather = response.getBody();
        
        LOGGER.info(String.format("Weather in %s is: temperature %s, insolation %s", city.getName(), weather.getTemperature().name(), weather.getInsolation().name()));
    }
}