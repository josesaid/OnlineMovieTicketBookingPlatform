package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.Reservation;
import com.coherent.solutions.hotel.reservations.entity.User;
import com.coherent.solutions.hotel.reservations.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
@Tag(name = "Users", description = "USERS API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all the users in the system", description = "This method lets to retrieve all the users " +
            "from the MySQL AWS DB instance. By default, when the app starts up, it automatically inserts some users " +
            "on the users table to start to play with the app.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Users found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})})
    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> userIterable = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userIterable);
    }


}
