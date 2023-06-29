package com.example.weatherapi.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.function.EntityResponse;

import com.example.weatherapi.api.ninjas.NinjasWeatherApi;
import com.example.weatherapi.api.ninjas.Response;
import com.example.weatherapi.model.InsolationInterpreter;
import com.example.weatherapi.model.InsolationName;
import com.example.weatherapi.model.TemperatureInterpreter;
import com.example.weatherapi.model.TemperatureName;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.model.impl.CityImpl;
import com.example.weatherapi.model.impl.WeatherImpl;
import com.example.weatherapi.service.WeatherService;

@RunWith(SpringRunner.class)
public class NinjasWeatherServiceTests {

    @TestConfiguration
    static class NinjasWeatherServiceImplTestContextConfiguration {
 
        @Bean
        public WeatherService apiService() {
            return new NinjasWeatherService();
        }
    }

    @Autowired
    private WeatherService weatherService;

    @MockBean
    private NinjasWeatherApi api;

    @MockBean
    private TemperatureInterpreter interpreter;
    
    @MockBean
    private InsolationInterpreter insInterpreter;
    
    @Before
    public void setUp() {
        Response warmWeather = new Response(0.0,0,20, 0, 0, 15, 30, 0, 0, 24);

        ResponseEntity<Response> expectedWeather = ResponseEntity.ok(warmWeather);

        Mockito.when(api.findCurrentWeather("Warszawa"))
          .thenReturn(expectedWeather);

        Mockito.when(api.findCurrentWeather("Warsz"))
          .thenThrow(new RestClientException("City has not been found."));

        Mockito.when(interpreter.interpret(warmWeather.temp()))
          .thenReturn(TemperatureName.warm);
        
        Mockito.when(insInterpreter.interpret(warmWeather.cloud_pct()))
        .thenReturn(InsolationName.bright);
    }

    @Test
    public void whenValidCityAndTemperatureInterpreted_thenResponseOk() {
        ResponseEntity<Weather> found = weatherService.findCurrentWeather(new CityImpl("Warszawa"));
    
        Assert.assertEquals(HttpStatus.OK, found.getStatusCode());
        Assert.assertEquals(found.getBody().getTemperature(),
        TemperatureName.warm);
        Assert.assertEquals(found.getBody().getInsolation(),
            InsolationName.bright);
    }

    @Test
    public void whenNoCity_thenBadRequest() {
        ResponseEntity<Weather> found = weatherService.findCurrentWeather(new CityImpl(""));
    
        Assert.assertEquals(HttpStatus.BAD_REQUEST, found.getStatusCode());
    }

    @Test
    public void whenInvalidCity_thenBadRequest() {
        ResponseEntity<Weather> found = weatherService.findCurrentWeather(new CityImpl("Warsz"));
    
        Assert.assertEquals(HttpStatus.BAD_REQUEST, found.getStatusCode());
    }
}
