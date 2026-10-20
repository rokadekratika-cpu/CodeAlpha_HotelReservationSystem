import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet { 

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String cardName =
                request.getParameter("cardname");

        String cardNumber =
                request.getParameter("cardnumber");

        String cvv =
                request.getParameter("cvv");

        if(cardNumber.length() != 16) {

            response.getWriter().println(
                "<h2>Invalid Card Number</h2>");

            return;
        }

        if(cvv.length() != 3) {

            response.getWriter().println(
                "<h2>Invalid CVV</h2>");

            return;
        }

        response.sendRedirect(
                "bookingSuccess.html");
    }
}