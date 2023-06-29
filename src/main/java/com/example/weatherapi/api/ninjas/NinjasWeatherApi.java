package com.example.weatherapi.api.ninjas;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class NinjasWeatherApi {
    
    private static final String REQUEST_SUFFIX = "/weather?city={city}";
	private static final Logger LOGGER = LoggerFactory.getLogger(NinjasWeatherApi.class);

    @Value("${api.ninjas.url}")
    private String apiUrl;

    @Value("${api.ninjas.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Response> findCurrentWeather(String city) {

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Response> weatherAttributes = restTemplate.exchange(apiUrl + REQUEST_SUFFIX, HttpMethod.GET, entity, Response.class, city);

        LOGGER.info(weatherAttributes.toString());

        return weatherAttributes;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Api-Key", apiKey);
        return headers;
    }
}
