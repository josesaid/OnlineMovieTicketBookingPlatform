package com.coherent.solutions.hotel.reservations.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
/*
    Top Level class in the Response body hierarchy level when we want to send a message to the user in the
    response body. The other classes located at same level of this one, extend from BaseResponse.
*/
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
