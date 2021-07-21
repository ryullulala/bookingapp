package com.weblab.booking.dao;

import com.weblab.booking.entity.Reservation;

import java.util.List;

public interface ReservationDAO {
    Reservation getReservation(int rsvSeq);
    List<Reservation> getReservations(String query);
    int insertReservation(Reservation rsv);
    int updateReservation(Reservation rsv);
    int deleteReservation(int rsvSeq);

}
