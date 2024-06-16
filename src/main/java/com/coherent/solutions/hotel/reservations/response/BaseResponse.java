package com.coherent.solutions.hotel.reservations.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseResponse {

    private String type;
    private String message;
    private LocalDateTime dateTime;
    public BaseResponse(String type, String message) {
        super();
        this.type = type;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

}
