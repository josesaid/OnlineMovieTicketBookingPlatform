package com.coherent.solutions.hotel.reservations.ito;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CancelBookingResult {
    private List<Integer> bookingIDsUpdated = new ArrayList<>();
    private List<Integer> bookingIDsNotFound = new ArrayList<>();
}
