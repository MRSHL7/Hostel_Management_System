package com.hostel.servlet;

import com.hostel.model.User;
import com.hostel.model.User.UserType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authentication & Authorization filter
 */
@WebFilter(urlPatterns = {"/admin/*", "/student/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // ✅ Allow login & static resources without auth
        if (requestURI.endsWith("login.jsp") ||
            requestURI.endsWith("login") ||
            requestURI.contains("dashboard.jsp") ||
            requestURI.endsWith(".css") ||
            requestURI.endsWith(".js") ||
            requestURI.endsWith(".png") ||
            requestURI.endsWith(".jpg") ||
            requestURI.endsWith(".gif")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user != null) {
            UserType role = user.getUserType();

            // ✅ Role-based access control
            if (requestURI.startsWith(contextPath + "/admin") && role == UserType.ADMIN) {
                chain.doFilter(request, response);
            } else if (requestURI.startsWith(contextPath + "/student") && role == UserType.STUDENT) {
                chain.doFilter(request, response);
            } else {
                // Forbidden if role mismatch
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            }

        } else {
            // Not logged in → redirect to login
            httpResponse.sendRedirect(contextPath + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
