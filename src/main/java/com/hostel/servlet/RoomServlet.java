package com.hostel.servlet;

import com.hostel.dao.RoomDAO;
import com.hostel.model.Room;
import com.hostel.model.Room.RoomType;
import com.hostel.model.Room.RoomStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/rooms")
public class RoomServlet extends HttpServlet {
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        roomDAO = new RoomDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "list":
                listRooms(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "view":
                viewRoom(request, response);
                break;
            default:
                listRooms(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/rooms");
            return;
        }
        switch (action) {
            case "add":
                addRoom(request, response);
                break;
            case "update":
                updateRoom(request, response);
                break;
            case "delete":
                deleteRoom(request, response);
            default:
                response.sendRedirect(request.getContextPath() + "/admin/rooms");
        }
    }

   private void listRooms(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        List<Room> rooms = roomDAO.getAllRooms();
        request.setAttribute("rooms", rooms);

        // Fetch dashboard stats from DAO
        int totalRooms = roomDAO.getTotalRooms();
        int occupiedRooms = roomDAO.getOccupiedRooms();
        int availableRooms = roomDAO.getAvailableRooms();
        int maintenanceRooms = roomDAO.getMaintenanceRooms();

        // Set stats as request attributes for JSP
        request.setAttribute("totalRooms", totalRooms);
        request.setAttribute("occupiedRooms", occupiedRooms);
        request.setAttribute("availableRooms", availableRooms);
        request.setAttribute("maintenanceRooms", maintenanceRooms);

        request.getRequestDispatcher("/admin/rooms.jsp").forward(request, response);
    } catch (Exception e) {
        request.setAttribute("error", "Error loading rooms: " + e.getMessage());
        request.getRequestDispatcher("/admin/rooms.jsp").forward(request, response);
    }
}


    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("id"));
            Room room = roomDAO.getRoomById(roomId);
            if (room != null) {
                request.setAttribute("room", room);
                request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/rooms");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/rooms");
        }
    }

    private void viewRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("id"));
            Room room = roomDAO.getRoomById(roomId);
            if (room != null) {
                request.setAttribute("room", room);
                request.getRequestDispatcher("/admin/room-view.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/rooms");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/rooms");
        }
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String roomNumber = request.getParameter("roomNumber");
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            String roomTypeStr = request.getParameter("roomType");
            RoomType roomType = RoomType.valueOf(roomTypeStr.toUpperCase());
            double fee = Double.parseDouble(request.getParameter("fee"));
            int floorNumber = Integer.parseInt(request.getParameter("floorNumber"));
            String description = request.getParameter("description");
            String facilities = request.getParameter("facilities");

            Room room = new Room(roomNumber, capacity, roomType, fee, floorNumber);
            room.setDescription(description);
            room.setFacilities(facilities);
            if (roomDAO.createRoom(room)) {
                response.sendRedirect(request.getContextPath() + "/admin/rooms");
            } else {
                request.setAttribute("error", "Failed to add room");
                request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error adding room: " + e.getMessage());
            request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
        }
    }
    private void deleteRoom(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/rooms?error=NoRoomId");
            return;
        }

        int roomId;
        try {
            roomId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/rooms?error=InvalidRoomId");
            return;
        }

        boolean deleted = roomDAO.deleteRoom(roomId);
        if (deleted) {
            // Deletion successful, redirect with success message
            response.sendRedirect(request.getContextPath() + "/admin/rooms?message=RoomDeleted");
        } else {
            // Could not delete (e.g., foreign key constraint), show error in list page
            request.setAttribute("error", "Failed to delete room. It might be assigned or locked.");
            listRooms(request, response);
        }
}


    private void updateRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String roomNumber = request.getParameter("roomNumber");
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            String roomTypeStr = request.getParameter("roomType");
            RoomType roomType = RoomType.valueOf(roomTypeStr.toUpperCase());
            double fee = Double.parseDouble(request.getParameter("fee"));
            int floorNumber = Integer.parseInt(request.getParameter("floorNumber"));
            String description = request.getParameter("description");
            String facilities = request.getParameter("facilities");
            String statusStr = request.getParameter("status");
            RoomStatus status = RoomStatus.valueOf(statusStr.toUpperCase());

            Room room = roomDAO.getRoomById(roomId);
            if (room != null) {
                room.setRoomNumber(roomNumber);
                room.setCapacity(capacity);
                room.setRoomType(roomType);
                room.setFee(fee);
                room.setFloorNumber(floorNumber);
                room.setDescription(description);
                room.setFacilities(facilities);
                room.setStatus(status);
                if (roomDAO.updateRoom(room)) {
                    response.sendRedirect(request.getContextPath() + "/admin/rooms");
                } else {
                    request.setAttribute("error", "Failed to update room");
                    request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/rooms");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid enum value for RoomType or RoomStatus");
            request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error updating room: " + e.getMessage());
            request.getRequestDispatcher("/admin/room-form.jsp").forward(request, response);
        }
    }
}
