package com.coherent.solutions.hotel.reservations.configuration;

import com.coherent.solutions.hotel.reservations.entity.Reservation;
import com.coherent.solutions.hotel.reservations.repository.ReservationRepository;
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

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        generateInitialReservationsVersionCustomizedReservation();
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
