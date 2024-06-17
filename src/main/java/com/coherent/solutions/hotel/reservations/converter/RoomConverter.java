package com.coherent.solutions.hotel.reservations.converter;

import com.coherent.solutions.hotel.reservations.entity.Room;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/*
    This class is used to convert the information coming from Room class to the database as a tool mapper and vice-versa.
* */
@Slf4j
@Converter(autoApply = true)
public class RoomConverter implements AttributeConverter<List<Room>,String> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public RoomConverter(){
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String convertToDatabaseColumn(List<Room> roomsList) {
        String roomsJSON = null;
        try{
            roomsJSON = objectMapper.writeValueAsString(roomsList);
        }catch(Exception e){
            log.error("JSON Writing error", e);
        }
        return roomsJSON;
    }

    public List<Room> convertToEntityAttribute(String roomsJSON) {
        List<Room> roomsList = null;
        try {
            roomsList = objectMapper.readValue(roomsJSON, new TypeReference<>() {});
        } catch (final Exception e) {
            log.error("JSON reading error", e);
        }
        return roomsList;
    }

}
