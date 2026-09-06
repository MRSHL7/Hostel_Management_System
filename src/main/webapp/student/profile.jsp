<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <meta charset="UTF-8" />
    <title>Update Profile</title>
    <style>
        /* Simple styling */
        .form-container {
            max-width: 500px;
            margin: 40px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-family: Arial, sans-serif;
        }
        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }
        input[type=text], input[type=email] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 4px;
            border: 1px solid #aaa;
            box-sizing: border-box;
        }
        .btn {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #667eea;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #556cd6;
        }
        .success {
            color: green;
            margin-bottom: 15px;
        }
        .error {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Update Profile</h2>
        <c:if test="${not empty successMessage}">
            <div class="success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/student/profile" method="post">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" value="${user.username}" required />

            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="${user.email}" required />

            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" value="${user.firstName}" required />

            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" value="${user.lastName}" required />

            <label for="phoneNumber">Phone Number</label>
            <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" />

            <button type="submit" class="btn">Update Profile</button>
        </form>
    </div>
</body>
</html>
