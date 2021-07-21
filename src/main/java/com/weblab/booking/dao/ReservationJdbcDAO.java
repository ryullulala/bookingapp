package com.weblab.booking.dao;

import com.google.gson.Gson;
import com.weblab.booking.entity.Reservation;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationJdbcDAO implements ReservationDAO{
    private String driver;
    private String url;
    private String userName;
    private String password;

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public ReservationJdbcDAO(String driver, String url, String userName, String password) {
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
    public Reservation getReservation(int rsvSeq) {
        Reservation rsv = null;

        String sql = "SELECT * FROM RESERVATION WHERE RSV_SEQ = ?";

        try {
            connect();

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, rsvSeq);

            rs = stmt.executeQuery();

            if (rs.next()) {
                rsv = new Reservation();
                rsv.setRsvSeq(rs.getInt("RSV_SEQ"));
                rsv.setName(rs.getString("NAME"));
                rsv.setPasswd(rs.getString("PASSWD"));
                rsv.setPhone(rs.getString("PHONE"));
                rsv.setRsvDate(rs.getDate("RSV_DATE"));
                rsv.setSeatNumbers((new Gson()).fromJson(rs.getString("SEAT_NUMBER"), int[].class));
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

        return rsv;
    }

    @Override
    public List<Reservation> getReservations(String query) {
        List<Reservation> rsvList = null;

        String sql = "SELECT * FROM RESERVATION";
        sql = sql + (query != null && !query.equals("") ? " WHERE " + query : " ORDER BY RSV_SEQ DESC");

        try {
            connect();

            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();

            if (rs.isBeforeFirst()) {
                rsvList = new ArrayList<Reservation>();
                while (rs.next()) {
                    Reservation rsv = new Reservation();
                    rsv.setRsvSeq(rs.getInt("RSV_SEQ"));
                    rsv.setName(rs.getString("NAME"));
                    rsv.setPasswd(rs.getString("PASSWD"));
                    rsv.setPhone(rs.getString("PHONE"));
                    rsv.setRsvDate(rs.getDate("RSV_DATE"));
                    rsv.setSeatNumbers((new Gson()).fromJson(rs.getString("SEAT_NUMBER"), int[].class));

                    rsvList.add(rsv);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rsvList;
    }

    @Override
    public int insertReservation(Reservation rsv) {
        int result = 0;

        String sql = "INSERT INTO RESERVATION (NAME, PASSWD, PHONE, SEAT_NUMBER) VALUES (?, ?, ?, ?)";
        String sql2 = "SELECT RSV_SEQ_GEN.CURRVAL FROM DUAL";

        try {
            connect();

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, rsv.getName());
            stmt.setString(2, rsv.getPasswd());
            stmt.setString(3, rsv.getPhone());
            stmt.setString(4, (new Gson()).toJson(rsv.getSeatNumbers()));

            result = stmt.executeUpdate();

            if (result == 1) {
                stmt = conn.prepareStatement(sql2);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("CURRVAL");
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

        return result;
    }

    @Override
    public int updateReservation(Reservation rsv) {
        int result = 0;

        String sql = "UPDATE RESERVATION SET NAME=?, PASSWD=?, PHONE=?, SEAT_NUMBER=?, RSV_DATE = SYSTIMESTAMP WHERE RSV_SEQ = ?";

        try {
            connect();

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, rsv.getName());
            stmt.setString(2, rsv.getPasswd());
            stmt.setString(3, rsv.getPhone());
            stmt.setString(4, (new Gson()).toJson(rsv.getSeatNumbers()));
            stmt.setInt(5, rsv.getRsvSeq());

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
    public int deleteReservation(int rsvSeq) {
        int result = 0;

        String sql = "DELETE FROM RESERVATION WHERE RSV_SEQ = ?";

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

}
