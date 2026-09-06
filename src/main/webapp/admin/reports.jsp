<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Reports - Hostel Management System</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css' />" />
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
            margin: 0; padding: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .header, nav, .main-container {
            max-width: 1000px;
            margin: auto;
        }
        nav ul {
            display: flex;
            gap: 20px;
            list-style: none;
            padding-left: 0;
            justify-content: center;
            margin-bottom: 30px;
        }
        nav ul li a {
            text-decoration: none;
            padding: 10px 15px;
            color: #555;
            border-radius: 5px;
        }
        nav ul li a.active, nav ul li a:hover {
            background-color: #667eea;
            color: white;
        }
        .reports-grid {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 25px;
        }
        .report-card {
            background: white;
            border-radius: 8px;
            padding: 30px 20px;
            width: 220px;
            box-shadow: 0 3px 12px rgba(102,126,234,0.3);
            text-align: center;
        }
        .report-number {
            font-size: 3em;
            color: #667eea;
            font-weight: 700;
            margin-bottom: 10px;
        }
        .report-label {
            font-size: 1.15em;
            color: #444;
        }
    </style>
</head>
<body>

<div class="header">
    <h1>Reports</h1>
</div>

<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/rooms">Room Management</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/students">Student Management</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/reports" class="active">Reports</a></li>
    </ul>
</nav>

<div class="main-container">
    <div class="reports-grid">
        <div class="report-card">
            <div class="report-number">${totalRooms != null ? totalRooms : 0}</div>
            <div class="report-label">Total Rooms</div>
        </div>
        <div class="report-card">
            <div class="report-number">${occupiedRooms != null ? occupiedRooms : 0}</div>
            <div class="report-label">Occupied Rooms</div>
        </div>
        <div class="report-card">
            <div class="report-number">${availableRooms != null ? availableRooms : 0}</div>
            <div class="report-label">Available Rooms</div>
        </div>
        <div class="report-card">
            <div class="report-number">${totalStudents != null ? totalStudents : 0}</div>
            <div class="report-label">Total Students</div>
        </div>
        <div class="report-card">
            <div class="report-number">${pendingApplications != null ? pendingApplications : 0}</div>
            <div class="report-label">Pending Applications</div>
        </div>
        <div class="report-card">
            <div class="report-number">2,50,000</div>
            <div class="report-label">Monthly Revenue</div>
        </div>
    </div>
</div>

</body>
</html>
