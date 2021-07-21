package com.weblab.booking.service;

import com.weblab.booking.dao.ReservationDAO;
import com.weblab.booking.dao.SeatDAO;
import com.weblab.booking.entity.Reservation;

import java.util.List;

public class ReservationServiceIMPL implements ReservationService{
    private ReservationDAO rsvdao;
    private SeatDAO seatdao;

    public ReservationServiceIMPL(ReservationDAO rsvdao, SeatDAO seatdao) {
        this.rsvdao = rsvdao;
        this.seatdao = seatdao;
    }

    @Override
    public Reservation getReservation(int rsvSeq) {

        return rsvdao.getReservation(rsvSeq);
    }

    @Override
    public List<Reservation> getReservations(String name, String phone) {

        return rsvdao.getReservations(String.format("NAME='%s' and PHONE='%s'", name, phone));
    }

    @Override
    public int registerReservation(Reservation rsv) {
        int result = 0;
        int rsvSeq = rsvdao.insertReservation(rsv);
        result = seatdao.updateSeat(rsv.getSeatNumbers(), rsvSeq);
        return result;
    }

    @Override
    public int updateReservation(Reservation rsv) {
        Reservation prevRsv = rsvdao.getReservation(rsv.getRsvSeq());
        seatdao.updateSeat(prevRsv.getSeatNumbers(), 0);


        rsvdao.updateReservation(rsv);
        seatdao.updateSeat(rsv.getSeatNumbers(), rsv.getRsvSeq());
        return 1;
    }

    @Override
    public int deleteReservation(Reservation rsv) {
        int result = 0;
        result = rsvdao.deleteReservation(rsv.getRsvSeq());
        result = seatdao.updateSeat(rsv.getSeatNumbers(), 0);
        return result;
    }

    @Override
    public List<Integer> getSeatNumbers(boolean booked) {
        return seatdao.getSeatNumbers(booked);
    }

}
