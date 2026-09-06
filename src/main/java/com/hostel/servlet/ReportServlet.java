package com.hostel.servlet;

import com.hostel.dao.RoomDAO;
import com.hostel.dao.StudentDAO;
import com.hostel.dao.FeeDAO;
import com.hostel.model.User;
import com.hostel.model.User.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

@WebServlet("/admin/reports")
public class ReportServlet extends HttpServlet {

    private RoomDAO roomDAO;
    private StudentDAO studentDAO;
    private FeeDAO feeDAO;

    @Override
    public void init() throws ServletException {
        roomDAO = new RoomDAO();
        studentDAO = new StudentDAO();
        feeDAO = new FeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null || user.getUserType() != UserType.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int totalRooms = roomDAO.getTotalRooms();
        int occupiedRooms = roomDAO.getOccupiedRooms();
        int availableRooms = roomDAO.getAvailableRooms();
        int totalStudents = studentDAO.getTotalStudents();
        int pendingApplications = studentDAO.getPendingApplications();  // Assuming method exists

        double monthlyRevenueValue = feeDAO.getMonthlyRevenue();

        // Format monthly revenue in Indian currency format (e.g., ₹1,23,456.00)
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String monthlyRevenue = formatter.format(monthlyRevenueValue);

        request.setAttribute("totalRooms", totalRooms);
        request.setAttribute("occupiedRooms", occupiedRooms);
        request.setAttribute("availableRooms", availableRooms);
        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("pendingApplications", pendingApplications);
        request.setAttribute("monthlyRevenue", monthlyRevenue);

        request.getRequestDispatcher("/admin/reports.jsp").forward(request, response);
    }
}
