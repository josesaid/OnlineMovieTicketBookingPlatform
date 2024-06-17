package com.coherent.solutions.hotel.reservations.ito;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/*
    This class is used by the Booking RestController class to return information when the user cancels many bookings.
    It let us inform in the JSON response body element what are the booking IDs which were correctly Cancelled
    and which ones were not.
*/
@Data
public class CancelBookingResult {
    private List<Integer> bookingIDsUpdated = new ArrayList<>();
    private List<Integer> bookingIDsNotFound = new ArrayList<>();
}
