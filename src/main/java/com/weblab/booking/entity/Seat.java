package com.weblab.booking.entity;

public class Seat {
    private int number;
    private String type;
    private int rsvSeq;

    public Seat(){}

    public Seat(int number, String type, int rsvSeq) {
        this.number = number;
        this.type = type;
        this.rsvSeq = rsvSeq;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) { this.type = type; }

    public int getRsvSeq() { return rsvSeq; }

    public void setRsvSeq(int rsvSeq) {
        this.rsvSeq = rsvSeq;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "number=" + number +
                ", type='" + type + '\'' +
                ", rsvSeq=" + rsvSeq +
                '}';
    }

}



