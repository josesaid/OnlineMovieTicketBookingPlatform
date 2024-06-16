package com.coherent.solutions.hotel.reservations.configuration;

import com.coherent.solutions.hotel.reservations.entity.*;
import com.coherent.solutions.hotel.reservations.enums.*;
import com.coherent.solutions.hotel.reservations.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class ReservationGenerator {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    Sala sala1 = new Sala();
    Sala sala2 = new Sala();
    Sala salaCreated1 = new Sala();
    Sala salaCreated2 = new Sala();
    Map<String, SEAT_STATUS>asientosMapa1 = new HashMap<>();
    Map<String, SEAT_STATUS>asientosMapa2 = new HashMap<>();
    Theatre theatre1 = new Theatre();
    Theatre theatre2 = new Theatre();
    List<Sala> salasCine1 = new ArrayList<>();
    List<Sala> salasCine2 = new ArrayList<>();
    User user1 = new User();
    User user2 = new User();
    Payment payment1 = new Payment();
    Evento evento1 = new Evento();

    Booking bookingAquaman2 = new Booking();
    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        generateInitialReservationsVersionCustomizedReservation();
        createTheatres();
        createSalas();
        createUsers();
        createPayments();
        createEvents();
        createBooking();
    }

    private void createBooking() {
        bookingAquaman2.setIdSala(sala1.getId());
        bookingAquaman2.setIdUsuario(user1.getId());
        bookingAquaman2.setIdTheatre(theatre1.getId());
        bookingAquaman2.setIdPayment(payment1.getId());
        bookingAquaman2.setComments("NA");
        bookingAquaman2.setStatus(BOOKING_STATUS.CONFIRMED);
        Booking bookingCreated = bookingRepository.save(bookingAquaman2);
        log.info("bookingCreated : " +bookingCreated );
    }

    private void createEvents() {
        evento1.setTipoEvento(TIPO_EVENTO.PELICULA);
        evento1.setNombreEvento("Aquaman 2");
        evento1.setHorarioInicio(LocalDateTime.of(2024, 06, 16, 16, 0));
        evento1.setHorarioFin(LocalDateTime.of(2024, 06, 16, 18, 0));
        evento1.setAudioIdiomaEvento("English");
        evento1.setSubtitulosIdiomaEvento("Spanish");
        evento1.setClassification("Science fiction");
        evento1.setComments("NA");
        Evento eventoCreated = eventRepository.save(evento1);
        log.info("eventoCreated: " + eventoCreated);
    }

    private void createPayments() {
        payment1.setAmount(254.65f);
        payment1.setTimestamp(LocalDateTime.now());
        payment1.setTipoPago(TIPO_PAGO.TARJETA_CREDITO);
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


    private void createSalas() {
        sala1.setId(1);
        sala1.setIdEvento(1);
        sala1.setStatus(STATUS_SALA.ABIERTA);

        asientosMapa1.put("A1", SEAT_STATUS.RESERVED);
        asientosMapa1.put("A2", SEAT_STATUS.FREE);
        asientosMapa1.put("A3", SEAT_STATUS.RESERVED);
        asientosMapa1.put("A4", SEAT_STATUS.FREE);
        sala1.setAsientosMapa(asientosMapa1);
        salaCreated1 = salaRepository.save(sala1);
        log.info("salaCreated1: " + salaCreated1);


        sala2.setId(2);
        sala2.setIdEvento(2);
        sala2.setStatus(STATUS_SALA.EN_REPARACION);

        asientosMapa2.put("A1", SEAT_STATUS.FREE);
        asientosMapa2.put("A2", SEAT_STATUS.FREE);
        asientosMapa2.put("A3", SEAT_STATUS.FREE);
        asientosMapa2.put("A4", SEAT_STATUS.RESERVED);
        sala2.setAsientosMapa(asientosMapa2);
        salaCreated2 = salaRepository.save(sala2);
        log.info("salaCreated2: " + salaCreated2);
    }

    private void createTheatres() {

        theatre1.setName("Cinepolis Galerias");
        theatre1.setAddress("Av Vallarta 5091");
        theatre1.setCity("Zapopan");
        theatre1.setState("Jalisco");
        theatre1.setCountry("Mexico");

        sala1.setId(1);
        sala1.setIdEvento(sala1.getIdEvento());
        sala1.setStatus(STATUS_SALA.ABIERTA);

        sala2.setId(2);
        sala2.setIdEvento(2);
        sala2.setStatus(STATUS_SALA.EN_REPARACION);
        Sala salaCreated2 = salaRepository.save(sala2);
        log.info("salaCreated2: " + salaCreated2);

        salasCine1.add(salaCreated1);
        salasCine1.add(salaCreated2);
        theatre1.setSalas(salasCine1);

        Theatre theatreCreated = theatreRepository.save(theatre1);
        log.info("theatreCreated: " +theatreCreated);

        theatre2.setName("Cinepolis Valle Oriente");
        theatre2.setAddress("Av Oriente 8529");
        theatre2.setCity("GDL");
        theatre2.setState("Jalisco");
        theatre2.setCountry("Mexico");
        theatre2.setSalas(salasCine1);

        Theatre theatreCreated2 = theatreRepository.save(theatre2);
        log.info("theatreCreated2: " +theatreCreated2);
    }

    private void generateInitialReservationsVersionCustomizedReservation() {
        log.info("BEGIN initial sample reservations...");

        Reservation reservation = new Reservation();
        reservation.setRoomNumber(100);
        reservation.setClientFullName("Jose Said Olano Garcia");
        List<LocalDate>localDateList = new ArrayList<>();
        localDateList.add(LocalDate.now());
        localDateList.add(LocalDate.now().plusDays(1));
        localDateList.add(LocalDate.now().plusDays(2));
        localDateList.add(LocalDate.now().plusDays(3));
        reservation.setReservationDates(localDateList);
        Reservation reservationCreatedAtDB = reservationRepository.save(reservation);
        log.info("reservationCreatedAtDB: " + reservationCreatedAtDB);

        //------------------------------------------------------------------------
        Reservation reservation2 = new Reservation();
        reservation2.setRoomNumber(101);
        reservation2.setClientFullName("John Smith");
        List<LocalDate>localDateList2 = new ArrayList<>();
        localDateList2.add(LocalDate.now());
        localDateList2.add(LocalDate.now().plusDays(1).plusMonths(1));
        localDateList2.add(LocalDate.now().plusDays(2).plusMonths(1));
        localDateList2.add(LocalDate.now().plusDays(3).plusMonths(1));
        reservation2.setReservationDates(localDateList2);
        reservationCreatedAtDB = reservationRepository.save(reservation2);
        log.info("reservationCreatedAtDB: " + reservationCreatedAtDB);

        //------------------------------------------------------------------------
        Reservation reservation3 = new Reservation();
        reservation3.setRoomNumber(102);
        reservation3.setClientFullName("Clark Kent");
        List<LocalDate>localDateList3 = new ArrayList<>();
        localDateList3.add(LocalDate.now());
        localDateList3.add(LocalDate.now().plusDays(4));
        localDateList3.add(LocalDate.now().plusDays(5).plusMonths(7));
        localDateList3.add(LocalDate.now().plusDays(6).plusMonths(7).plusYears(1));
        reservation3.setReservationDates(localDateList3);
        reservationCreatedAtDB = reservationRepository.save(reservation3);
        log.info("reservationCreatedAtDB: " + reservationCreatedAtDB);

        log.info("END initial sample reservations...");
    }

}
