import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


public class CancelBooking extends HttpServlet {

    public void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        int roomId = Integer.parseInt(
                request.getParameter("roomid"));

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql:///hotelreservation",
                    "root",
                    "root");

            PreparedStatement deleteBooking =
                    con.prepareStatement(
                    "DELETE FROM bookings WHERE roomid=?");

            deleteBooking.setInt(1, roomId);

            int deleted = deleteBooking.executeUpdate();

            if(deleted > 0) {

                PreparedStatement updateRoom =
                        con.prepareStatement(
                        "UPDATE rooms SET status='Available' WHERE roomid=?");

                updateRoom.setInt(1, roomId);
                updateRoom.executeUpdate();

                response.sendRedirect("cancelSuccess.html");
            } else {

                response.getWriter().println(
                        "<h2>No Reservation Found</h2>");
            }

            con.close();

        } catch(Exception e) {

            response.getWriter().println(
                    "<h2>Error : " + e.getMessage() + "</h2>");
        }
    }
}