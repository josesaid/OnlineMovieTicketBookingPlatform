package com.coherent.solutions.hotel.reservations.configuration;

import com.coherent.solutions.hotel.reservations.entity.Reservation;
import com.coherent.solutions.hotel.reservations.entity.Sala;
import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.enums.SEAT_STATUS;
import com.coherent.solutions.hotel.reservations.enums.STATUS_SALA;
import com.coherent.solutions.hotel.reservations.repository.ReservationRepository;
import com.coherent.solutions.hotel.reservations.repository.SalaRepository;
import com.coherent.solutions.hotel.reservations.repository.TheatreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    Sala sala1 = new Sala();
    Sala sala2 = new Sala();
    Map<String, SEAT_STATUS>asientosMapa1 = new HashMap<>();
    Map<String, SEAT_STATUS>asientosMapa2 = new HashMap<>();
    Theatre theatre1 = new Theatre();
    Theatre theatre2 = new Theatre();
    List<Sala> salasCine1 = new ArrayList<>();
    List<Sala> salasCine2 = new ArrayList<>();
    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        generateInitialReservationsVersionCustomizedReservation();
        createTheatres();
        createSalas();
    }


    private void createSalas() {
        sala1.setId(1);
        sala1.setIdEvento(1);
        sala1.setStatusSala(STATUS_SALA.ABIERTA);

        asientosMapa1.put("A1", SEAT_STATUS.RESERVED);
        asientosMapa1.put("A2", SEAT_STATUS.FREE);
        asientosMapa1.put("A3", SEAT_STATUS.RESERVED);
        asientosMapa1.put("A4", SEAT_STATUS.FREE);
        sala1.setAsientosMapa(asientosMapa1);
        Sala salaCreated1 = salaRepository.save(sala1);
        log.info("salaCreated1: " + salaCreated1);


        sala2.setId(2);
        sala2.setIdEvento(2);
        sala2.setStatusSala(STATUS_SALA.EN_REPARACION);

        asientosMapa2.put("A1", SEAT_STATUS.FREE);
        asientosMapa2.put("A2", SEAT_STATUS.FREE);
        asientosMapa2.put("A3", SEAT_STATUS.FREE);
        asientosMapa2.put("A4", SEAT_STATUS.RESERVED);
        sala2.setAsientosMapa(asientosMapa2);
        Sala salaCreated2 = salaRepository.save(sala2);
        log.info("salaCreated2: " + salaCreated2);
    }

    private void createTheatres() {

        theatre1.setName("Cinepolis Galerias");
        theatre1.setAddress("Av Vallarta 5091");
        theatre1.setCity("Zapopan");
        theatre1.setState("Jalisco");
        theatre1.setCountry("Mexico");

        sala1.setId(1);
        sala1.setIdEvento(1);
        sala1.setStatusSala(STATUS_SALA.ABIERTA);

        sala2.setId(2);
        sala2.setIdEvento(2);
        sala2.setStatusSala(STATUS_SALA.EN_REPARACION);
        Sala salaCreated2 = salaRepository.save(sala2);
        log.info("salaCreated2: " + salaCreated2);

        salasCine1.add(sala1);
        salasCine1.add(sala2);
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
