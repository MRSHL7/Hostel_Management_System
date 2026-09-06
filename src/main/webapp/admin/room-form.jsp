<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hostel.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || (
        user.getUserType() != User.UserType.ADMIN &&
        user.getUserType() != User.UserType.WARDEN
    )) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${room != null ? 'Edit Room' : 'Add Room'} - Hostel Management System</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <div class="header">
        <div class="container">
            <h1>${room != null ? 'Edit Room' : 'Add New Room'}</h1>
        </div>
    </div>

    <nav class="navbar">
        <div class="container">
            <ul>
                <li><a href="dashboard.jsp">Dashboard</a></li>
                <li><a href="rooms" class="active">Room Management</a></li>
                <li><a href="students.jsp">Student Management</a></li>
                <li><a href="reports.jsp">Reports</a></li>
                <li><a href="settings.jsp">Settings</a></li>
                <li><a href="../logout">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="main-content">
        <div class="container">
            <div class="form-container">
                <c:if test="${not empty error}">
                    <div class="alert alert-error">
                        ${error}
                    </div>
                </c:if>

                <form action="rooms" method="post">
                    <c:if test="${room != null}">
                        <input type="hidden" name="roomId" value="${room.roomId}">
                        <input type="hidden" name="action" value="update">
                    </c:if>
                    <c:if test="${room == null}">
                        <input type="hidden" name="action" value="add">
                    </c:if>

                    <div class="form-group">
                        <label for="roomNumber">Room Number:</label>
                        <input type="text" id="roomNumber" name="roomNumber" 
                               class="form-control" value="${room.roomNumber}" required>
                    </div>

                    <div class="form-group">
                        <label for="floorNumber">Floor Number:</label>
                        <input type="number" id="floorNumber" name="floorNumber" 
                               class="form-control" value="${room.floorNumber}" min="1" max="10" required>
                    </div>

                    <div class="form-group">
                        <label for="roomType">Room Type:</label>
                        <select id="roomType" name="roomType" class="form-control" required>
                            <option value="">Select Room Type</option>
                            <option value="SINGLE" ${room.roomType == 'SINGLE' ? 'selected' : ''}>Single</option>
                            <option value="DOUBLE" ${room.roomType == 'DOUBLE' ? 'selected' : ''}>Double</option>
                            <option value="TRIPLE" ${room.roomType == 'TRIPLE' ? 'selected' : ''}>Triple</option>
                            <option value="DORMITORY" ${room.roomType == 'DORMITORY' ? 'selected' : ''}>Dormitory</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="capacity">Capacity:</label>
                        <input type="number" id="capacity" name="capacity" 
                               class="form-control" value="${room.capacity}" min="1" max="8" required>
                    </div>

                    <div class="form-group">
                        <label for="fee">Monthly Fee (₹):</label>
                        <input type="number" id="fee" name="fee" 
                               class="form-control" value="${room.fee}" min="0" step="0.01" required>
                    </div>

                    <c:if test="${room != null}">
                        <div class="form-group">
                            <label for="status">Status:</label>
                            <select id="status" name="status" class="form-control" required>
                                <option value="AVAILABLE" ${room.status == 'AVAILABLE' ? 'selected' : ''}>Available</option>
                                <option value="OCCUPIED" ${room.status == 'OCCUPIED' ? 'selected' : ''}>Occupied</option>
                                <option value="MAINTENANCE" ${room.status == 'MAINTENANCE' ? 'selected' : ''}>Under Maintenance</option>
                                <option value="RESERVED" ${room.status == 'RESERVED' ? 'selected' : ''}>Reserved</option>
                            </select>
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label for="facilities">Facilities:</label>
                        <textarea id="facilities" name="facilities" 
                                  class="form-control" rows="3" 
                                  placeholder="e.g., AC, Attached Bathroom, WiFi, Study Table">${room.facilities}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea id="description" name="description" 
                                  class="form-control" rows="3" 
                                  placeholder="Additional room details">${room.description}</textarea>
                    </div>

                    <div style="display: flex; gap: 10px; margin-top: 30px;">
                        <button type="submit" class="btn btn-primary">
                            ${room != null ? 'Update Room' : 'Add Room'}
                        </button>
                        <a href="rooms" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="footer">
        <div class="container">
            <p>&copy; 2025 Hostel Management System. All rights reserved.</p>
        </div>
    </div>

    <script src="../js/app.js"></script>
    <script>
        // Auto-set capacity based on room type
        document.getElementById('roomType').addEventListener('change', function() {
            const capacityInput = document.getElementById('capacity');
            switch(this.value) {
                case 'SINGLE':
                    capacityInput.value = 1;
                    break;
                case 'DOUBLE':
                    capacityInput.value = 2;
                    break;
                case 'TRIPLE':
                    capacityInput.value = 3;
                    break;
                case 'DORMITORY':
                    capacityInput.value = 4;
                    break;
            }
        });
    </script>
</body>
</html>