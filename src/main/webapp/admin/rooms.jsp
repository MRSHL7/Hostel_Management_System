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
%><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Room Management - Hostel Management System</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css' />" />
    <style>
        /* Basic table styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px 15px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #667eea;
            color: white;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f1f4ff;
        }
        .btn {
            padding: 7px 15px;
            border-radius: 5px;
            text-decoration: none;
            color: white;
            font-weight: bold;
            font-size: 14px;
        }
        .btn-primary {
            background-color: #667eea;
        }
        .btn-primary:hover {
            background-color: #556cd6;
        }
        .btn-danger {
            background-color: #e53e3e;
        }
        .btn-danger:hover {
            background-color: #c53030;
        }
        .top-bar {
            margin-top: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .top-bar h1 {
            margin: 0;
            color: #333;
        }
        .btn-add {
            background-color: #38a169;
            padding: 8px 20px;
            font-size: 16px;
        }
        .btn-add:hover {
            background-color: #2f855a;
        }
    </style>
</head>
<body>
       <nav class="navbar">
        <div class="container">
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/dashboard" >Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/rooms" class="active">Room Management</a></li>
                
                <a href="${pageContext.request.contextPath}/admin/students">Student Management</a>
                <li><a href="${pageContext.request.contextPath}/admin/reports">Reports</a></li>    
                
                
                <li><a href="../logout">Logout</a></li>
            </ul>
        </div>
    </nav>
    
    <div class="top-bar"> 
       <h1>Room Management</h1>
        <a href="${pageContext.request.contextPath}/admin/rooms?action=add" class="btn btn-add">Add New Room</a>
    </div>
    
    <table>
        <thead>
            <tr>
                <th>Room Number</th>
                <th>Floor</th>
                <th>Type</th>
                <th>Status</th>
                <th>Capacity</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty rooms}">
                    <c:forEach var="room" items="${rooms}">
                        <tr>
                            <td>${room.roomNumber}</td>
                            <td>${room.floorNumber}</td>
                            <td>${room.roomType}</td>
                            <td>${room.status}</td>
                            <td>${room.capacity}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/rooms?action=edit&id=${room.roomId}" class="btn btn-primary">Edit</a>
                                
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6" style="text-align:center; color:#888;">No rooms found.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
