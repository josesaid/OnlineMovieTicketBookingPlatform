package com.coherent.solutions.hotel.reservations.response;

import lombok.ToString;

@ToString
public class UserResponse extends BaseResponse{
    public UserResponse(String type, String message) {
        super(type, message);
    }

}