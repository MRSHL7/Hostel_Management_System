
package com.hostel.servlet;


import com.hostel.dao.UserDAO;
import com.hostel.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/student/profile")
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    // Show Update Profile form with current user details
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User sessionUser = (User) request.getSession().getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Reload fresh user details from DB (optional)
        User user = userDAO.getUserById(sessionUser.getUserId());
        request.setAttribute("user", user);

        request.getRequestDispatcher("/student/profile.jsp").forward(request, response);
    }

    // Process form submission for profile update
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User sessionUser = (User) request.getSession().getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Read form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");

        // Create user object with updated data
        User user = new User();
        user.setUserId(sessionUser.getUserId());
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setUserType(sessionUser.getUserType());
        user.setActive(true);

        // Update user in DB
        boolean success = userDAO.updateUser(user);

        if (success) {
            // Update session user data
            request.getSession().setAttribute("user", user);
            request.setAttribute("successMessage", "Profile updated successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to update profile. Try again.");
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/student/profile.jsp").forward(request, response);
    }
}
