package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.Reservation;
import com.coherent.solutions.hotel.reservations.entity.User;
import com.coherent.solutions.hotel.reservations.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Iterable<User> getAllUsers() {
        log.info("Retrieving all the users");
        return userRepository.findAll();
    }

    public User createUser(User user) {
        log.info("Creating an user");
        User userCreated = userRepository.save(user);
        return userCreated;
    }

    public Optional<User> getUser(int userId) {
        log.info("Retrieving an user");
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional;
    }

    public User saveUser(User user) {
        log.info("Updating an user");
        return userRepository.save(user);
    }

    public void removeUser(int userId) {
        log.info("Removing an user");
        userRepository.deleteById(userId);
    }


}
