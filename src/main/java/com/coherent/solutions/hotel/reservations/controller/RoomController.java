package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.Room;
import com.coherent.solutions.hotel.reservations.response.RoomResponse;
import com.coherent.solutions.hotel.reservations.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
@Tag(name = "Rooms", description = "ROOMS API")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get all the rooms in the system", description = "This method lets to retrieve all the rooms " +
            "from the MySQL AWS DB instance. By default, when the app starts up, it automatically inserts some rooms " +
            "on the rooms table to start to play with the app.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rooms found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))})})
    @GetMapping("/rooms")
    public ResponseEntity<Iterable<Room>> getAllRooms() {
        Iterable<Room> roomIterable = roomService.getAllRooms();
        return ResponseEntity.status(HttpStatus.OK).body(roomIterable);
    }

    @Operation(summary = "Creates a room in the database.",
            description = "This method generates a room entry at the MySQL AWS database instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Room created",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))})})
    @PostMapping("/rooms")
    public ResponseEntity<Room> create(@RequestBody Room room){
        Room roomCreated = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomCreated);
    }

    @Operation(summary = "Retrieves a room", description = "This method lets to retrieve a room from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room gotten",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)) })
    })
    @GetMapping("/rooms/{id}")
    public ResponseEntity<Object> getRoom(@PathVariable String id) {
        try{
            Integer.parseInt(id);
        }catch(NumberFormatException e){
            String message = "ROOM ID has an incorrect format: " + id;
            log.error(message);
            return new ResponseEntity<>(new RoomResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Room> roomOptional = roomService.getRoom(Integer.parseInt(id));
        if(roomOptional.isPresent())
            return new ResponseEntity<>(roomOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(new RoomResponse("ERROR", "Room ID: " + id + " was NOT found on the DB"), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates a room", description = "This method updates a room object from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)) }),
            @ApiResponse(responseCode = "404", description = "Room not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)) })
    })
    @PutMapping("/rooms/{id}")
    public ResponseEntity<Object> updateRoom(@PathVariable("id") String id, @RequestBody Room room) {
        String message = null;
        int roomId = -1;
        try{
            roomId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Room ID has an incorrect format: " + roomId;
            log.error(message);
            return new ResponseEntity<>(new RoomResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }

        // Retrieve the resource from the database
        Optional<Room> roomOptional = roomService.getRoom(Integer.parseInt(id));

        // If the resource is not found, return a 404 (not found) status code
        if (!roomOptional.isPresent()) {
            message = "Room ID: " + id + " was found found on the DB";
            log.error(message);
            return new ResponseEntity<>(new RoomResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }

        // Update the resource
        room.setId(roomId);
        log.info("room incoming: " + room);
        Room roomUpdated = roomService.saveRoom(room);
        log.info("roomUpdated: " + roomUpdated);

        // Return the updated resource with a 200 (OK) status code
        return ResponseEntity.status(HttpStatus.OK).body(roomUpdated);
    }


    @Operation(summary = "Deletes a room", description = "This method deletes a room from database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room removed",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)) }),
            @ApiResponse(responseCode = "404", description = "Room not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)) })
    })
    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable String id){
        String message = "Room ID: "+  id + " deleted correctly.";
        int roomId = -1;
        try{
            roomId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Room ID has an incorrect format: " + roomId;
            log.error(message);
            return new ResponseEntity<>(new RoomResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Room> roomOptional = roomService.getRoom(Integer.parseInt(id));
        if(!roomOptional.isPresent()) {
            message = "Room ID: " + id + " was not found on the DB";
            log.error(message);
            return new ResponseEntity<>(new RoomResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }else {
            message = "Room ID: " + id + " removed from the DB";
            roomService.removeRoom(roomId);
            log.info(message);
            // Return the info about the removed resource with a 202 (ACCEPTED) status code
            return new ResponseEntity<>(new RoomResponse("SUCCESS", message), HttpStatus.ACCEPTED);
        }
    }

}
