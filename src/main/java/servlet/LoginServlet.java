package servlet;

import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.UserService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType("text/html; charset=UTF-8");

        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("error", "Введите логин и пароль");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        try {
            User user = userService.login(login, password);

            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userLogin", user.getLogin());
            session.setAttribute("userName", user.getName());
            session.setMaxInactiveInterval(60 * 60);

            Cookie cookie = new Cookie("userLogin", user.getLogin());
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            resp.addCookie(cookie);

            resp.sendRedirect(req.getContextPath() + "/index");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}