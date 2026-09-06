<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hostel.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || (
        user.getUserType() != User.UserType.ADMIN &&
        user.getUserType() != User.UserType.WARDEN   // keep if WARDEN should access admin area
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
    <title>Admin Dashboard - Hostel Management System</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <div class="header">
        <div class="container">
            <h1>Admin Dashboard</h1>
            <p>Welcome back, ${sessionScope.user.firstName} ${sessionScope.user.lastName}</p>
        </div>
    </div>

    <nav class="navbar">
        <div class="container">
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="active">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/rooms">Room Management</a></li>
                
                <a href="${pageContext.request.contextPath}/admin/students">Student Management</a>
                <li><a href="${pageContext.request.contextPath}/admin/reports">Reports</a></li>    
                
                
                <li><a href="../logout">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="main-content">
        <div class="container">
          <h2>Dashboard Overview</h2>

            <div class="dashboard-grid">
                <div class="stat-card">
                    <div class="stat-number">${totalRooms}</div>
                    <div class="stat-label">Total Rooms</div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">${occupiedRooms}</div>
                    <div class="stat-label">Occupied Rooms</div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">${availableRooms}</div>
                    <div class="stat-label">Available Rooms</div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">${totalStudents}</div>
                    <div class="stat-label">Total Students</div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">12</div>
                    <div class="stat-label">Pending Applications</div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">125000</div>
                    <div class="stat-label">Monthly Revenue</div>
                </div>
            </div>


            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-top: 30px;">
                <div class="card">
                    <div class="card-header">
                        Recent Activities
                    </div>
                    <div class="card-body">
                        <ul style="list-style: none; padding: 0;">
                            <li style="padding: 10px 0; border-bottom: 1px solid #eee;">
                                <strong>John Doe</strong> checked into Room 101
                                <small style="color: #666; display: block;">2 hours ago</small>
                            </li>
                            <li style="padding: 10px 0; border-bottom: 1px solid #eee;">
                                <strong>Room 205</strong> maintenance request submitted
                                <small style="color: #666; display: block;">4 hours ago</small>
                            </li>
                            <li style="padding: 10px 0; border-bottom: 1px solid #eee;">
                                <strong>Jane Smith</strong> fee payment received
                                <small style="color: #666; display: block;">6 hours ago</small>
                            </li>
                            <li style="padding: 10px 0;">
                                <strong>Room 102</strong> became available
                                <small style="color: #666; display: block;">1 day ago</small>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        Quick Actions
                    </div>
                    <div class="card-body">
                        <div style="display: grid; gap: 15px;">
                            <a href="rooms?action=add" class="btn btn-primary">Add New Room</a>
                            <a href="student-form.jsp" class="btn btn-success">Register Student</a>
                            <a  href="${pageContext.request.contextPath}/admin/reports"  class="btn btn-secondary">View Report</a>
                            <a href="maintenance.jsp" class="btn btn-secondary">View Maintenance</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card" style="margin-top: 30px;">
                <div class="card-header">
                    Room Occupancy Status
                </div>
                <div class="card-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Floor</th>
                                <th>Total Rooms</th>
                                <th>Occupied</th>
                                <th>Available</th>
                                <th>Maintenance</th>
                                <th>Occupancy Rate</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1st Floor</td>
                                <td>10</td>
                                <td>8</td>
                                <td>2</td>
                                <td>0</td>
                                <td>80%</td>
                            </tr>
                            <tr>
                                <td>2nd Floor</td>
                                <td>12</td>
                                <td>11</td>
                                <td>0</td>
                                <td>1</td>
                                <td>92%</td>
                            </tr>
                            <tr>
                                <td>3rd Floor</td>
                                <td>12</td>
                                <td>10</td>
                                <td>2</td>
                                <td>0</td>
                                <td>83%</td>
                            </tr>
                            <tr>
                                <td>4th Floor</td>
                                <td>8</td>
                                <td>7</td>
                                <td>1</td>
                                <td>0</td>
                                <td>88%</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        <div class="container">
            <p>&copy; 2025 Hostel Management System. All rights reserved.</p>
        </div>
    </div>

    <script src="../js/app.js"></script>
</body>
</html>