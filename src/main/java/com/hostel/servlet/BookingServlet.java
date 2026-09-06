package com.hostel.servlet;

import com.hostel.dao.RoomDAO;
import com.hostel.dao.StudentDAO;
import com.hostel.model.Room;
import com.hostel.model.Student;
import com.hostel.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/bookRoom")
public class BookingServlet extends HttpServlet {

    private RoomDAO roomDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        roomDAO = new RoomDAO();
        studentDAO = new StudentDAO();
    }

    // Show booking form with available rooms
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Room> availableRooms = roomDAO.getAvailableRoomsList(); // You need to implement this method to fetch rooms with available slots
        request.setAttribute("availableRooms", availableRooms);
        request.getRequestDispatcher("/student/room-booking.jsp").forward(request, response);
    }

    // Handle booking submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Read mandatory fields
            String userIdStr = request.getParameter("userId");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String studentNumber = request.getParameter("studentNumber");
            String course = request.getParameter("course");
            String yearStr = request.getParameter("year");
            String department = request.getParameter("department");
            String roomIdStr = request.getParameter("roomId");

            // Validate required fields
            if (userIdStr == null || firstName == null || lastName == null || studentNumber == null ||
                course == null || yearStr == null || department == null || roomIdStr == null ||
                userIdStr.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || studentNumber.isEmpty() ||
                course.isEmpty() || yearStr.isEmpty() || department.isEmpty() || roomIdStr.isEmpty()) {
                request.setAttribute("errorMessage", "Please fill all required fields.");
                doGet(request, response);
                return;
            }

            int userId = Integer.parseInt(userIdStr);
            int year = Integer.parseInt(yearStr);
            int roomId = Integer.parseInt(roomIdStr);

            Room room = roomDAO.getRoomById(roomId);
            if (room == null || room.getOccupied() >= room.getCapacity()) {
                request.setAttribute("errorMessage", "Selected room is not available.");
                doGet(request, response);
                return;
            }

            Student student = studentDAO.getStudentByUserId(userId);
            if (student == null) {
                student = new Student();
                student.setUserId(userId);
            }

            // Set all fields from form
            student.setFirstName(firstName.trim());
            student.setLastName(lastName.trim());
            student.setStudentNumber(studentNumber.trim());
            student.setCourse(course.trim());
            student.setYear(year);
            student.setDepartment(department.trim());

            // Optional fields
            student.setGuardianName(request.getParameter("guardianName"));
            student.setGuardianPhone(request.getParameter("guardianPhone"));
            student.setAddress(request.getParameter("address"));
            student.setCity(request.getParameter("city"));
            student.setState(request.getParameter("state"));
            student.setPincode(request.getParameter("pincode"));

            String dobStr = request.getParameter("dateOfBirth");
            if (dobStr != null && !dobStr.isEmpty()) {
                student.setDateOfBirth(LocalDate.parse(dobStr));
            }

            String genderStr = request.getParameter("gender");
            if (genderStr != null && !genderStr.isEmpty()) {
                student.setGender(Student.Gender.valueOf(genderStr));
            }

            student.setBloodGroup(request.getParameter("bloodGroup"));
            student.setRoomId(roomId);

            String checkInStr = request.getParameter("checkInDate");
            if (checkInStr != null && !checkInStr.isEmpty()) {
                student.setCheckInDate(LocalDate.parse(checkInStr));
            }

            String checkOutStr = request.getParameter("checkOutDate");
            if (checkOutStr != null && !checkOutStr.isEmpty()) {
                student.setCheckOutDate(LocalDate.parse(checkOutStr));
            }

            String statusStr = request.getParameter("status");
            if (statusStr != null && !statusStr.isEmpty()) {
                student.setStatus(Student.StudentStatus.valueOf(statusStr));
            } else {
                student.setStatus(Student.StudentStatus.ACTIVE);
            }

        boolean success;
        if (student.getStudentId() == 0) {
            success = studentDAO.addStudent(student);
        } else {
            success = studentDAO.updateStudent(student);
        }

        if (success) {
            roomDAO.incrementOccupiedCount(roomId);

            // Instead of forwarding, redirect to dashboard so it reloads fresh data
            response.sendRedirect(request.getContextPath() + "/student/dashboard");
        } else {
            request.setAttribute("errorMessage", "Booking failed. Please try again.");
            doGet(request, response);
        }


        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unexpected error occurred: " + e.getMessage());
            doGet(request, response);
        }
    }

}