package com.weblab.booking.dao;

import com.weblab.booking.entity.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatJdbcDAO implements SeatDAO{
    private String driver;
    private String url;
    private String userName;
    private String password;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public SeatJdbcDAO(String driver, String url, String userName, String password) {
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, userName, password);
    }
    private void disconnect() throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
            rs = null;
        }
        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
            stmt = null;
        }
        if (conn != null && !conn.isClosed()) {
            conn.close();
            conn = null;
        }
    }
    @Override
    public Seat getSeat(int number) {
        Seat seat = null;

        String sql = "SELECT * FROM SEAT WHERE SEAT_NUMBER = ?";

        try {
            connect();

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, number);

            rs = stmt.executeQuery();

            if (rs.next()) {
                seat = new Seat();
                seat.setNumber(rs.getInt("SEAT_NUMBER"));
                seat.setType(rs.getString("SEAT_TYPE"));
                seat.setRsvSeq(rs.getInt("RSV_SEQ"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return seat;
    }

    @Override
    public List<Seat> getSeats(String query) {  // WHERE RSV_SEQ > 0
        List<Seat> seats = null;

        String sql = "SELECT * FROM SEAT";
        sql = sql + (query != null && !query.equals("") ? " WHERE " + query : " ORDER BY SEAT_NUMBER");

        try {
            connect();

            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();

            if (rs.isBeforeFirst()) {
                seats = new ArrayList<Seat>();
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setNumber(rs.getInt("SEAT_NUMBER"));
                    seat.setType(rs.getString("SEAT_TYPE"));
                    seat.setRsvSeq(rs.getInt("RSV_SEQ"));

                    seats.add(seat);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return seats;
    }

    @Override
    public int insertSeat(Seat seat) {
        int result = 0;

        String sql = "INSERT INTO SEAT (SEAT_NUMBER, SEAT_TYPE, RSV_SEQ) VALUES (?, ?, ?)";

        try {
            connect();

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, seat.getNumber());
            stmt.setString(2, seat.getType());
            stmt.setInt(3, seat.getRsvSeq());

            result = stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public int updateSeat(Seat seat) {
        int result = 0;

        String sql = "UPDATE SEAT SET SEAT_TYPE = ?, RSV_SEQ = ? WHERE SEAT_NUMBER = ?";

        try {
            connect();

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, seat.getType());
            stmt.setInt(2, seat.getRsvSeq());
            stmt.setInt(3, seat.getNumber());

            result = stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public int deleteSeat(int number) {
        int result = 0;

        String sql = "DELETE FROM SEAT WHERE SEAT_NUMBER = ?";

        try {
            connect();

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, number);

            result = stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public int updateSeat(int[] seats, int rsvSeq) {
        int result = 0;

        String numList = "";
        for (int i=0; i<seats.length-1; i++ ) {
            numList += seats[i] + ", ";
        }
        numList += seats[seats.length-1];

        String sql = "UPDATE SEAT SET RSV_SEQ = ? WHERE SEAT_NUMBER IN (" + numList + ")";

        try {
            connect();

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, rsvSeq);

            result = stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public List<Integer> getSeatNumbers(boolean booked) {
        List<Integer> seatNumbers = null;

        String sql = "SELECT SEAT_NUMBER FROM SEAT WHERE RSV_SEQ " + (booked ? ">" : "=") + " 0";

        try {
            connect();

            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();

            if (rs.isBeforeFirst()) {
                seatNumbers = new ArrayList<Integer>();
                while (rs.next()) {
                    seatNumbers.add(rs.getInt("SEAT_NUMBER"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return seatNumbers;
    }

}