package com.weblab.booking.entity;

import java.util.Arrays;
import java.util.Date;

public class Reservation {
    private int rsvSeq;
    private String name;
    private String passwd;
    private String phone;
    private Date rsvDate;
    private int[] seatNumbers;

    public int getRsvSeq() {
        return rsvSeq;
    }

    public void setRsvSeq(int rsvSeq) { this.rsvSeq = rsvSeq; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRsvDate() {
        return rsvDate;
    }

    public void setRsvDate(Date rsvDate) {
        this.rsvDate = rsvDate;
    }

    public int[] getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(int[] seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "rsvSeq=" + rsvSeq +
                ", name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", phone='" + phone + '\'' +
                ", rsvDate=" + rsvDate +
                ", seatNumbers=" + Arrays.toString(seatNumbers) +
                '}';
    }

}
