package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.entity.User;
import com.coherent.solutions.hotel.reservations.repository.TheatreRepository;
import com.coherent.solutions.hotel.reservations.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;
    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }
    public Iterable<Theatre> getAllTheathres(){
        log.info("Retrieving all the theatres");
        return theatreRepository.findAll();
    }

    public Theatre createTheatre(Theatre theatre) {
        log.info("Creating an theatre");
        Theatre theatreCreated = theatreRepository.save(theatre);
        return theatreCreated;
    }

    public Optional<Theatre> getTheatre(int theatreId) {
        log.info("Retrieving an user");
        Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
        return theatreOptional;
    }

    public Theatre saveTheatre(Theatre theatre) {
        log.info("Updating an Theatre");
        return theatreRepository.save(theatre);
    }

    public void removeTheatre(int theatreId) {
        log.info("Removing an theatre");
        theatreRepository.deleteById(theatreId);
    }


}
