package com.example.weatherapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;

import com.example.weatherapi.service.WeatherService;
import com.example.weatherapi.api.ninjas.NinjasWeatherApi;
import com.example.weatherapi.api.weather.WeatherApi;
import com.example.weatherapi.model.InsolationInterpreter;
import com.example.weatherapi.model.TemperatureInterpreter;
import com.example.weatherapi.model.impl.PercentageInsolationInterpreter;
import com.example.weatherapi.model.impl.ThresholdTemperatureInterpreter;
import com.example.weatherapi.service.impl.WeatherApiService;

@Configuration
public class WeatherApiConfiguration {
    
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate template = builder.build();
		template.setErrorHandler(new DefaultResponseErrorHandler());
		return template;
	}
	
	@Bean
	public TemperatureInterpreter temperatureInterpreter(@Value("${weatherapi.averageTemperaturePoland2022}") double averageTemperature) {
		return new ThresholdTemperatureInterpreter(averageTemperature);
	}

	@Bean
	public InsolationInterpreter insolationInterpreter(@Value("${weatherapi.insolationPercentageThreshold}") int insolationPercentageThreshold) {
		return new PercentageInsolationInterpreter(insolationPercentageThreshold);
	}

	@Bean
	public WeatherApi weatherapi() {
		return new WeatherApi();
	}

	@Bean
	public NinjasWeatherApi ninjasWeatherapi() {
		return new NinjasWeatherApi();
	}

	@Bean
	public WeatherService myWeatherService() {
		return new WeatherApiService();
	}
}
