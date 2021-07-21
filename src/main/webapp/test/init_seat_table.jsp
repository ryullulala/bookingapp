<%@ page import="com.weblab.booking.dao.*" %>
<%@ page import="com.weblab.booking.entity.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    SeatDAO seatdao = new SeatJdbcDAO(
            application.getInitParameter("jdbc_driver"),
            application.getInitParameter("db_url"),
            application.getInitParameter("db_userid"),
            application.getInitParameter("db_passwd")
            );
    List<Seat> seats = seatdao.getSeats("SEAT_TYPE = 'R'");
    if (seats != null) {
        out.println("<h2>SEAT 테이블이 이미 초기화 되어 있습니다...</h2>");
    }
    else {
        Seat seat = new Seat();
        seat.setRsvSeq(0);
        seat.setType("R");
        for (int i=1; i<=20; i++) {
            seat.setNumber(i);
            seatdao.insertSeat(seat);
        }
        seat.setType("A");
        for (int i=21; i<=50; i++) {
            seat.setNumber(i);
            seatdao.insertSeat(seat);
        }
        seat.setType("B");
        for (int i=50; i<=100; i++) {
            seat.setNumber(i);
            seatdao.insertSeat(seat);
        }
    }
    seats = seatdao.getSeats("");
    for(Seat seat : seats) {
        out.println(seat.toString() + "<br>");
    }

%>

</body>
</html>
