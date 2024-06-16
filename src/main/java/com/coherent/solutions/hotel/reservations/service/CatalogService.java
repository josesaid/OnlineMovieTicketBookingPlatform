package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.repository.TheatreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CatalogService {
    @Autowired
    private TheatreRepository theatreRepository;

    public List<Theatre> getTheatresByCountry(String country) {
        return theatreRepository.findByCountry(country);
    }

    public Iterable<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public List<Theatre> getTheatresByCountryAndState(String countryName, String stateName) {
        return theatreRepository.findByCountryAndState(countryName, stateName);
    }

    public List<Theatre> getTheatresByCountryAndStateAndCity(String countryName, String stateName, String cityName) {
        return theatreRepository.findByCountryAndStateAndCity(countryName, stateName, cityName);
    }


}
