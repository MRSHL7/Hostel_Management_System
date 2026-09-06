# Hostel Management System

A comprehensive web-based hostel management system built with Java EE technologies including Servlets, JSP, and MySQL database.

## Technologies Used

- **Java**: JDK 21
- **Web Framework**: Java EE (Servlets, JSP)
- **Application Server**: GlassFish 8.x
- **Database**: MySQL 8.0+
- **Build Tool**: Maven
- **Frontend**: HTML5, CSS3, JavaScript
- **Template Engine**: JSP with JSTL

## Features

### Admin Features
- Dashboard with comprehensive statistics
- Room management (Add, Edit, View rooms)
- Student management and registration
- Fee collection and tracking
- Maintenance request management
- Complaint handling
- Report generation

### Student Features
- Personal dashboard
- Room booking and management
- Fee payment tracking
- Complaint submission
- Maintenance request submission
- Profile management

### General Features
- User authentication and authorization
- Role-based access control
- Responsive web design
- Real-time data updates
- Comprehensive reporting
- Search and filter functionality

## Project Structure

```
hostel-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hostel/
│   │   │           ├── dao/           # Data Access Objects
│   │   │           ├── model/         # Model/Entity classes
│   │   │           ├── servlet/       # Servlet controllers
│   │   │           └── util/          # Utility classes
│   │   ├── resources/
│   │   │   └── schema.sql            # Database schema
│   │   └── webapp/
│   │       ├── admin/                # Admin JSP pages
│   │       ├── student/              # Student JSP pages
│   │       ├── css/                  # Stylesheets
│   │       ├── js/                   # JavaScript files
│   │       ├── WEB-INF/
│   │       │   └── web.xml           # Deployment descriptor
│   │       ├── index.jsp             # Landing page
│   │       └── login.jsp             # Login page
├── pom.xml                           # Maven configuration
└── README.md                         # This file
```

## Setup Instructions

### Prerequisites
- JDK 21 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- GlassFish 8.x application server

### Database Setup
1. Install and start MySQL server
2. Create a database named `hostel_management`
3. Execute the SQL script in `src/main/resources/schema.sql`
4. Update database credentials in `DatabaseUtil.java` if needed

### Application Deployment
1. Clone or extract the project
2. Navigate to the project directory
3. Build the project:
   ```bash
   mvn clean package
   ```
4. Deploy the generated WAR file to GlassFish:
   - Copy `target/hostel-management-system.war` to GlassFish deployments directory
   - Or deploy through GlassFish Admin Console

### Configuration
- Update database connection settings in `src/main/java/com/hostel/util/DatabaseUtil.java`
- Modify application settings in `web.xml` if needed

## Default Login Credentials

### Admin Access
- Username: `admin`
- Password: `admin123`

### Student Access
- Username: `student`
- Password: `student123`

## Key Components

### Models
- `User`: Base user entity for authentication
- `Student`: Student-specific information
- `Room`: Room details and availability
- Additional models for complaints, maintenance, etc.

### Data Access Objects (DAOs)
- `UserDAO`: User management operations
- `RoomDAO`: Room management operations
- Database connection and transaction handling

### Servlets
- `LoginServlet`: User authentication
- `LogoutServlet`: Session termination
- `RoomServlet`: Room management operations
- `AuthenticationFilter`: Security filter for protected resources

### JSP Pages
- Responsive design with modern CSS
- Role-based navigation
- Form validation and error handling
- Dynamic content rendering

## Development Guidelines

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Include proper comments and documentation
- Implement proper error handling

### Database Operations
- Use prepared statements to prevent SQL injection
- Implement proper connection management
- Handle database exceptions appropriately

### Security
- Input validation on both client and server side
- Session management for user authentication
- Role-based access control
- XSS and CSRF protection

## Future Enhancements

- Mobile application support
- Email notifications
- Payment gateway integration
- Advanced reporting with charts
- File upload for documents
- Real-time chat support
- API for third-party integrations

## Support

For issues and questions:
1. Check the error logs in GlassFish
2. Verify database connection and schema
3. Ensure all dependencies are properly configured
4. Review the application logs for detailed error information

## License

This project is developed for educational purposes. Feel free to modify and distribute as needed.