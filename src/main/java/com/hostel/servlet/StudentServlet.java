package com.hostel.servlet;

import com.hostel.dao.StudentDAO;
import com.hostel.dao.RoomDAO;
import com.hostel.model.Student;
import com.hostel.model.Student.Gender;
import com.hostel.model.Student.StudentStatus;
import com.hostel.model.User;
import com.hostel.model.User.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/admin/students")
public class StudentServlet extends HttpServlet {

    private StudentDAO studentDAO;
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
        roomDAO = new RoomDAO();
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
        String action = request.getParameter("action");
        if ("add".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/admin/student-form.jsp").forward(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            Student student = studentDAO.getStudentById(studentId);
            if (student != null) {
                request.setAttribute("student", student);
                request.getRequestDispatcher("/admin/student-form.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/students");
            }
        } else if ("view".equalsIgnoreCase(action)) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            Student student = studentDAO.getStudentById(studentId);
            if (student != null) {
                request.setAttribute("student", student);
                request.getRequestDispatcher("/admin/student-view.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/students");
            }
        } else {
            // List action as default
            List<Student> students = studentDAO.getAllStudents();
            request.setAttribute("students", students);
            request.getRequestDispatcher("/admin/students.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null || user.getUserType() != UserType.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/students");
            return;
        }
        switch (action) {
            case "add":
                addStudent(request, response);
                break;
            case "update":
                updateStudent(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/students");
                break;
        }
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Student student = new Student();
            populateStudentFromRequest(student, request);

            boolean success = studentDAO.addStudent(student);

            if (success && student.getRoomId() != 0) {
                roomDAO.incrementOccupiedCount(student.getRoomId());
            }

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/students?success=Student added successfully");
            } else {
                request.setAttribute("error", "Failed to add student");
                request.getRequestDispatcher("/admin/student-form.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/admin/student-form.jsp").forward(request, response);
        }
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Student student = new Student();
            student.setStudentId(Integer.parseInt(request.getParameter("studentId")));
            populateStudentFromRequest(student, request);

            Student oldStudent = studentDAO.getStudentById(student.getStudentId());

            boolean success = studentDAO.updateStudent(student);

            if (success && oldStudent != null) {
                int oldRoomId = oldStudent.getRoomId();
                int newRoomId = student.getRoomId();

                if (oldRoomId != newRoomId) {
                    if (oldRoomId != 0) {
                        roomDAO.decrementOccupiedCount(oldRoomId);
                    }
                    if (newRoomId != 0) {
                        roomDAO.incrementOccupiedCount(newRoomId);
                    }
                }
            }

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/students?success=Student updated successfully");
            } else {
                request.setAttribute("error", "Failed to update student");
                request.getRequestDispatcher("/admin/student-form.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/admin/student-form.jsp").forward(request, response);
        }
    }

    // Helper method to populate Student fields from request parameters
    private void populateStudentFromRequest(Student student, HttpServletRequest request) {
        student.setUserId(Integer.parseInt(request.getParameter("userId")));
        student.setStudentNumber(request.getParameter("studentNumber"));
        student.setFirstName(request.getParameter("firstName"));
        student.setLastName(request.getParameter("lastName"));
        student.setCourse(request.getParameter("course"));
        student.setYear(Integer.parseInt(request.getParameter("year")));
        student.setDepartment(request.getParameter("department"));
        student.setGuardianName(request.getParameter("guardianName"));
        student.setGuardianPhone(request.getParameter("guardianPhone"));
        student.setAddress(request.getParameter("address"));
        student.setCity(request.getParameter("city"));
        student.setState(request.getParameter("state"));
        student.setPincode(request.getParameter("pincode"));
        String genderStr = request.getParameter("gender");
        if (genderStr != null && !genderStr.isEmpty()) {
            student.setGender(Gender.valueOf(genderStr.toUpperCase()));
        }
        student.setBloodGroup(request.getParameter("bloodGroup"));
        String roomIdStr = request.getParameter("roomId");
        if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
            student.setRoomId(Integer.parseInt(roomIdStr));
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dobStr = request.getParameter("dateOfBirth");
        if (dobStr != null && !dobStr.trim().isEmpty()) {
            student.setDateOfBirth(LocalDate.parse(dobStr, df));
        }
        String checkInStr = request.getParameter("checkInDate");
        if (checkInStr != null && !checkInStr.trim().isEmpty()) {
            student.setCheckInDate(LocalDate.parse(checkInStr, df));
        }
        String checkOutStr = request.getParameter("checkOutDate");
        if (checkOutStr != null && !checkOutStr.trim().isEmpty()) {
            student.setCheckOutDate(LocalDate.parse(checkOutStr, df));
        }
        String statusStr = request.getParameter("status");
        if (statusStr != null && !statusStr.isEmpty()) {
            student.setStatus(StudentStatus.valueOf(statusStr.toUpperCase()));
        }
    }
}
