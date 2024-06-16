package com.coherent.solutions.hotel.reservations.response;

import lombok.ToString;
@ToString
public class EventResponse extends BaseResponse{
    public EventResponse(String type, String message) {
        super(type, message);
    }


}
