package com.weblab.booking.dao;

import com.weblab.booking.entity.Seat;

import java.util.List;

public interface SeatDAO {
    //CRUD (Create, Retrieve, Update, Delete)
    Seat getSeat(int number);
    List<Seat> getSeats(String query);
    int insertSeat(Seat seat);
    int updateSeat(Seat seat);
    int updateSeat(int[] seats, int rsvSeq);
    int deleteSeat(int number);
    List<Integer> getSeatNumbers(boolean booked);

}
