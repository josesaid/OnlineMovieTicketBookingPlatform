package com.coherent.solutions.hotel.reservations.repository;

import com.coherent.solutions.hotel.reservations.entity.Theatre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends CrudRepository<Theatre, Integer> {
    List<Theatre> findByCountry(String country);
    List<Theatre> findByCountryAndState(String country, String state);
    List<Theatre> findByCountryAndStateAndCity(String country, String state, String cityName);

}

