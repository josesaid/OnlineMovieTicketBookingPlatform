package com.coherent.solutions.hotel.reservations.converter;

import com.coherent.solutions.hotel.reservations.enums.SEAT_STATUS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/*
    This class is used to convert the information coming from the property Room.seatsMap to the database as a
    tool mapper and vice-versa.
*/
@Slf4j
@Converter(autoApply = true)
public class SeatsMapConverter implements AttributeConverter<Map<String, SEAT_STATUS>,String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public SeatsMapConverter(){
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String convertToDatabaseColumn(Map<String, SEAT_STATUS> seatsMap) {
        String seatsMapJSON = null;
        try{
            seatsMapJSON = objectMapper.writeValueAsString(seatsMap);
        }catch(Exception e){
            log.error("JSON Writing error", e);
        }
        return seatsMapJSON;
    }

    public Map<String, SEAT_STATUS> convertToEntityAttribute(String seatsMapJSON) {
        Map<String, SEAT_STATUS> seatsMap = null;
        try {
            seatsMap = objectMapper.readValue(seatsMapJSON, new TypeReference<>() {});
        } catch (final Exception e) {
            log.error("JSON reading error", e);
        }
        return seatsMap;
    }

}
