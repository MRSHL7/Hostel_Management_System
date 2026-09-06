<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hostel.model.Room" %>
<%
    Room room = (Room) request.getAttribute("room");
    if (room == null) {
        response.sendRedirect(request.getContextPath() + "/admin/rooms");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>View Room - ${room.roomNumber}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>

<h1>Room Details - ${room.roomNumber}</h1>

<div class="room-details">

    <table class="table" style="width: 50%; margin-bottom: 20px;">
        <tr>
            <th>Room Number</th>
            <td>${room.roomNumber}</td>
        </tr>
        <tr>
            <th>Floor Number</th>
            <td>${room.floorNumber}</td>
        </tr>
        <tr>
            <th>Room Type</th>
            <td>${room.roomType}</td>
        </tr>
        <tr>
            <th>Capacity</th>
            <td>${room.capacity}</td>
        </tr>
        <tr>
            <th>Occupied</th>
            <td>${room.occupied}</td>
        </tr>
        <tr>
            <th>Fee (₹)</th>
            <td>${room.fee}</td>
        </tr>
        <tr>
            <th>Status</th>
            <td>${room.status}</td>
        </tr>
        <tr>
            <th>Facilities</th>
            <td><c:out value="${room.facilities}" default="N/A"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${room.description}" default="N/A"/></td>
        </tr>
    </table>

    <a href="${pageContext.request.contextPath}/admin/rooms" class="btn btn-secondary">Back to Rooms List</a>
    <a href="${pageContext.request.contextPath}/admin/rooms?action=edit&id=${room.roomId}" class="btn btn-primary">Edit Room</a>

</div>

</body>
</html>
