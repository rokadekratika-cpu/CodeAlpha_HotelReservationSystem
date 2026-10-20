import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


public class BookingServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        int roomId = Integer.parseInt(request.getParameter("roomid"));

         if(!mobile.matches("\\d{10}")){

          response.getWriter().println("<h2>Invalid Mobile Number!</h2>"+ "<p>Mobile number must contain exactly 10 digits.</p>");

         return;
            }

if(roomId < 101 || roomId > 112){

    response.getWriter().println(
        "<h2>Invalid Room ID!</h2>"
      + "<p>Room ID must be between 101 and 112.</p>");

    return;
}
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql:///hotelreservation",
                    "root",
                    "root");

            PreparedStatement checkRoom =
                    con.prepareStatement(
                    "SELECT status FROM rooms WHERE roomid=?");

            checkRoom.setInt(1, roomId);

            ResultSet rs = checkRoom.executeQuery();

            if (rs.next()
                    && rs.getString("status")
                            .equalsIgnoreCase("Available")) {

                PreparedStatement insertBooking =
                        con.prepareStatement(
                        "INSERT INTO bookings(customername,mobile,roomid) VALUES(?,?,?)");
                

                insertBooking.setString(1, name);
                insertBooking.setString(2, mobile);
                insertBooking.setInt(3, roomId);

                insertBooking.executeUpdate();

                PreparedStatement updateRoom =
                        con.prepareStatement(
                        "UPDATE rooms SET status='Booked' WHERE roomid=?");

                updateRoom.setInt(1, roomId);
                updateRoom.executeUpdate();

                response.sendRedirect("payment.html");

            } else {

                response.getWriter().println(
                        "<h2>Room Not Available!</h2>");
            }

            con.close();

        } catch (Exception e) {

            response.getWriter().println(
                    "<h2>Error : " + e.getMessage() + "</h2>");
        }
    }
}