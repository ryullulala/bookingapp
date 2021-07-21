package com.weblab.booking.service;

import com.weblab.booking.entity.Reservation;
import com.weblab.booking.entity.Seat;

import java.util.List;

public interface ReservationService {
    Reservation getReservation(int rsvSeq);
    List<Reservation> getReservations(String name, String phone);
    int registerReservation(Reservation rsv);
    int updateReservation(Reservation rsv);
    int deleteReservation(Reservation rsv);

    List<Integer> getSeatNumbers(boolean booked);
}
