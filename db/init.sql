-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS transportation;

-- Use the created database
USE transportation;

-- Drop existing tables (optional: to start fresh)
DROP TABLE IF EXISTS electric_vehicles;
DROP TABLE IF EXISTS hybrid_vehicles;
DROP TABLE IF EXISTS vehicles;

-- Create the 'vehicles' table
CREATE TABLE vehicles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    efficiency DOUBLE NOT NULL,
    battery_capacity DOUBLE DEFAULT NULL,
    emissions_rate DOUBLE DEFAULT NULL
);

-- Create the 'electric_vehicles' table
CREATE TABLE electric_vehicles (
    vehicle_id INT PRIMARY KEY,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE
);

-- Create the 'hybrid_vehicles' table
CREATE TABLE hybrid_vehicles (
    vehicle_id INT PRIMARY KEY,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE
);
