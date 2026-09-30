package servlet;

import dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirmPassword");
        String name = req.getParameter("name");

        try {
            if (password == null || !password.equals(confirm)) {
                throw new IllegalArgumentException("Пароли не совпадают");
            }

            UserDto dto = userService.register(login, password, name);

            req.getSession().setAttribute("flash", "Регистрация успешна. Войдите как " + dto.getLogin());
            resp.sendRedirect(req.getContextPath() + "/login");

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("login", login);
            req.setAttribute("name", name);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}