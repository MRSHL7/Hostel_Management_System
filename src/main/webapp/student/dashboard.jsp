<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hostel.model.User" %>


<%
    User user = (User) session.getAttribute("user");
    if (user == null || (
        user.getUserType() != User.UserType.STUDENT &&
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
    <title>Student Dashboard - Hostel Management System</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <div class="header">
        <div class="container">
            <h1>Student Dashboard</h1>
            <p>Welcome back, ${sessionScope.user.firstName} ${sessionScope.user.lastName}</p>
        </div>
    </div>

    <nav class="navbar">
        <div class="container">
            <ul>
                <li><a href="dashboard.jsp" class="active">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/student/profile" >Update Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/student/bookRoom" >Room Booking</a></li>
                <li><a href="https://docs.google.com/forms/d/e/1FAIpQLSfCMrZK8dx3mnMYo_odvprX-UgDRKLLPdOWSTEpn3vO_Tf5VQ/viewform?usp=dialog">Complaints</a></li>
                
                <li><a href="../logout">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="main-content">
        <div class="container">
            <h2>Dashboard Overview</h2>

            <div class="dashboard-grid">
                <div class="card">
                    <div class="card-header">
                        My Room Details
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty currentRoom}">
                                <p><strong>Room Number:</strong> ${currentRoom.roomNumber}</p>
                                <p><strong>Floor:</strong> ${currentRoom.floorNumber}</p>
                                <p><strong>Type:</strong> ${currentRoom.roomType}</p>
                                <p><strong>Monthly Fee:</strong> ₹${currentRoom.fee}</p>
                                <p><strong>Facilities:</strong> ${currentRoom.facilities}</p>
                            </c:when>
                            <c:otherwise>
                                <p>No room allocated yet.</p>
                                <a href="${pageContext.request.contextPath}/student/bookRoom" class="btn btn-add">Book a Room</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        Fee Status
                    </div>
                    <div class="card-body">
                        <p><strong>Current Month:</strong> January 2025</p>
                        <p><strong>Amount Due:</strong> ₹8,500</p>
                        <p><strong>Due Date:</strong> 15th January 2025</p>
                        <p><strong>Status:</strong> <span style="color: orange;">Pending</span></p>
                        <a href="${pageContext.request.contextPath}/student/payment-qr.jsp" target="_blank" class="btn btn-success">Pay Now</a>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        Quick Actions
                    </div>
                    <div class="card-body">
                        <div style="display: grid; gap: 10px;">
                            <a href="https://docs.google.com/forms/d/e/1FAIpQLSfCMrZK8dx3mnMYo_odvprX-UgDRKLLPdOWSTEpn3vO_Tf5VQ/viewform?usp=dialog" class="btn btn-secondary">Lodge Complaint</a>
                            <a href="${pageContext.request.contextPath}/student/profile" class="btn btn-secondary">Update Profile</a>

                            <a href=https://docs.google.com/forms/d/e/1FAIpQLSfCMrZK8dx3mnMYo_odvprX-UgDRKLLPdOWSTEpn3vO_Tf5VQ/viewform?usp=dialog" class="btn btn-secondary">Request Maintenance</a>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        Announcements
                    </div>
                    <div class="card-body">
                        <ul style="list-style: none; padding: 0;">
                            <li style="padding: 8px 0; border-bottom: 1px solid #eee;">
                                <strong>Hostel Timing Changes</strong>
                                <small style="color: #666; display: block;">New entry timings: 6 AM - 10 PM</small>
                            </li>
                            <li style="padding: 8px 0; border-bottom: 1px solid #eee;">
                                <strong>WiFi Maintenance</strong>
                                <small style="color: #666; display: block;">Scheduled for this weekend</small>
                            </li>
                            <li style="padding: 8px 0;">
                                <strong>Fee Payment Reminder</strong>
                                <small style="color: #666; display: block;">Due date approaching</small>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="card" style="margin-top: 30px;">
                <div class="card-header">
                    Recent Activities
                </div>
                <div class="card-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Activity</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>2025-01-10</td>
                                <td>Room booking request submitted</td>
                                <td><span class="status-available">Approved</span></td>
                            </tr>
                            <tr>
                                <td>2025-01-08</td>
                                <td>Fee payment for December 2024</td>
                                <td><span class="status-available">Completed</span></td>
                            </tr>
                            <tr>
                                <td>2025-01-05</td>
                                <td>Maintenance request for AC</td>
                                <td><span class="status-maintenance">In Progress</span></td>
                            </tr>
                            <tr>
                                <td>2025-01-03</td>
                                <td>Profile updated</td>
                                <td><span class="status-available">Completed</span></td>
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