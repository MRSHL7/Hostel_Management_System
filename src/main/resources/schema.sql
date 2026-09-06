-- Hostel Management System Database Schema
-- MySQL Database Schema for JDK 21 and GlassFish 8.x

-- Create database
CREATE DATABASE IF NOT EXISTS hostel_management;
USE hostel_management;
-- Hostel Management System Schema for PostgreSQL

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15),
    user_type VARCHAR(20) CHECK (user_type IN ('ADMIN', 'STUDENT', 'WARDEN')),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms (
    room_id SERIAL PRIMARY KEY,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    capacity INT NOT NULL,
    occupied INT DEFAULT 0,
    room_type VARCHAR(15) CHECK (room_type IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'DORMITORY')),
    fee NUMERIC(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE', 'OCCUPIED', 'MAINTENANCE', 'RESERVED')),
    description TEXT,
    facilities TEXT,
    floor_number INT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    course VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    department VARCHAR(100) NOT NULL,
    guardian_name VARCHAR(100),
    guardian_phone VARCHAR(15),
    address TEXT,
    city VARCHAR(50),
    state VARCHAR(50),
    pincode VARCHAR(10),
    date_of_birth DATE,
    gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    blood_group VARCHAR(5),
    room_id INT REFERENCES rooms(room_id) ON DELETE SET NULL,
    check_in_date DATE,
    check_out_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'GRADUATED', 'SUSPENDED')),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE room_allocations (
    allocation_id SERIAL PRIMARY KEY,
    student_id INT REFERENCES students(student_id) ON DELETE CASCADE,
    room_id INT REFERENCES rooms(room_id) ON DELETE CASCADE,
    allocated_date DATE NOT NULL,
    deallocated_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'COMPLETED', 'CANCELLED')),
    allocated_by INT REFERENCES users(user_id),
    notes TEXT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fee_payments (
    payment_id SERIAL PRIMARY KEY,
    student_id INT REFERENCES students(student_id) ON DELETE CASCADE,
    amount NUMERIC(10, 2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_month DATE NOT NULL,
    payment_method VARCHAR(20) CHECK (payment_method IN ('CASH', 'CARD', 'ONLINE', 'CHEQUE')),
    transaction_id VARCHAR(100),
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED')),
    notes TEXT,
    received_by INT REFERENCES users(user_id),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE complaints (
    complaint_id SERIAL PRIMARY KEY,
    student_id INT REFERENCES students(student_id) ON DELETE CASCADE,
    subject VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(20) CHECK (category IN ('MAINTENANCE', 'FOOD', 'SECURITY', 'CLEANLINESS', 'OTHER')),
    priority VARCHAR(10) DEFAULT 'MEDIUM' CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'URGENT')),
    status VARCHAR(20) DEFAULT 'SUBMITTED' CHECK (status IN ('SUBMITTED', 'IN_PROGRESS', 'RESOLVED', 'CLOSED')),
    assigned_to INT REFERENCES users(user_id),
    resolution_notes TEXT,
    submitted_date DATE NOT NULL,
    resolved_date DATE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE maintenance_requests (
    request_id SERIAL PRIMARY KEY,
    room_id INT REFERENCES rooms(room_id) ON DELETE CASCADE,
    requested_by INT REFERENCES users(user_id),
    issue_description TEXT NOT NULL,
    priority VARCHAR(10) DEFAULT 'MEDIUM' CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'URGENT')),
    status VARCHAR(20) DEFAULT 'SUBMITTED' CHECK (status IN ('SUBMITTED', 'ASSIGNED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
    assigned_to INT REFERENCES users(user_id),
    estimated_cost NUMERIC(10, 2),
    actual_cost NUMERIC(10, 2),
    scheduled_date DATE,
    completed_date DATE,
    notes TEXT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE visitor_logs (
    log_id SERIAL PRIMARY KEY,
    student_id INT REFERENCES students(student_id) ON DELETE CASCADE,
    visitor_name VARCHAR(100) NOT NULL,
    visitor_phone VARCHAR(15),
    purpose VARCHAR(200),
    check_in_time TIMESTAMPTZ NOT NULL,
    check_out_time TIMESTAMPTZ,
    authorized_by INT REFERENCES users(user_id),
    notes TEXT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);



-- Sample users for Admin/Student (passwords should be properly hashed in production)
INSERT INTO users (username, password, email, first_name, last_name, user_type)
VALUES 
  ('admin', 'admin123', 'admin@hostel.com', 'System', 'Administrator', 'ADMIN'),
  ('student', 'student123', 'student@example.com', 'John', 'Doe', 'STUDENT');

-- Sample demo room
INSERT INTO rooms (room_number, capacity, room_type, fee, floor_number, facilities, description)
VALUES 
  ('101', 1, 'SINGLE', 8500.00, 1, 'AC, Attached Bathroom, WiFi, Study Table', 'Premium single room with all amenities');

-- Sample student account (using ID of demo student user above)
INSERT INTO students (user_id, student_number, course, year, department)
VALUES (2, 'STU2025001', 'Computer Science Engineering', 2, 'Computer Science');
