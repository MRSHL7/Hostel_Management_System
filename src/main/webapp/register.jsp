<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
    
<head>
    <meta charset="UTF-8" />
    <title>Sign Up - Hostel Management System</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f4f7f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .register-container {
            background: #fff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 6px 12px rgba(0,0,0,0.1);
            width: 400px;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        label {
            display: block;
            margin-top: 15px;
            font-weight: 600;
            color: #555;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 6px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #667eea;
            outline: none;
        }
        .btn {
            background-color: #667eea;
            color: white;
            border: none;
            width: 100%;
            padding: 12px;
            margin-top: 25px;
            font-size: 18px;
            cursor: pointer;
            border-radius: 6px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #5a67d8;
        }
        .alert-error {
            background-color: #f56565;
            color: white;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
            text-align: center;
            font-weight: bold;
        }
        .login-link {
            margin-top: 20px;
            text-align: center;
        }
        .login-link a {
            color: #667eea;
            font-weight: 600;
            text-decoration: none;
        }
        .login-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <h1>Student Sign Up</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert-error">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required />

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required />

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required />

            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" required />

            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" required />
            
            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" />

            <button type="submit" class="btn">Sign Up</button>
        </form>

        <div class="login-link">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
        </div>
    </div>
</body>
</html>
