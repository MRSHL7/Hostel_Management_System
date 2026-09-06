<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Booking Confirmation</title>
    <style>
        .container {
            max-width: 400px;
            margin: 50px auto;
            font-family: Arial, sans-serif;
            color: #38a169;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Booking Successful</h2>
        <p>${message}</p>
        <a href="${pageContext.request.contextPath}/student/dashboard.jsp">Back to Dashboard</a>
    </div>
</body>
</html>
