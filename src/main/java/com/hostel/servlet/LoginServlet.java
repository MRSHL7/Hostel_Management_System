package com.hostel.servlet;

import com.hostel.dao.UserDAO;
import com.hostel.model.User;
import com.hostel.model.User.UserType;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.authenticateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());

            UserType userType = user.getUserType();
            session.setAttribute("userType", userType);

            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            // REDIRECT through SERVLET, not directly to JSP!
            String redirectURL;

            switch (userType) {
                case ADMIN:
                    redirectURL = request.getContextPath() + "/admin/dashboard";   // Note: servlet mapping
                    break;
                case STUDENT:
                    redirectURL = request.getContextPath() + "/student/dashboard"; // Use your student dashboard servlet mapping
                    break;
                case WARDEN:
                    redirectURL = request.getContextPath() + "/admin/dashboard";   // Warden also see admin dashboard
                    break;
                default:
                    redirectURL = request.getContextPath() + "/index.jsp";
            }

            response.sendRedirect(redirectURL);

        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
