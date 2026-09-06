<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Payment QR Code</title>
    <style>
        .container {
            max-width: 400px;
            margin: 50px auto;
            font-family: Arial, sans-serif;
            text-align: center;
        }
        img {
            margin-top: 20px;
            height: 250px;
            width: 250px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Scan to Pay</h2>
    <img src="${pageContext.request.contextPath}/qrcode?data=https%3A%2F%2Fyour.payment.url" alt="QR Code" />
    <p>Use your phone to scan this QR code and complete the payment.</p>
    <a href="${pageContext.request.contextPath}/student/dashboard">Back to Dashboard</a>
</div>
</body>
</html>
