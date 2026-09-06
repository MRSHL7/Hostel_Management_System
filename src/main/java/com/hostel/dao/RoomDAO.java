package com.hostel.dao;

import com.hostel.model.Room;
import com.hostel.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // Returns a list of all Room objects
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY floor_number, room_number";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Returns the total number of rooms
    public int getTotalRooms() {
        String sql = "SELECT COUNT(*) FROM rooms";
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

    // Returns the sum of occupied slots for all rooms
   public int getOccupiedRooms() {
    String sql = "SELECT COUNT(*) FROM rooms WHERE occupied > 0";
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


    // Returns the count of rooms with available slots
    public int getAvailableRooms() {
        String sql = "SELECT COUNT(*) FROM rooms WHERE status = 'AVAILABLE' AND occupied < capacity";
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

    // Returns the count of rooms under maintenance
    public int getMaintenanceRooms() {
        String sql = "SELECT COUNT(*) FROM rooms WHERE status = 'MAINTENANCE'";
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

    // Utility function to map a database row to a Room object
    private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setRoomNumber(rs.getString("room_number"));
        room.setCapacity(rs.getInt("capacity"));
        room.setOccupied(rs.getInt("occupied"));

        // Convert String to enum for RoomType
        String roomTypeStr = rs.getString("room_type");
        if (roomTypeStr != null) {
            room.setRoomType(Room.RoomType.valueOf(roomTypeStr));
        } else {
            room.setRoomType(null);
        }

        room.setFee(rs.getDouble("fee"));

        // Convert String to enum for RoomStatus
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            room.setStatus(Room.RoomStatus.valueOf(statusStr));
        } else {
            room.setStatus(null);
        }

        room.setDescription(rs.getString("description"));
        room.setFacilities(rs.getString("facilities"));
        room.setFloorNumber(rs.getInt("floor_number"));

        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) room.setCreatedAt(created.toLocalDateTime());
        Timestamp updated = rs.getTimestamp("updated_at");
        if (updated != null) room.setUpdatedAt(updated.toLocalDateTime());

        return room;
    }

    public Room getRoomById(int roomId) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoom(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, capacity, occupied, room_type, fee, status, description, facilities, floor_number, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, room.getRoomNumber());
            pstmt.setInt(2, room.getCapacity());
            pstmt.setInt(3, room.getOccupied()); // Usually 0 for new room
            pstmt.setString(4, room.getRoomType() != null ? room.getRoomType().name() : null);
            pstmt.setDouble(5, room.getFee());
            pstmt.setString(6, room.getStatus() != null ? room.getStatus().name() : null);
            pstmt.setString(7, room.getDescription());
            pstmt.setString(8, room.getFacilities());
            pstmt.setInt(9, room.getFloorNumber());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean decrementOccupiedCount(int roomId) {
        String sql = "UPDATE rooms SET occupied = occupied - 1, " +
                     "status = CASE WHEN occupied - 1 < capacity THEN 'AVAILABLE' ELSE status END " +
                     "WHERE room_id = ? AND occupied > 0";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET room_number = ?, capacity = ?, occupied = ?, room_type = ?, fee = ?, status = ?, description = ?, facilities = ?, floor_number = ?, updated_at = CURRENT_TIMESTAMP WHERE room_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, room.getRoomNumber());
            pstmt.setInt(2, room.getCapacity());
            pstmt.setInt(3, room.getOccupied());
            pstmt.setString(4, room.getRoomType() != null ? room.getRoomType().name() : null);
            pstmt.setDouble(5, room.getFee());
            pstmt.setString(6, room.getStatus() != null ? room.getStatus().name() : null);
            pstmt.setString(7, room.getDescription());
            pstmt.setString(8, room.getFacilities());
            pstmt.setInt(9, room.getFloorNumber());
            pstmt.setInt(10, room.getRoomId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Room> getAvailableRoomsList() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE occupied < capacity AND status <> 'MAINTENANCE' ORDER BY floor_number, room_number";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    
    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean incrementOccupiedCount(int roomId) {
        String incrementSql = "UPDATE rooms SET occupied = occupied + 1 WHERE room_id = ?";
        String updateStatusSql = "UPDATE rooms SET status = 'OCCUPIED' WHERE room_id = ? AND occupied >= capacity";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtInc = conn.prepareStatement(incrementSql);
                 PreparedStatement pstmtStatus = conn.prepareStatement(updateStatusSql)) {

                pstmtInc.setInt(1, roomId);
                int updatedRows = pstmtInc.executeUpdate();

                pstmtStatus.setInt(1, roomId);
                pstmtStatus.executeUpdate();

                conn.commit();
                return updatedRows > 0;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
