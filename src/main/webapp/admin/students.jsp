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
    <meta charset="UTF-8" />
    <title>Student Management - Hostel Management System</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css' />" />
</head>
<body>
    <h1>Student Management</h1>
    
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>
        <br>   
    <nav class="navbar">
        <div class="container">
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>

                <li><a href="${pageContext.request.contextPath}/admin/rooms">Room Management</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/students" class=active">Student Management</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/reports" >Reports</a></li>
                <li><a href="../logout">Logout</a></li>
            </ul>
        </div>
    </nav>
        <br>
        <br>
    <a href="${pageContext.request.contextPath}/admin/students?action=add" class="btn btn-primary">Add New Student</a>

    <table class="table">
        <thead>
            <tr>
                <th>Student Number</th>
                <th>Name</th>
                <th>Course</th>
                <th>Year</th>
                <th>Room Number</th>
                
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty students}">
                    <c:forEach var="student" items="${students}">
                        <tr>
                            <td>${student.studentNumber}</td>
                            <td>${student.firstName} ${student.lastName}</td>
                            <td>${student.course}</td>
                            <td>${student.year}</td>
                            <td>${student.roomNumber != null ? student.roomNumber : 'N/A'}</td>
                            
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/students?action=edit&studentId=${student.studentId}">Edit</a>
                                
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="7" style="text-align:center;">No students found.</td></tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
