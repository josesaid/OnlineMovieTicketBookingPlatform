package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.BodyResponse;
import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.service.CatalogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/theatres")
@Slf4j
@Tag(name = "Catalog", description = "Current Movies API")
public class CatalogController {
    private final CatalogService catalogService;
    private BodyResponse bodyResponse;
    private List<Theatre> theatreList;
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping(value = {
            "/countries", "/countries/", "/countries/{countryName}", "/countries/{countryName}/",
            "/countries/{countryName}/states", "/countries/{countryName}/states/", "/countries/{countryName}/states/{stateName}",
            "/countries/{countryName}/states/{stateName}/", "/countries/{countryName}/states/{stateName}/cities",
            "/countries/{countryName}/states/{stateName}/cities/", "/countries/{countryName}/states/{stateName}/cities/{cityName}",
            "/countries/{countryName}/states/{stateName}/cities/{cityName}/"
    })
    public Object getTheatresByLocation(@PathVariable(required = false) String countryName,
                                        @PathVariable(required = false) String stateName,
                                        @PathVariable(required = false) String cityName) {
        bodyResponse = new BodyResponse();
        theatreList = new ArrayList<>();

        if (cityName != null && stateName != null && countryName != null) {
            System.out.println("cityName y stateName y countryName !=null");
            theatreList = catalogService.getTheatresByCountryAndStateAndCity(countryName, stateName, cityName);
            bodyResponse.setNumberOfRecords(theatreList.size());
            bodyResponse.setData(theatreList);
            return bodyResponse;
        }
        if (stateName != null && countryName != null) {
            System.out.println("stateName y countryName !=null");
            theatreList = catalogService.getTheatresByCountryAndState(countryName, stateName);
            bodyResponse.setNumberOfRecords(theatreList.size());
            bodyResponse.setData(theatreList);
            return bodyResponse;
        }
        if (countryName != null) {
            System.out.println("countryName !=null");
            theatreList = catalogService.getTheatresByCountry(countryName);
            bodyResponse.setNumberOfRecords(theatreList.size());
            bodyResponse.setData(theatreList);
            return bodyResponse;
        }

        //Country, state and city are null, then return ALL the theatres
        for (Theatre theatre : catalogService.getAllTheatres()) {
            theatreList.add(theatre);
        }
        bodyResponse.setNumberOfRecords(theatreList.size());
        bodyResponse.setData(theatreList);
        return bodyResponse;

    }

}
