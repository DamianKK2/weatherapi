package com.example.weatherapi.model.impl;

import java.util.Optional;

import com.example.weatherapi.model.City;

public class CityImpl implements City {

    private String name;
    private Optional<String> postalCode;
    private Optional<String> country;

    public CityImpl(String city) {
        this.name = city;
        this.postalCode = Optional.empty();
        this.country = Optional.empty();
    }
    public CityImpl(String city, String postalCode) {
        this.name = city;
        this.postalCode = Optional.of(postalCode);
        this.country = Optional.empty();
    }
    public CityImpl(String city, String postalCode, String country) {
        this.name = city;
        this.postalCode = Optional.of(postalCode);
        this.country = Optional.of(country);
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getPostalCode() {
        return postalCode;
    }

    @Override
    public Optional<String> getCountry() {
        return country;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result = prime * result + name.hashCode();
        if(postalCode.isPresent()) {
            result = 31 * result + postalCode.get().hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } if (obj == null) {
            return false;
        } if (getClass() != obj.getClass()) {
            return false;
        } 
        CityImpl other = (CityImpl) obj;
        if (!name.equals(other.name)) {
            return false;
        } 
        return (this.postalCode.get() == null && other.postalCode.get() == null)
            || (this.postalCode.get() != null && this.postalCode.get().equals(other.postalCode.get()));
    }
    
}
