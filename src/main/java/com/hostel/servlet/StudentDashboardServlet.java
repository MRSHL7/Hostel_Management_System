package com.hostel.servlet;

import com.hostel.dao.StudentDAO;
import com.hostel.dao.RoomDAO;
import com.hostel.model.Room;
import com.hostel.model.Student;
import com.hostel.model.User;
import com.hostel.model.User.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private StudentDAO studentDAO;
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
        roomDAO = new RoomDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || user.getUserType() != UserType.STUDENT) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Student student = studentDAO.getStudentByUserId(user.getUserId());
        request.setAttribute("student", student);

        if (student != null && student.getRoomId() != 0) {
            Room currentRoom = roomDAO.getRoomById(student.getRoomId());
            request.setAttribute("currentRoom", currentRoom);
        }

        request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);
}

}
