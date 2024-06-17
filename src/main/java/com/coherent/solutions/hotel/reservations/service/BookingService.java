package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.Booking;
import com.coherent.solutions.hotel.reservations.enums.BOOKING_STATUS;
import com.coherent.solutions.hotel.reservations.ito.CancelBookingResult;
import com.coherent.solutions.hotel.reservations.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    public Iterable<Booking> getAllBookings() {
        log.info("Retrieving all the bookings");
        return bookingRepository.findAll();
    }

    public Iterable<Booking> createBooking(List<Booking> bookings) {
        System.out.println("bookings: " + bookings.size());
        log.info("Creating many bookings at the DB");
        return bookingRepository.saveAll(bookings);
    }

    public Booking createBooking(Booking booking) {
        log.info("Creating a booking");
        Booking bookingCreated = bookingRepository.save(booking);
        return bookingCreated;
    }

    public Optional<Booking> getBooking(int bookingId) {
        log.info("Retrieving a booking");
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        return bookingOptional;
    }

    public Booking saveBooking(Booking booking) {
        log.info("Updating a booking");
        return bookingRepository.save(booking);
    }

    public void removeBooking(int bookingId) {
        log.info("Removing a booking");
        bookingRepository.deleteById(bookingId);
    }

    public CancelBookingResult cancelBookingList(List<Booking> bookingList) {
        CancelBookingResult cancelBookingResult = new CancelBookingResult();
        log.info("Updating many bookings at the DB");

        for(Booking booking : bookingList){
            Optional<Booking> bookingOptional = bookingRepository.findById(booking.getId());
            if(bookingOptional.isPresent()) {
                cancelBookingResult.getBookingIDsUpdated().add(booking.getId());
            }else{
                cancelBookingResult.getBookingIDsNotFound().add(booking.getId());
            }
        }

        //---- Change status to cancelled on the selected ones (only the ones who are present, taking advantage of
        // the Java Optional functionality/feature).
        for(Integer bookingID :cancelBookingResult.getBookingIDsUpdated()){
            log.warn("Cancelling booking ID: " + bookingID);
            Optional<Booking> bookingOptional = bookingRepository.findById(bookingID);
            if(bookingOptional.isPresent()){
                Booking booking = bookingOptional.get();
                booking.setId(bookingID);
                booking.setStatus(BOOKING_STATUS.CANCELED);
                bookingRepository.save(booking);
            }
        }

        return cancelBookingResult;
    }

}
