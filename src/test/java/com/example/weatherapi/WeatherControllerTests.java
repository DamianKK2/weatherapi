package com.example.weatherapi;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.weatherapi.model.InsolationName;
import com.example.weatherapi.model.TemperatureName;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.model.impl.CityImpl;
import com.example.weatherapi.model.impl.WeatherImpl;
import com.example.weatherapi.service.WeatherService;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherApiController.class)
public class WeatherControllerTests {

    private static final Weather WARM = new WeatherImpl(TemperatureName.warm, InsolationName.bright);
    private static final String CITY = "Warszawa";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService service;

    @Value("${weatherapi.key}") 
    private String apiKey;

    @Before
    public void setUp() {
        
        ResponseEntity<Weather> expectedWeather = ResponseEntity.ok(WARM);
        
        when(service.findCurrentWeather(any(CityImpl.class)))
          .thenReturn(expectedWeather);
    }
    
    @Test
    public void givenCityAndApiKey_whenFindCurrentWeather()
      throws Exception {
        
        RequestBuilder builder = MockMvcRequestBuilders
          .get("/weatherapi/v1/current?apiKey={apiKey}&city={city}", apiKey, CITY);
        
        mvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(WARM.getTemperature().name()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.insolation").value(WARM.getInsolation().name()));
    }

    @Test
    public void noCity_whenFindCurrentWeather()
      throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders
          .get("/weatherapi/v1/current?apiKey={apiKey}", apiKey);
        
        mvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void noApiKey_whenFindCurrentWeather()
      throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders
          .get("/weatherapi/v1/current");
        
        mvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void incorrectApiKey_whenFindCurrentWeather()
      throws Exception {

        String incorrectApiKey = "testApiKey";
        RequestBuilder builder = MockMvcRequestBuilders
          .get("/weatherapi/v1/current?apiKey={apiKey}&city={city}", incorrectApiKey, CITY);
        
        mvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void incorrectCity_whenFindCurrentWeather()
      throws Exception {
        
        String incorrectCityName = "Warsz";

        when(service.findCurrentWeather(any(CityImpl.class)))
          .thenReturn(ResponseEntity.badRequest().build());
        
        RequestBuilder builder = MockMvcRequestBuilders
          .get("/weatherapi/v1/current?apiKey={apiKey}&city={city}", apiKey, incorrectCityName);
        
        mvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
} 
