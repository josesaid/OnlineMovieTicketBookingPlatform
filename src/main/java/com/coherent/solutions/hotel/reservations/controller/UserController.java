package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.User;
import com.coherent.solutions.hotel.reservations.response.UserResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @Operation(summary = "Creates an user in the database.",
            description = "This method generates an user entry at the MySQL AWS database instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User created",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})})
    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody User user){
        User userCreated = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @Operation(summary = "Retrieves an user", description = "This method lets to retrieve an user from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User gotten",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id) {
        try{
            Integer.parseInt(id);
        }catch(NumberFormatException e){
            String message = "USER ID has an incorrect format: " + id;
            log.error(message);
            return new ResponseEntity<>(new UserResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<User> userOptional = userService.getUser(Integer.parseInt(id));
        if(userOptional.isPresent())
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(new UserResponse("ERROR", "User ID: " + id + " was NOT found on the DB"), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates an user",
            description = "This method updates an user object from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        String message = null;
        int userId = -1;
        try{
            userId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "User ID has an incorrect format: " + userId;
            log.error(message);
            return new ResponseEntity<>(new UserResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }

        // Retrieve the resource from the database
        Optional<User> userOptional = userService.getUser(Integer.parseInt(id));

        // If the resource is not found, return a 404 (not found) status code
        if (!userOptional.isPresent()) {
            message = "User ID: " + id + " was found found on the DB";
            log.error(message);
            return new ResponseEntity<>(new UserResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }

        // Update the resource
        user.setId(userId);
        log.info("user incoming: " + user);
        User userUpdated = userService.saveUser(user);
        log.info("userUpdated: " + userUpdated);

        // Return the updated resource with a 200 (OK) status code
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }


    @Operation(summary = "Deletes an user", description = "This method deletes an user from database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User removed",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id){
        String message = "User ID: "+  id + " deleted correctly.";
        int userId = -1;
        try{
            userId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "User ID has an incorrect format: " + userId;
            log.error(message);
            return new ResponseEntity<>(new UserResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<User> userOptional = userService.getUser(Integer.parseInt(id));
        if(!userOptional.isPresent()) {
            message = "User ID: " + id + " was not found on the DB";
            log.error(message);
            return new ResponseEntity<>(new UserResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }else {
            message = "User ID: " + id + " removed from the DB";
            userService.removeUser(userId);
            log.info(message);
            return new ResponseEntity<>(new UserResponse("SUCCESS", message), HttpStatus.ACCEPTED);
        }
    }

}
