package com.example.weatherapi.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.weatherapi.model.InsolationName;
import com.example.weatherapi.model.TemperatureInterpreter;
import com.example.weatherapi.api.weather.Response;
import com.example.weatherapi.api.weather.WeatherApi;
import com.example.weatherapi.model.City;
import com.example.weatherapi.model.InsolationInterpreter;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.model.impl.WeatherImpl;

public class WeatherApiService extends BaseWeatherService {

    @Autowired
    private WeatherApi apiService;

    @Autowired
    private TemperatureInterpreter temperatureInterpreter;

    @Autowired
    private InsolationInterpreter insolationInterpreter;

    @Override
    public ResponseEntity<Weather> findCurrentWeatherByApi(City city) throws RestClientException{

        if(city == null || city.getName() == null || city.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ResponseEntity<Response> response = apiService.findCurrentWeather(city.getName());

        var temp = temperatureInterpreter.interpret(response.getBody().current().temp_c());
        var insolation = insolationInterpreter.interpret(response.getBody().current().cloud());
        
        Weather responseWeather = new WeatherImpl(temp, insolation);
            
        return ResponseEntity.ok(responseWeather);
    }
}