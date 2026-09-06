package com.hostel.dao;

import com.hostel.model.User;
import com.hostel.model.User.UserType;
import com.hostel.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User operations
 */
public class UserDAO {

    /**
     * Create a new user
     * @param user User object to create
     * @return true if creation successful, false otherwise
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, password, email, first_name, last_name, phone_number, user_type, is_active, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getPhoneNumber());
            pstmt.setString(7, user.getUserType().name()); // store enum as String
            pstmt.setBoolean(8, user.isActive());
            pstmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }

        return false;
    }

    /**
     * Authenticate user login
     */
    public User authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND is_active = true";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }

        return null;
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
        }

        return null;
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting user by username: " + e.getMessage());
        }

        return null;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, first_name = ?, last_name = ?, phone_number = ?, user_type = ?, is_active = ?, updated_at = ? WHERE user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setString(5, user.getPhoneNumber());
            pstmt.setString(6, user.getUserType().name()); // store enum as String
            pstmt.setBoolean(7, user.isActive());
            pstmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(9, user.getUserId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }

        return false;
    }

    public List<User> getUsersByType(UserType userType) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE user_type = ? AND is_active = true ORDER BY created_at DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userType.name());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting users by type: " + e.getMessage());
        }

        return users;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPhoneNumber(rs.getString("phone_number"));
        
        // Convert string back to enum
        String typeStr = rs.getString("user_type");
        if (typeStr != null) {
            user.setUserType(UserType.valueOf(typeStr.toUpperCase()));
        }

        user.setActive(rs.getBoolean("is_active"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return user;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
        }

        return false;
    }
    public boolean registerUser(User user) {
    String sql = "INSERT INTO users (username, email, password, first_name, last_name, phone_number, user_type, is_active) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getEmail());
        pstmt.setString(3, user.getPassword());  // Consider hashing before this
        pstmt.setString(4, user.getFirstName());
        pstmt.setString(5, user.getLastName());
        pstmt.setString(6, user.getPhoneNumber());
        pstmt.setString(7, user.getUserType().name());
        pstmt.setBoolean(8, user.isActive());

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
