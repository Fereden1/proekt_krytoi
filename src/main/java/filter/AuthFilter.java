package filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.isEmpty()) {
            path = "/";
        }

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    private boolean isPublic(String path) {
        return path.equals("/")
                || path.equals("/index.jsp")
                || path.equals("/login")
                || path.equals("/register")
                || path.equals("/logout")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.endsWith(".ico");
    }
}
