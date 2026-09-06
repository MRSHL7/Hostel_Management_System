<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Hostel Management System</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="login-container">
        <div class="login-form">
            <h2>Hostel Management System</h2>
            <h3 style="text-align: center; margin-bottom: 30px; color: #666;">Sign In</h3>

            <c:if test="${not empty errorMessage}">
                <div style="margin-bottom:12px;padding:10px;border:1px solid #e99;background:#fee;color:#a00;">
                    ${errorMessage}
                </div>
            </c:if>


            <c:if test="${not empty param.message}">
                <div class="alert alert-info">
                    ${param.message}
                </div>
            </c:if>

            <form action="login" method="post">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary">Sign In</button>
                <br>
                <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign Up</a></p>

            </form>

            <div style="text-align: center; margin-top: 20px;">
                <p style="color: #666; margin-bottom: 10px;">Demo Credentials:</p>
                <p style="font-size: 14px; color: #888;">
                    Admin: admin/admin123<br>
                    Student: student/student123
                </p>
            </div>
        </div>
    </div>

    <script src="js/app.js"></script>
</body>
</html>