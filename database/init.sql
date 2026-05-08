-- Broker Skip Real Estate Database Schema

CREATE DATABASE IF NOT EXISTS broker_skip_db;
USE broker_skip_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    profile_photo_url VARCHAR(255),
    bio TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    pincode VARCHAR(10),
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_phone (phone_number),
    INDEX idx_email (email)
);

-- OTP Table
CREATE TABLE IF NOT EXISTS otp (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL,
    otp_code VARCHAR(6) NOT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_phone_otp (phone_number),
    INDEX idx_expires_at (expires_at)
);

-- Properties Table
CREATE TABLE IF NOT EXISTS properties (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    property_type VARCHAR(50),
    price DECIMAL(12, 2) NOT NULL,
    area DECIMAL(10, 2),
    bedrooms INT DEFAULT 0,
    bathrooms INT DEFAULT 0,
    location VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    pincode VARCHAR(10),
    image_urls JSON,
    amenities JSON,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_city (city),
    INDEX idx_is_active (is_active),
    FULLTEXT INDEX ft_title_description (title, description)
);

-- Favorites Table
CREATE TABLE IF NOT EXISTS favorites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    property_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_property (user_id, property_id),
    INDEX idx_user_id (user_id)
);

-- Insert Sample Users
INSERT INTO users (phone_number, email, first_name, last_name, city, state, pincode, is_verified) VALUES
('9876543210', 'user1@example.com', 'Raj', 'Kumar', 'Pune', 'Maharashtra', '411001', TRUE),
('9876543211', 'user2@example.com', 'Priya', 'Singh', 'Mumbai', 'Maharashtra', '400001', TRUE),
('9876543212', 'user3@example.com', 'Amit', 'Patel', 'Bangalore', 'Karnataka', '560001', TRUE);

-- Insert Sample Properties
INSERT INTO properties (user_id, title, description, property_type, price, area, bedrooms, bathrooms, location, city, state, pincode, amenities, is_active) VALUES
(1, '2 BHK Apartment in Pune', 'Beautiful apartment with modern amenities', 'Apartment', 5000000, 1200, 2, 2, 'Kalyani Nagar', 'Pune', 'Maharashtra', '411006', '["Gym", "Swimming Pool", "Parking", "Security"]', TRUE),
(2, '3 BHK House in Mumbai', 'Spacious house with garden', 'House', 15000000, 2500, 3, 3, 'Bandra', 'Mumbai', 'Maharashtra', '400050', '["Garden", "Garage", "Security"]', TRUE),
(1, '1 BHK Flat near Tech Park', 'Cozy flat perfect for young professionals', 'Apartment', 3500000, 800, 1, 1, 'Whitefield', 'Bangalore', 'Karnataka', '560066', '["Parking", "Water Supply", "Power Backup"]', TRUE);

-- Insert Sample Favorites
INSERT INTO favorites (user_id, property_id) VALUES
(2, 1),
(3, 1),
(1, 2);

-- Create Indexes for Performance
CREATE INDEX idx_properties_user_city ON properties(user_id, city);
CREATE INDEX idx_properties_price ON properties(price);
CREATE INDEX idx_properties_created_at ON properties(created_at);

echo "Database initialized successfully!";
