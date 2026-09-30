package filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EncodingFilter implements Filter {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        req.setCharacterEncoding(UTF_8);
        resp.setCharacterEncoding(UTF_8);

        chain.doFilter(req, resp);
    }
}
