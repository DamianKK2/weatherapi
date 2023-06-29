package com.example.weatherapi.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.example.weatherapi.model.TemperatureInterpreter;
import com.example.weatherapi.api.ninjas.NinjasWeatherApi;
import com.example.weatherapi.api.ninjas.Response;
import com.example.weatherapi.model.City;
import com.example.weatherapi.model.InsolationInterpreter;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.model.impl.WeatherImpl;

public class NinjasWeatherService extends BaseWeatherService {

    @Autowired
    private NinjasWeatherApi weatherApi;

    @Autowired
    private TemperatureInterpreter temperatureInterpreter;

    @Autowired
    private InsolationInterpreter insolationInterpreter;
    
    @Override
    public ResponseEntity<Weather> findCurrentWeatherByApi(City city) throws RestClientException{

        if(city == null || city.getName() == null || city.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ResponseEntity<Response> response = weatherApi.findCurrentWeather(city.getName());

        var temp = temperatureInterpreter.interpret(response.getBody().temp());
        var insolation = insolationInterpreter.interpret(response.getBody().cloud_pct());

        Weather responseWeather = new WeatherImpl(temp, insolation);
            
        return ResponseEntity.ok(responseWeather);


    }

}