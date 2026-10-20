import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/roomDetails")
public class roomDetails extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int roomId = Integer.parseInt(
                request.getParameter("roomid"));

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql:///hotelreservation",
                    "root",
                    "root");

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM rooms WHERE roomid=?");

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Room Details</title>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body>");

            if(rs.next()) {

                String status = rs.getString("status");

                out.println("<div style='width:70%;"
                        + "margin:50px auto;"
                        + "padding:25px;"
                        + "border:1px solid white;"
                        + "border-radius:15px;"
                        + "background:rgba(0,0,0,0.5);'>");

                out.println("<h1 style='text-align:center;'>Room Details</h1>");

                out.println("<h3>Room ID : "
                        + rs.getInt("roomid")
                        + "</h3>");

                out.println("<h3>Room Type : "
                        + rs.getString("roomtype")
                        + "</h3>");

                out.println("<h3>Price : "
                        + rs.getDouble("price")
                        + " Per Night</h3>");

                out.println("<h3>Status : "
                        + status
                        + "</h3>");

                out.println("<h3>Facilities :</h3>");

                String facilities =
                        rs.getString("facilities");

                String[] facilityList =
                        facilities.split(",");

                out.println("<ul>");

                for(String f : facilityList) {

                    out.println("<li>" + f + "</li>");
                }

                out.println("</ul>");

                if(status.equalsIgnoreCase("Available")) {

                    out.println(
                      "<a href='booking.html'>"
                    + "<button class='B'>Book Now</button>"
                    + "</a>");

                } else {

                    out.println(
                    "<h2 style='color:red;'>"
                    + "Room Already Booked"
                    + "</h2>");
                }

                out.println("</div>");

            } else {

                out.println(
                "<h2 style='text-align:center;'>"
                + "Room Not Found"
                + "</h2>");
            }

            out.println("</body>");
            out.println("</html>");

            con.close();

        } catch(Exception e) {

            out.println("<h2>Error : "
                    + e.getMessage()
                    + "</h2>");
        }
    }
}