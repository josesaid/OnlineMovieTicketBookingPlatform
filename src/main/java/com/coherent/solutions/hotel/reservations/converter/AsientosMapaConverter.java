package com.coherent.solutions.hotel.reservations.converter;

import com.coherent.solutions.hotel.reservations.enums.SEAT_STATUS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Converter(autoApply = true)
public class AsientosMapaConverter implements AttributeConverter<Map<String, SEAT_STATUS>,String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public AsientosMapaConverter(){
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String convertToDatabaseColumn(Map<String, SEAT_STATUS> reservationDates) {
        String reservationDatesJSON = null;
        try{
            reservationDatesJSON = objectMapper.writeValueAsString(reservationDates);
        }catch(Exception e){
            log.error("JSON Writing error", e);
        }
        return reservationDatesJSON;
    }

    public Map<String, SEAT_STATUS> convertToEntityAttribute(String reservationDatesJSON) {
        Map<String, SEAT_STATUS> reservedDates = null;
        try {
            reservedDates = objectMapper.readValue(reservationDatesJSON, new TypeReference<>() {});
        } catch (final Exception e) {
            log.error("JSON reading error", e);
        }
        return reservedDates;
    }

}
