package com.coherent.solutions.hotel.reservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.SpringServletContainerInitializer;

/*
    This class extends from SpringServletContainerInitializer to add features required to the application in order to
    be deployed as WAR file.
*/
@SpringBootApplication
public class OnlineMovieTicketBookingPlatformSpringBootApp extends SpringServletContainerInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OnlineMovieTicketBookingPlatformSpringBootApp.class, args);
    }

}
