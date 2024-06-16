package com.coherent.solutions.hotel.reservations.response;

import lombok.ToString;

@ToString
public class TheatreResponse extends  BaseResponse{

    public TheatreResponse(String type, String message) {
        super(type, message);
    }

}
