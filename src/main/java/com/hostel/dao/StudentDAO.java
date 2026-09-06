package com.hostel.dao;

import com.hostel.model.Student;
import com.hostel.model.Room;
import com.hostel.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, u.first_name, u.last_name, r.room_number FROM students s " +
                     "JOIN users u ON s.user_id = u.user_id " +
                     "LEFT JOIN rooms r ON s.room_id = r.room_id ORDER BY s.student_number";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = mapResultSetToStudent(rs);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
    
    public Student getStudentById(int studentId) {
        String sql = "SELECT s.*, u.first_name, u.last_name, r.room_number FROM students s " +
                     "JOIN users u ON s.user_id = u.user_id " +
                     "LEFT JOIN rooms r ON s.room_id = r.room_id WHERE s.student_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStudent(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (user_id, student_number, course, year, department, guardian_name, guardian_phone, " +
                     "address, city, state, pincode, date_of_birth, gender, blood_group, room_id, check_in_date, check_out_date, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, student.getUserId());
            pstmt.setString(2, student.getStudentNumber());
            pstmt.setString(3, student.getCourse());
            pstmt.setInt(4, student.getYear());
            pstmt.setString(5, student.getDepartment());
            pstmt.setString(6, student.getGuardianName());
            pstmt.setString(7, student.getGuardianPhone());
            pstmt.setString(8, student.getAddress());
            pstmt.setString(9, student.getCity());
            pstmt.setString(10, student.getState());
            pstmt.setString(11, student.getPincode());

            if (student.getDateOfBirth() != null) {
                pstmt.setDate(12, Date.valueOf(student.getDateOfBirth()));
            } else {
                pstmt.setNull(12, Types.DATE);
            }

            pstmt.setString(13, student.getGender() != null ? student.getGender().name() : null);
            pstmt.setString(14, student.getBloodGroup());

            if (student.getRoomId() != 0) {
                pstmt.setInt(15, student.getRoomId());
            } else {
                pstmt.setNull(15, Types.INTEGER);
            }

            if (student.getCheckInDate() != null) {
                pstmt.setDate(16, Date.valueOf(student.getCheckInDate()));
            } else {
                pstmt.setNull(16, Types.DATE);
            }

            if (student.getCheckOutDate() != null) {
                pstmt.setDate(17, Date.valueOf(student.getCheckOutDate()));
            } else {
                pstmt.setNull(17, Types.DATE);
            }

            pstmt.setString(18, student.getStatus() != null ? student.getStatus().name() : null);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET user_id=?, student_number=?, course=?, year=?, department=?, guardian_name=?, guardian_phone=?, " +
                     "address=?, city=?, state=?, pincode=?, date_of_birth=?, gender=?, blood_group=?, room_id=?, " +
                     "check_in_date=?, check_out_date=?, status=? WHERE student_id=?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, student.getUserId());
            pstmt.setString(2, student.getStudentNumber());
            pstmt.setString(3, student.getCourse());
            pstmt.setInt(4, student.getYear());
            pstmt.setString(5, student.getDepartment());
            pstmt.setString(6, student.getGuardianName());
            pstmt.setString(7, student.getGuardianPhone());
            pstmt.setString(8, student.getAddress());
            pstmt.setString(9, student.getCity());
            pstmt.setString(10, student.getState());
            pstmt.setString(11, student.getPincode());

            if (student.getDateOfBirth() != null) {
                pstmt.setDate(12, Date.valueOf(student.getDateOfBirth()));
            } else {
                pstmt.setNull(12, Types.DATE);
            }

            pstmt.setString(13, student.getGender() != null ? student.getGender().name() : null);
            pstmt.setString(14, student.getBloodGroup());

            if (student.getRoomId() != 0) {
                pstmt.setInt(15, student.getRoomId());
            } else {
                pstmt.setNull(15, Types.INTEGER);
            }

            if (student.getCheckInDate() != null) {
                pstmt.setDate(16, Date.valueOf(student.getCheckInDate()));
            } else {
                pstmt.setNull(16, Types.DATE);
            }

            if (student.getCheckOutDate() != null) {
                pstmt.setDate(17, Date.valueOf(student.getCheckOutDate()));
            } else {
                pstmt.setNull(17, Types.DATE);
            }

            pstmt.setString(18, student.getStatus() != null ? student.getStatus().name() : null);
            pstmt.setInt(19, student.getStudentId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    

    public int getTotalStudents() {
        String sql = "SELECT COUNT(*) FROM students";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getPendingApplications() {
        String sql = "SELECT COUNT(*) FROM students WHERE status = 'PENDING'";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
  
    public Student getStudentByUserId(int userId) {
        String sql = "SELECT s.*, u.first_name, u.last_name, r.room_number FROM students s " +
                     "JOIN users u ON s.user_id = u.user_id " +
                     "LEFT JOIN rooms r ON s.room_id = r.room_id WHERE s.user_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStudent(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
      Student student = new Student();
      student.setStudentId(rs.getInt("student_id"));
      student.setUserId(rs.getInt("user_id"));
      student.setStudentNumber(rs.getString("student_number"));
      student.setCourse(rs.getString("course"));
      student.setYear(rs.getInt("year"));
      student.setDepartment(rs.getString("department"));
      student.setFirstName(rs.getString("first_name"));
      student.setLastName(rs.getString("last_name"));
      student.setRoomId(rs.getInt("room_id"));               // <--- ADD THIS LINE
      student.setRoomNumber(rs.getString("room_number"));
      // set other needed fields...
      return student;
  }

}
