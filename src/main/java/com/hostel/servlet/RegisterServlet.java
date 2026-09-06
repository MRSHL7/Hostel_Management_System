package com.hostel.servlet;

import com.hostel.dao.UserDAO;
import com.hostel.model.User;
import com.hostel.model.User.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form values
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");  // TODO: Hash password in production!
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");

        // Validate input here as needed...

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password); // TODO: hash before saving
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setUserType(UserType.STUDENT);
        newUser.setActive(true);

        boolean success = userDAO.registerUser(newUser);
        if (success) {
            // Redirect to login page after successful registration
            response.sendRedirect(request.getContextPath() + "/login?registerSuccess=true");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Username or email might already exist.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
