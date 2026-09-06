<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Book a Room</title>
    <style>
        .error { color: red; }
        .container {
            max-width: 500px;
            margin: 50px auto;
            font-family: Arial, sans-serif;
        }
        label, input, select, textarea, button {
            display: block;
            width: 100%;
            margin-top: 10px;
            font-size: 14px;
        }
        textarea {
            height: 80px;
        }
        button {
            margin-top: 20px;
            padding: 10px;
            background-color: #667eea;
            border: none;
            color: white;
            cursor: pointer;
            border-radius: 4px;
            font-size: 16px;
        }
        button:hover {
            background-color: #556cd6;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Book a Room</h2>

    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/student/bookRoom" method="post">

        <!-- userId from session, hidden -->
        <input type="hidden" name="userId" value="${sessionScope.user.userId}" />

        <label for="firstName">First Name *</label>
        <input type="text" id="firstName" name="firstName" required />

        <label for="lastName">Last Name *</label>
        <input type="text" id="lastName" name="lastName" required />

        <label for="studentNumber">Student Number *</label>
        <input type="text" id="studentNumber" name="studentNumber" required />

        <label for="course">Course *</label>
        <input type="text" id="course" name="course" required />

        <label for="year">Year *</label>
        <input type="number" id="year" name="year" min="1" max="10" required />

        <label for="department">Department *</label>
        <input type="text" id="department" name="department" required />

        <label for="guardianName">Guardian Name</label>
        <input type="text" id="guardianName" name="guardianName" />

        <label for="guardianPhone">Guardian Phone</label>
        <input type="text" id="guardianPhone" name="guardianPhone" />

        <label for="address">Address</label>
        <textarea id="address" name="address"></textarea>

        <label for="city">City</label>
        <input type="text" id="city" name="city" />

        <label for="state">State</label>
        <input type="text" id="state" name="state" />

        <label for="pincode">Pincode</label>
        <input type="text" id="pincode" name="pincode" />

        <label for="dateOfBirth">Date of Birth</label>
        <input type="date" id="dateOfBirth" name="dateOfBirth" />

        <label for="gender">Gender</label>
        <select id="gender" name="gender">
            <option value="">Select Gender</option>
            <option value="MALE">Male</option>
            <option value="FEMALE">Female</option>
            <option value="OTHER">Other</option>
        </select>

        <label for="bloodGroup">Blood Group</label>
        <input type="text" id="bloodGroup" name="bloodGroup" />

        <label for="roomId">Select Room *</label>
        <select name="roomId" id="roomId" required>
            <option value="">-- Select a Room --</option>
            <c:forEach var="room" items="${availableRooms}">
                <option value="${room.roomId}">
                    Room No: ${room.roomNumber} - Capacity: ${room.capacity} - Occupied: ${room.occupied}
                </option>
            </c:forEach>
        </select>

        <label for="checkInDate">Check-In Date</label>
        <input type="date" id="checkInDate" name="checkInDate" />

        <label for="checkOutDate">Check-Out Date</label>
        <input type="date" id="checkOutDate" name="checkOutDate" />

        <label for="status">Status</label>
        <select id="status" name="status">
            <option value="ACTIVE" selected>Active</option>
            <option value="INACTIVE">Inactive</option>
            <option value="GRADUATED">Graduated</option>
            <option value="SUSPENDED">Suspended</option>
        </select>

        <button type="submit">Book Room</button>
    </form>
</div>
</body>
</html>
