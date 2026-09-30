package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.CategoryService;
import service.TransactionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/transactions/add")
public class TransactionAddServlet extends HttpServlet {

    private final TransactionService transactionService = new TransactionService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("categories", categoryService.all());
        req.getRequestDispatcher("/transaction-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String type = req.getParameter("type");
        String amountStr = req.getParameter("amount");
        String categoryIdStr = req.getParameter("categoryId");
        String dateStr = req.getParameter("operationDate");

        try {
            BigDecimal amount = new BigDecimal(amountStr);
            Integer categoryId = (categoryIdStr == null || categoryIdStr.isBlank())
                    ? null : Integer.valueOf(categoryIdStr);
            LocalDate date = LocalDate.parse(dateStr);

            transactionService.add(userId, type, amount, categoryId, date);
            resp.sendRedirect(req.getContextPath() + "/index");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("categories", categoryService.all());
            req.setAttribute("type", type);
            req.setAttribute("amount", amountStr);
            req.setAttribute("categoryId", categoryIdStr);
            req.setAttribute("operationDate", dateStr);
            req.getRequestDispatcher("/transaction-form.jsp").forward(req, resp);
        }
    }
}