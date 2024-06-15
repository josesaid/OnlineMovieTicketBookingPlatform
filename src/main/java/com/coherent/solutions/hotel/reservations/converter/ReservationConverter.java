package com.coherent.solutions.hotel.reservations.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Converter(autoApply = true)
public class ReservationConverter implements AttributeConverter<List<LocalDate>,String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public ReservationConverter(){
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String convertToDatabaseColumn(List<LocalDate> reservationDates) {
        String reservationDatesJSON = null;
        try{
            reservationDatesJSON = objectMapper.writeValueAsString(reservationDates);
        }catch(Exception e){
            log.error("JSON Writing error", e);
        }
        return reservationDatesJSON;
    }

    public List<LocalDate> convertToEntityAttribute(String reservationDatesJSON) {
        List<LocalDate> reservedDates = null;
        try {
            reservedDates = objectMapper.readValue(reservationDatesJSON, new TypeReference<>() {});
        } catch (final Exception e) {
            log.error("JSON reading error", e);
        }
        return reservedDates;
    }

}
