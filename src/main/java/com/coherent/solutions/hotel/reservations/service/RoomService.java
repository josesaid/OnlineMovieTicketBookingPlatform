package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.Room;
import com.coherent.solutions.hotel.reservations.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    public Iterable<Room> getAllRooms() {
        log.info("Retrieving all the rooms");
        return roomRepository.findAll();
    }

    public Room createRoom(Room room) {
        log.info("Creating a room");
        Room roomCreated = roomRepository.save(room);
        return roomCreated;
    }

    public Optional<Room> getRoom(int roomId) {
        log.info("Retrieving a room");
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        return roomOptional;
    }

    public Room saveRoom(Room room) {
        log.info("Updating a room");
        return roomRepository.save(room);
    }

    public void removeRoom(int roomId) {
        log.info("Removing a room");
        roomRepository.deleteById(roomId);
    }

}
