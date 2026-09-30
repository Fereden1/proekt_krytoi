package servlet;

import entity.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.TransactionService;

import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    private final TransactionService transactionService = new TransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String type = req.getParameter("type"); // null | "income" | "expense"
        List<Transaction> list = transactionService.listForUser(userId, type);

        req.setAttribute("transactions", list);
        req.setAttribute("filterType", type);
        req.setAttribute("totalIncome",  transactionService.totalIncome(userId));
        req.setAttribute("totalExpense", transactionService.totalExpense(userId));
        req.setAttribute("balance",      transactionService.balance(userId));

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}