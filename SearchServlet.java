import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql:///hotelreservation",
                    "root",
                    "root");

            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery(
                    "SELECT COUNT(*) FROM rooms");

            rs1.next();
            int totalRooms = rs1.getInt(1);

            ResultSet rs2 = st.executeQuery(
                    "SELECT COUNT(*) FROM rooms WHERE status='Available'");

            rs2.next();
            int availableRooms = rs2.getInt(1);

            ResultSet rs3 = st.executeQuery(
                    "SELECT COUNT(*) FROM rooms WHERE status='Booked'");

            rs3.next();
            int bookedRooms = rs3.getInt(1);

            out.println("<html><head>");
            out.println("<title>Room Dashboard</title>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("</head><body>");

            out.println("<h1 style='text-align:center;'>Hotel Room Status</h1>");

            out.println("<div style='width:80%;margin:auto;"
                    + "padding:15px;border:1px solid white;"
                    + "border-radius:10px;'>");

            out.println("<h2>Total Rooms : " + totalRooms + "</h2>");
            out.println("<h2>Available Rooms : " + availableRooms + "</h2>");
            out.println("<h2>Booked Rooms : " + bookedRooms + "</h2>");

            out.println("</div><br>");

            ResultSet rooms = st.executeQuery(
                    "SELECT * FROM rooms");

            out.println("<table style='width:90%;"
                    + "margin:auto;"
                    + "border-collapse:collapse;"
                    + "text-align:center;'>");

            out.println("<tr>");
            out.println("<th style='border:1px solid white;padding:12px;'>Room ID</th>");
            out.println("<th style='border:1px solid white;padding:12px;'>Room Type</th>");
            out.println("<th style='border:1px solid white;padding:12px;'>Price</th>");
            out.println("<th style='border:1px solid white;padding:12px;'>Status</th>");
            out.println("<th style='border:1px solid white;padding:12px;'>Details</th>");
            out.println("</tr>");

            while (rooms.next()) {

                int roomId = rooms.getInt("roomid");

                out.println("<tr>");

                out.println("<td style='border:1px solid white;padding:10px;'>"
                        + roomId + "</td>");

                out.println("<td style='border:1px solid white;padding:10px;'>"
                        + rooms.getString("roomtype") + "</td>");

                out.println("<td style='border:1px solid white;padding:10px;'>"
                        + rooms.getDouble("price") + "</td>");

                out.println("<td style='border:1px solid white;padding:10px;'>"
                        + rooms.getString("status") + "</td>");

                out.println("<td style='border:1px solid white;padding:10px;'>"
                        + "<a href='roomDetails?roomid="
                        + roomId
                        + "'>View Details</a></td>");

                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

            con.close();

        } catch (Exception e) {

            out.println("<h2>Error : " + e.getMessage() + "</h2>");
        }
    }
}