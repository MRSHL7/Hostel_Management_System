package com.hostel.model;

import java.time.LocalDateTime;

/**
 * Room model class representing rooms in the hostel
 */
public class Room {

    public enum RoomType {
        SINGLE, DOUBLE, TRIPLE, DORMITORY
    }

    public enum RoomStatus {
        AVAILABLE, OCCUPIED, MAINTENANCE, RESERVED
    }

    private int roomId;
    private String roomNumber;
    private int capacity;
    private int occupied;
    private RoomType roomType;   // Enum instead of String
    private double fee;
    private RoomStatus status;   // Enum instead of String
    private String description;
    private String facilities; // AC, NON_AC, ATTACHED_BATHROOM, etc.
    private int floorNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public Room() {}

    // Constructor with essential fields
    public Room(String roomNumber, int capacity, RoomType roomType, double fee, int floorNumber) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomType = roomType;
        this.fee = fee;
        this.floorNumber = floorNumber;
        this.occupied = 0;
        this.status = RoomStatus.AVAILABLE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getOccupied() { return occupied; }
    public void setOccupied(int occupied) { this.occupied = occupied; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFacilities() { return facilities; }
    public void setFacilities(String facilities) { this.facilities = facilities; }

    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Utility methods
    public int getAvailableSlots() {
        return capacity - occupied;
    }

    public boolean isAvailable() {
        return status == RoomStatus.AVAILABLE && occupied < capacity;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomNumber='" + roomNumber + '\'' +
                ", capacity=" + capacity +
                ", occupied=" + occupied +
                ", roomType=" + roomType +
                ", fee=" + fee +
                ", status=" + status +
                ", floorNumber=" + floorNumber +
                '}';
    }
}
