package coreservlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/** Servlet that reads a customer ID and displays
 *  information on the account balance of the customer
 *  who has that ID.
 */
@WebServlet("/show-balance")
public class ShowBalance extends HttpServlet {
  private static final long serialVersionUID = -3455685360667483044L;

  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    BankCustomer currentCustomer =
      BankCustomerLookup.getCustomer(request.getParameter("id"));
    request.setAttribute("customer", currentCustomer);
    String address;
    if (currentCustomer == null) {
      address = "/WEB-INF/bank-account/UnknownCustomer.jsp";
    } else if (currentCustomer.getBalance() < 0) {
      address = "/WEB-INF/bank-account/NegativeBalance.jsp";
    } else if (currentCustomer.getBalance() < 10000) {
      address = "/WEB-INF/bank-account/NormalBalance.jsp";
    } else {
      address = "/WEB-INF/bank-account/HighBalance.jsp";
    }
    RequestDispatcher dispatcher =
      request.getRequestDispatcher(address);
    dispatcher.forward(request, response);
  }
}
