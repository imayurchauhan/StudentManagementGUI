# Student Management System – Java Swing GUI

This is a **Student Management System** desktop application built using **Java Swing** with MySQL database connectivity.

### 🚀 Features:
- 🔐 Add, View, Update, Delete Students
- 🔍 Search Student by ID
- 📊 Display Chart (Bar Graph - Students Per Course)
- 🎨 Dark Theme using FlatLaf
- 🗃️ JDBC-based MySQL Integration
- 👋 Animated Welcome Interface
- 🖥️ Multi-tab GUI layout

---

### 🛠 Technologies Used:
- Java (JDK 17+)
- Swing (GUI)
- MySQL (Database)
- JDBC (Database connectivity)
- FlatLaf (modern UI look)
- JFreeChart (for graphs)

---

### 📦 How to Run

#### 🔧 Requirements:
- Java JDK installed (17+)
- MySQL Server running (default user: root)
- MySQL database created with a `students` table:
```sql
CREATE DATABASE studentdb;

USE studentdb;

CREATE TABLE students (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  age INT,
  course VARCHAR(100)
);
