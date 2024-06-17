package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.Booking;
import com.coherent.solutions.hotel.reservations.entity.User;
import com.coherent.solutions.hotel.reservations.ito.CancelBookingResult;
import com.coherent.solutions.hotel.reservations.response.BookingResponse;
import com.coherent.solutions.hotel.reservations.response.UserResponse;
import com.coherent.solutions.hotel.reservations.service.BookingService;
import com.coherent.solutions.hotel.reservations.service.UserService;
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

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
@Tag(name = "Bookings", description = "BOOKINGS API")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get all the bookings in the system", description = "This method lets to retrieve all the bookings " +
            "from the MySQL AWS DB instance. By default, when the app starts up, it automatically inserts some bookings " +
            "on the bookings table to start to play with the app.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Bookings found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))})})
    @GetMapping("/bookings")
    public ResponseEntity<Iterable<Booking>> getAllBookings() {
        Iterable<Booking> bookingIterable = bookingService.getAllBookings();
        return ResponseEntity.status(HttpStatus.OK).body(bookingIterable);
    }

    @Operation(summary = "Creates a booking in the database.",
            description = "This method generates an booking entry at the MySQL AWS database instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Booking created",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))})})
    @PostMapping("/bookings")
    public ResponseEntity<Booking> create(@RequestBody Booking booking){
        Booking bookingCreated = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingCreated);
    }

    @Operation(summary = "Creates many bookings in the database in one single shot.",
            description = "This method generates SEVERAL booking entries at the MySQL AWS database instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Bookings created",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))})})
    @PostMapping("/bookings-many")
    public ResponseEntity< Iterable<Booking>> createMany(@RequestBody List<Booking> bookingList){
        System.out.println("Recibió una lista con elemtnos: " + bookingList.size());
        Iterable<Booking> bookingsCreated = bookingService.createBooking(bookingList);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingsCreated);
    }

    @Operation(summary = "Cancels many bookings in the database in one single shot.",
            description = "This method cancels SEVERAL booking entries at the MySQL AWS database instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Bookings cancelled",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))})})
    @PutMapping("/bookings-many")
    public ResponseEntity<CancelBookingResult> cancelMany(@RequestBody List<Booking> bookingList){
        System.out.println("Canceling a list with  elements: " + bookingList.size());
        CancelBookingResult cancelBookingResult = bookingService.cancelBookingList(bookingList);
        // Return the updated resource with a 200 (OK) status code
        return ResponseEntity.status(HttpStatus.OK).body(cancelBookingResult);
    }


    @Operation(summary = "Retrieves a booking", description = "This method lets to retrieve a booking from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking gotten",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)) })
    })
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Object> getBooking(@PathVariable String id) {
        try{
            Integer.parseInt(id);
        }catch(NumberFormatException e){
            String message = "Booking ID has an incorrect format: " + id;
            log.error(message);
            return new ResponseEntity<>(new BookingResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Booking> bookingOptional = bookingService.getBooking(Integer.parseInt(id));
        if(bookingOptional.isPresent())
            return new ResponseEntity<>(bookingOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(new BookingResponse("ERROR", "Booking ID: " + id + " was NOT found on the DB"), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates a booking",
            description = "This method updates a booking object from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)) })
    })
    @PutMapping("/bookings/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable("id") String id, @RequestBody Booking booking) {
        String message = null;
        int bookingId = -1;
        try{
            bookingId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Booking ID has an incorrect format: " + bookingId;
            log.error(message);
            return new ResponseEntity<>(new BookingResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }

        // Retrieve the resource from the database
        Optional<Booking> bookingOptional = bookingService.getBooking(Integer.parseInt(id));

        // If the resource is not found, return a 404 (not found) status code
        if (!bookingOptional.isPresent()) {
            message = "Booking ID: " + id + " was found found on the DB";
            log.error(message);
            return new ResponseEntity<>(new BookingResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }

        // Update the resource
        booking.setId(bookingId);
        log.info("booking incoming: " + booking);
        Booking bookingCreated = bookingService.saveBooking(booking);
        log.info("bookingCreated: " + bookingCreated);

        // Return the updated resource with a 200 (OK) status code
        return ResponseEntity.status(HttpStatus.OK).body(bookingCreated);
    }

    @Operation(summary = "Deletes a booking", description = "This method deletes a booking from database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking removed",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)) })
    })
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable String id){
        String message = "Booking ID: "+  id + " deleted correctly.";
        int bookingId = -1;
        try{
            bookingId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Booking ID has an incorrect format: " + bookingId;
            log.error(message);
            return new ResponseEntity<>(new BookingResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Booking> bookingOptional = bookingService.getBooking(Integer.parseInt(id));
        if(!bookingOptional.isPresent()) {
            message = "Booking ID: " + id + " was not found on the DB";
            log.error(message);
            return new ResponseEntity<>(new BookingResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }else {
            message = "Booking ID: " + id + " removed from the DB";
            bookingService.removeBooking(bookingId);
            log.info(message);
            // Return the info about the removed resource with a 202 (ACCEPTED) status code
            return new ResponseEntity<>(new BookingResponse("SUCCESS", message), HttpStatus.ACCEPTED);
        }
    }

}
