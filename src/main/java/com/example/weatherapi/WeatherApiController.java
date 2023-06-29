package com.example.weatherapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.weatherapi.model.Weather;
import com.example.weatherapi.model.impl.CityImpl;
import com.example.weatherapi.service.WeatherService;

@RestController
@RequestMapping("weatherapi/v1")
public class WeatherApiController {

    @Autowired
    private WeatherService weatherService;

    @Value("${weatherapi.key}")
    private String apiKey;
    
	@GetMapping("/current")
	public ResponseEntity<Weather> getCurrent(@RequestParam String apiKey, @RequestParam String city) {
        
        if(!apiKey.equals(this.apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(city.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var myCity = new CityImpl(city);

        var response = weatherService.findCurrentWeather(myCity);
        
        return response;
	}
}

