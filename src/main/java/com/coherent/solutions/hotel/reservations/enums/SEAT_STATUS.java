package com.coherent.solutions.hotel.reservations.enums;

/*
    This SEAT_STATUS enum contains all the possible values for the Room.seatsMap property.
    It is important to mention seatsMap is a Java Map with a key-value structure where
    key is the name is the seat e.g. A1, A2, A3, etc.
    Meanwhile, value can be FREE or RESERVED, so all this information is queried by a room
    to know is a specific seat is FREE or RESERVED.
*/
public enum SEAT_STATUS {
    FREE,
    RESERVED
}
