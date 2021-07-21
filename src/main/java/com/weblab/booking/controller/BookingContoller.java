package com.weblab.booking.controller;


import com.weblab.booking.dao.ReservationDAO;
import com.weblab.booking.dao.ReservationJdbcDAO;
import com.weblab.booking.dao.SeatDAO;
import com.weblab.booking.dao.SeatJdbcDAO;
import com.weblab.booking.entity.Reservation;
import com.weblab.booking.service.ReservationService;
import com.weblab.booking.service.ReservationServiceIMPL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class BookingContoller extends HttpServlet {
    private ReservationService rsvService = null;
    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        String driver = context.getInitParameter("jdbc_driver");
        String url = context.getInitParameter("db_url");
        String userName = context.getInitParameter("db_userid");
        String password = context.getInitParameter("db_passwd");

        SeatDAO seatdao = new SeatJdbcDAO(driver, url, userName, password);
        ReservationDAO rsvdao = new ReservationJdbcDAO(driver, url, userName, password);
        rsvService = new ReservationServiceIMPL(rsvdao, seatdao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. GET REQUEST PARAMETERS
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI();
        String viewName = null;
        // step #2. data processing
        if ("/index".equals(path)) {
            viewName = "index.jsp";
        }
        else if ("/register".equals(path)) {
            String method = request.getMethod().toUpperCase();

            if (method.equals("GET")) {
                int[] seats = new int[100];

                List<Integer> seatNumbers = rsvService.getSeatNumbers(true);
                if (seatNumbers != null && seatNumbers.size() > 0) {
                    for (int number : seatNumbers) {
                        seats[number-1] = 1;
                    }
                }

                request.setAttribute("seats", seats);

                viewName = "register.jsp";
            }
            else if (method.equals("POST")) {
                String name = request.getParameter("name");
                String passwd = request.getParameter("passwd");
                String phone = request.getParameter("phone");

                Reservation rsv = new Reservation();
                rsv.setName(name);
                rsv.setPasswd(passwd);
                rsv.setPhone(phone);

                String[] seats = request.getParameterValues("seats");
                if (seats.length > 0) {
                    int[] seatNumbers = new int[seats.length];
                    for (int i=0; i<seats.length; i++) {
                        seatNumbers[i] = (seats[i].charAt(0)-'A') * 10 + Integer.parseInt(seats[i].substring(1));
                    }
                    rsv.setSeatNumbers(seatNumbers);
                }
                else {
                    rsv.setSeatNumbers(new int[0]);
                };

                rsvService.registerReservation(rsv);

                List<Reservation> rsvList = rsvService.getReservations(name, phone);

                request.setAttribute("name", name);
                request.setAttribute("rsv_list", rsvList);

                viewName = "confirm.jsp";
            }
        }
        else if ("/confirm".equals(path)) {
            String method = request.getMethod().toUpperCase();

            if (method.equals("POST")) {
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");

                List<Reservation> rsvList = rsvService.getReservations(name, phone);

                request.setAttribute("name", name);
                request.setAttribute("rsv_list", rsvList);

                viewName = "confirm.jsp";
            }
        }
        else if ("/delete".equals(path)) {
            String method = request.getMethod().toUpperCase();

            if (method.equals("POST")) {
                String seq = request.getParameter("seq");
                String passwd = request.getParameter("passwd");
                String message = null;

                int rsvSeq = Integer.parseInt(seq);
                Reservation rsv = rsvService.getReservation(rsvSeq);
                if (passwd.equals(rsv.getPasswd())) {
                    if (rsvService.deleteReservation(rsv) <= 0) {
                        message = "예약취소 작업중에 오류가 발생하여 취소가 되지 않았습니다.";
                    }
                }
                else {
                    message = "입력하신 비밀번호를 틀렸습니다!";
                }

                List<Reservation> rsvList = rsvService.getReservations(rsv.getName(), rsv.getPhone());

                request.setAttribute("name", rsv.getName());
                request.setAttribute("rsv_list", rsvList);
                if (message != null) {
                    request.setAttribute("message", message);
                }

                viewName = "confirm.jsp";
            }
        }
        else if ("/update".equals(path)) {
            String method = request.getMethod().toUpperCase();

            if (method.equals("POST")) {
                String seq = request.getParameter("seq");
                String passwd = request.getParameter("passwd");
                String message = null;

                int rsvSeq = Integer.parseInt(seq);
                Reservation rsv = rsvService.getReservation(rsvSeq);
                if (passwd.equals(rsv.getPasswd())) {
                    int[] seats = new int[100];

                    List<Integer> seatNumbers = rsvService.getSeatNumbers(true);
                    if (seatNumbers != null && seatNumbers.size() > 0) {
                        for (int number : seatNumbers) {
                            seats[number-1] = 1;
                        }
                    }

                    int[] bookedSeats = rsv.getSeatNumbers();
                    if (bookedSeats != null && bookedSeats.length > 0) {
                        for (int number : bookedSeats) {
                            seats[number-1] = 2;
                        }
                    }

                    request.setAttribute("seats", seats);
                    request.setAttribute("rsv", rsv);

                    viewName = "update.jsp";
                }
                else {
                    message = "입력하신 비밀번호를 틀렸습니다!";

                    List<Reservation> rsvList = rsvService.getReservations(rsv.getName(), rsv.getPhone());

                    request.setAttribute("name", rsv.getName());
                    request.setAttribute("rsv_list", rsvList);
                    request.setAttribute("message", message);

                    viewName = "confirm.jsp";
                }

            }
        }
        else if ("/update_proc".equals(path)) {
            String method = request.getMethod().toUpperCase();

            if (method.equals("POST")) {
                String name = request.getParameter("name");
                String passwd = request.getParameter("passwd");
                String phone = request.getParameter("phone");
                String seq = request.getParameter("seq");

                Reservation rsv = new Reservation();
                rsv.setRsvSeq(Integer.parseInt(seq));
                rsv.setName(name);
                rsv.setPasswd(passwd);
                rsv.setPhone(phone);

                String[] seats = request.getParameterValues("seats");
                if (seats.length > 0) {
                    int[] seatNumbers = new int[seats.length];
                    for (int i=0; i<seats.length; i++) {
                        seatNumbers[i] = (seats[i].charAt(0)-'A') * 10 + Integer.parseInt(seats[i].substring(1));
                    }
                    rsv.setSeatNumbers(seatNumbers);
                }
                else {
                    rsv.setSeatNumbers(new int[0]);
                };

                rsvService.updateReservation(rsv);

                List<Reservation> rsvList = rsvService.getReservations(name, phone);

                request.setAttribute("name", name);
                request.setAttribute("rsv_list", rsvList);

                viewName = "confirm.jsp";
            }
        }

        // step #3. output processing results
        if (viewName != null) {
            viewName = viewName.trim();

            viewName = "/WEB-INF/views/" + viewName;

            RequestDispatcher view = request.getRequestDispatcher(viewName);
            view.forward(request, response);
        }
        else {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append("지원되지 않는 URL입니다...");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}