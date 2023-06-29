package com.example.weatherapi.model;

import java.util.Optional;

public interface City {
    String getName();
    Optional<String> getPostalCode();
    Optional<String> getCountry();
}
