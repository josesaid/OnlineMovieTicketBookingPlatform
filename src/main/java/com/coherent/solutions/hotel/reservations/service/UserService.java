package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.User;
import com.coherent.solutions.hotel.reservations.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


}
