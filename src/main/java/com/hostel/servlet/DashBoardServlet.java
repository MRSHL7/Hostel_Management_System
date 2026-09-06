package com.hostel.servlet;

import com.hostel.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("/admin/dashboard")
public class DashBoardServlet extends HttpServlet {

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

        int totalRooms = roomDAO.getTotalRooms();
        int occupiedRooms = roomDAO.getOccupiedRooms();
        int availableRooms = roomDAO.getAvailableRooms();
        int totalStudents = studentDAO.getTotalStudents();
       
        double monthlyRevenueValue = feeDAO.getMonthlyRevenue();

        // Format revenue (optional)
        request.setAttribute("totalRooms", totalRooms);
        request.setAttribute("occupiedRooms", occupiedRooms);
        request.setAttribute("availableRooms", availableRooms);
        request.setAttribute("totalStudents", totalStudents);
       
        request.setAttribute("monthlyRevenue", monthlyRevenueValue);

        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
