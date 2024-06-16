package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.Event;
import com.coherent.solutions.hotel.reservations.entity.User;
import com.coherent.solutions.hotel.reservations.response.EventResponse;
import com.coherent.solutions.hotel.reservations.response.UserResponse;
import com.coherent.solutions.hotel.reservations.service.EventService;
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
@Tag(name = "Events", description = "EVENTS API")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Get all the events in the system", description = "This method lets to retrieve all the events " +
            "from the MySQL AWS DB instance. By default, when the app starts up, it automatically inserts some events " +
            "on the events table to start to play with the app.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Events found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))})})
    @GetMapping("/events")
    public ResponseEntity<Iterable<Event>> getAllEvents() {
        Iterable<Event> eventIterable = eventService.getAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body(eventIterable);
    }

    @Operation(summary = "Creates an event in the database.",
            description = "This method generates an event entry at the MySQL AWS database instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Event created",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})})
    @PostMapping("/events")
    public ResponseEntity<Event> create(@RequestBody Event event){
        Event eventCreated = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventCreated);
    }

    @Operation(summary = "Retrieves an event", description = "This method lets to retrieve an event from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User gotten",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    })
    @GetMapping("/events/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable String id) {
        try{
            Integer.parseInt(id);
        }catch(NumberFormatException e){
            String message = "EVENT ID has an incorrect format: " + id;
            log.error(message);
            return new ResponseEntity<>(new UserResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Event> eventOptional = eventService.getEvent(Integer.parseInt(id));
        if(eventOptional.isPresent())
            return new ResponseEntity<>(eventOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(new EventResponse("ERROR", "Event ID: " + id + " was NOT found on the DB"), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates an event",
            description = "This method updates an event object from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) })
    })
    @PutMapping("/events/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable("id") String id, @RequestBody Event event) {
        String message = null;
        int eventId = -1;
        try{
            eventId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Event ID has an incorrect format: " + eventId;
            log.error(message);
            return new ResponseEntity<>(new EventResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }

        // Retrieve the resource from the database
        Optional<Event> eventOptional = eventService.getEvent(Integer.parseInt(id));

        // If the resource is not found, return a 404 (not found) status code
        if (!eventOptional.isPresent()) {
            message = "Event ID: " + id + " was found found on the DB";
            log.error(message);
            return new ResponseEntity<>(new EventResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }

        // Update the resource
        event.setId(eventId);
        log.info("event incoming: " + event);
        Event eventUpdated = eventService.saveEvent(event);
        log.info("eventUpdated: " + eventUpdated);

        // Return the updated resource with a 200 (OK) status code
        return ResponseEntity.status(HttpStatus.OK).body(eventUpdated);
    }


    @Operation(summary = "Deletes an event", description = "This method deletes an event from database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event removed",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) })
    })
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable String id){
        String message = "Event ID: "+  id + " deleted correctly.";
        int eventId = -1;
        try{
            eventId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Event ID has an incorrect format: " + eventId;
            log.error(message);
            return new ResponseEntity<>(new EventResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Event> eventOptional = eventService.getEvent(Integer.parseInt(id));
        if(!eventOptional.isPresent()) {
            message = "Event ID: " + id + " was not found on the DB";
            log.error(message);
            return new ResponseEntity<>(new EventResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }else {
            message = "Event ID: " + id + " removed from the DB";
            eventService.removeEvent(eventId);
            log.info(message);
            // Return the info about the removed resource with a 202 (ACCEPTED) status code
            return new ResponseEntity<>(new EventResponse("SUCCESS", message), HttpStatus.ACCEPTED);
        }
    }

}
