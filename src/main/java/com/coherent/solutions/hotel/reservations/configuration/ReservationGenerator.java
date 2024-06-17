package com.coherent.solutions.hotel.reservations.configuration;

import com.coherent.solutions.hotel.reservations.entity.*;
import com.coherent.solutions.hotel.reservations.enums.*;
import com.coherent.solutions.hotel.reservations.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class ReservationGenerator {
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    Room room1 = new Room();
    Room room02 = new Room();
    Room roomCreated01 = new Room();
    Room roomCreated2 = new Room();
    Map<String, SEAT_STATUS> seatsMap01 = new HashMap<>();
    Map<String, SEAT_STATUS> seatsMap02 = new HashMap<>();
    Theatre theatre1 = new Theatre();
    Theatre theatre2 = new Theatre();
    List<Room> roomTheatre1 = new ArrayList<>();
    List<Room> roomTheatre2 = new ArrayList<>();
    User user1 = new User();
    User user2 = new User();
    Payment payment1 = new Payment();
    Event event1 = new Event();

    Booking bookingAquaman2 = new Booking();
    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        createTheatres();
        createRooms();
        createUsers();
        createPayments();
        createEvents();
        createBooking();
    }

    private void createBooking() {
        bookingAquaman2.setIdRoom(room1.getId());
        bookingAquaman2.setIdUser(user1.getId());
        bookingAquaman2.setIdTheatre(theatre1.getId());
        bookingAquaman2.setIdPayment(payment1.getId());
        bookingAquaman2.setComments("NA");
        bookingAquaman2.setStatus(BOOKING_STATUS.CONFIRMED);
        Booking bookingCreated = bookingRepository.save(bookingAquaman2);
        log.info("bookingCreated : " +bookingCreated );
    }

    private void createEvents() {
        event1.setEventType(EVENT_TIPE.PELICULA);
        event1.setEventName("Aquaman 2");
        event1.setInitTime(LocalDateTime.of(2024, 06, 16, 16, 0));
        event1.setFinishTime(LocalDateTime.of(2024, 06, 16, 18, 0));
        event1.setEventAudioLanguage("English");
        event1.setEventSubtitleLanguage("Spanish");
        event1.setClassification("Science fiction");
        event1.setComments("NA");
        Event eventCreated = eventRepository.save(event1);
        log.info("eventCreated: " + eventCreated);
    }

    private void createPayments() {
        payment1.setAmount(254.65f);
        payment1.setTimestamp(LocalDateTime.now());
        payment1.setPaymentType(PAYMENT_TYPE.TARJETA_CREDITO);
        payment1.setDescription("User paid with credit card online");
        payment1.setReferenceDetails("Citibank_REF1234567890");
        payment1.setStatus(PAYMENT_STATUS.CHARGED);
        Payment paymentCreated = paymentRepository.save(payment1);
        log.info("paymentCreated: " + paymentCreated);
    }

    private void createUsers() {
        user1.setName("Said Olano");
        user1.setStatus(USER_STATUS.ENABLED);
        user1.setEmail("josesaid@gmail.com");
        user1.setCellPhone("33-3252-1153");
        User userCreated = userRepository.save(user1);
        log.info("userCreated: " + userCreated);

        user2.setName("Juana la cubana");
        user2.setStatus(USER_STATUS.DISABLED);
        user2.setEmail("juana_la_cubana@gmail.com");
        user2.setCellPhone("52-9876-4541");
        User userCreated2 = userRepository.save(user2);
        log.info("userCreated2: " + userCreated2);
    }


    private void createRooms() {
        room1.setId(1);
        room1.setIdEvent(1);
        room1.setStatus(ROOM_STATUS.ABIERTA);

        seatsMap01.put("A1", SEAT_STATUS.RESERVED);
        seatsMap01.put("A2", SEAT_STATUS.FREE);
        seatsMap01.put("A3", SEAT_STATUS.RESERVED);
        seatsMap01.put("A4", SEAT_STATUS.FREE);
        room1.setSeatsMap(seatsMap01);
        roomCreated01 = roomRepository.save(room1);
        log.info("roomCreated01: " + roomCreated01);


        room02.setId(2);
        room02.setIdEvent(2);
        room02.setStatus(ROOM_STATUS.EN_REPARACION);

        seatsMap02.put("A1", SEAT_STATUS.FREE);
        seatsMap02.put("A2", SEAT_STATUS.FREE);
        seatsMap02.put("A3", SEAT_STATUS.FREE);
        seatsMap02.put("A4", SEAT_STATUS.RESERVED);
        room02.setSeatsMap(seatsMap02);
        roomCreated2 = roomRepository.save(room02);
        log.info("roomCreated2: " + roomCreated2);
    }

    private void createTheatres() {

        theatre1.setName("Cinepolis Galerias");
        theatre1.setAddress("Av Vallarta 5091");
        theatre1.setCity("Zapopan");
        theatre1.setState("Jalisco");
        theatre1.setCountry("Mexico");

        room1.setId(1);
        room1.setIdEvent(room1.getIdEvent());
        room1.setStatus(ROOM_STATUS.ABIERTA);

        room02.setId(2);
        room02.setIdEvent(2);
        room02.setStatus(ROOM_STATUS.EN_REPARACION);
        Room roomCreated2 = roomRepository.save(room02);
        log.info("roomCreated2: " + roomCreated2);

        roomTheatre1.add(roomCreated01);
        roomTheatre1.add(roomCreated2);
        theatre1.setRooms(roomTheatre1);

        Theatre theatreCreated = theatreRepository.save(theatre1);
        log.info("theatreCreated: " +theatreCreated);

        theatre2.setName("Cinepolis Valle Oriente");
        theatre2.setAddress("Av Oriente 8529");
        theatre2.setCity("GDL");
        theatre2.setState("Jalisco");
        theatre2.setCountry("Mexico");
        theatre2.setRooms(roomTheatre1);

        Theatre theatreCreated2 = theatreRepository.save(theatre2);
        log.info("theatreCreated2: " +theatreCreated2);
    }



}
