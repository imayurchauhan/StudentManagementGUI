# Student Management System GUI

A Java Swing-based desktop application for managing student records with MySQL database integration.

---

## Features

- Add, View, Update, Delete student records
- Search students by ID
- Multi-tab GUI with attractive design and animations
- Database connectivity using JDBC and MySQL
- Uses FlatLaf for modern UI look and JFreeChart for charts

---

## Requirements 

- Java JDK 17 or later
- MySQL Server installed and running
- MySQL Connector/J (JDBC driver)
- FlatLaf 3.4 library
- JFreeChart library

---

## Setup and Run

Database Setup

1. Create a database in MySQL:
   
CREATE DATABASE studentdb;
USE studentdb;

2. Create the students table:

CREATE TABLE students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  age INT,
  course VARCHAR(50)
);

**Update your database credentials in DBConnect.java if needed.


### 1. Clone or download this repository

### 2. Add required libraries (JAR files) to your project folder:

- `mysql-connector-j-9.3.0.jar`
- `flatlaf-3.4.jar`
- `jfreechart-1.5.3.jar`
- `jcommon-1.0.24.jar`

### 3. Compile Java files

Open a terminal or Git Bash in the project folder and run:

```bash
javac -cp ".;mysql-connector-j-9.3.0.jar;flatlaf-3.4.jar;jfreechart-1.5.3.jar;jcommon-1.0.24.jar" *.java ''''

