package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.TransactionService;

import java.io.IOException;

@WebServlet("/transactions/delete")
public class TransactionDeleteServlet extends HttpServlet {

    private final TransactionService transactionService = new TransactionService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        transactionService.delete(userId, id);
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}