package com.coherent.solutions.hotel.reservations.converter;

import com.coherent.solutions.hotel.reservations.entity.Room;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Converter(autoApply = true)
public class RoomConverter implements AttributeConverter<List<Room>,String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public RoomConverter(){
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String convertToDatabaseColumn(List<Room> salasList) {
        String salasJSON = null;
        try{
            salasJSON = objectMapper.writeValueAsString(salasList);
        }catch(Exception e){
            log.error("JSON Writing error", e);
        }
        return salasJSON;
    }

    public List<Room> convertToEntityAttribute(String salasJSON) {
        List<Room> salasList = null;
        try {
            salasList = objectMapper.readValue(salasJSON, new TypeReference<>() {});
        } catch (final Exception e) {
            log.error("JSON reading error", e);
        }
        return salasList;
    }

}
