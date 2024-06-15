package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.Reservation;
import com.coherent.solutions.hotel.reservations.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Iterable<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservation(int reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        return reservationOptional;
    }
    public void removeReservation(int reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public boolean verifyReservationInfo(Reservation reservation) {
        boolean error = false;
        log.info("Verifying before to reserve: " + reservation);
        List<LocalDate> reservationDates = reservation.getReservationDates();
        int wishedRoomNumber = reservation.getRoomNumber();
        for(int i=0; !error && i<reservationDates.size(); i++){
            LocalDate localDate = reservationDates.get(i);
            String wishedReservationDate = localDate.toString();

            //Search each wishedReservationDate versus reservationsAtDB
            List<Reservation> reservationListAtDB = new ArrayList<>((Collection) reservationRepository.findAll());
            for(int j=0; !error && j < reservationListAtDB.size(); j++) {
                Reservation reservationAtDB = reservationListAtDB.get(j);
                List<LocalDate> localDateList = reservationAtDB.getReservationDates();
                for (int k = 0; !error && k < localDateList.size(); k++) {
                    LocalDate localDateAtDB = localDateList.get(k);
                    String reservationDateAtDB = localDateAtDB.toString();

                    //Search each date from the user coming versus the ones recorded at the DB.
                    if (reservationDateAtDB.equals(wishedReservationDate)) {
                        //It means, we can't reserve, and we need to inform the user this reservation error.
                        log.info("user date: " + wishedReservationDate + " exists on the DB");

                        //Finally, check if the room number is also reserved, if so, we can not reserve the room on that date:
                        int roomNumberAtDB = reservationAtDB.getRoomNumber();
                        if (roomNumberAtDB == wishedRoomNumber) {
                            error = true;
                            log.info("The room: " + roomNumberAtDB + " is already reserved on:" + wishedReservationDate + ", sorry for this inconvenient.");
                        }
                    }
                }
            }
        }
        // If there is no Error, then returns success(true), otherwise, return error(false).
        return (!error)?true:false;
    }

}
