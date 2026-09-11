# 💼 Job Application Tracker

A console-based **Java application** built using **JDBC and MySQL** to track, manage, search, and analyze job applications in an organized way.

This project was developed to strengthen practical knowledge of **Java, JDBC, SQL, database connectivity, CRUD operations, PreparedStatement, ResultSet, and MySQL**.

---

## 🚀 Features

* ➕ Add new job applications
* 📋 View all job applications
* 🔍 Search applications by company
* 🔄 Update application status
* 🗑️ Delete applications
* 📌 Filter applications by status
* 📅 View interview applications
* 📊 View application statistics
* 🗄️ Store application data in MySQL
* 🔐 Use PreparedStatement for database operations

---

## 🛠️ Tech Stack

* **Java**
* **JDBC (Java Database Connectivity)**
* **MySQL**
* **IntelliJ IDEA**
* **Git & GitHub**

---

## 🗃️ Database Structure

Database:

```sql
CREATE DATABASE job_tracker;

USE job_tracker;
```

Table:

```sql
CREATE TABLE applications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(100) NOT NULL,
    job_role VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    applied_date DATE,
    interview_date DATE,
    notes VARCHAR(500)
);
```

---

## 📌 Application Status

The project supports the following application statuses:

* Applied
* Shortlisted
* Online Assessment
* Interview
* Selected
* Rejected

---

## 🖥️ Application Menu

```text
=================================
       JOB APPLICATION TRACKER
=================================
1. Add Job Application
2. View All Applications
3. Search by Company
4. Update Application Status
5. Delete Application
6. View Applications by Status
7. View Interview Applications
8. Application Statistics
9. Exit
=================================
```

---

## 🔌 JDBC Concepts Used

This project provides practical implementation of:

* JDBC Driver Loading
* Database Connection
* `Connection`
* `Statement`
* `PreparedStatement`
* `ResultSet`
* `executeQuery()`
* `executeUpdate()`
* SQL `INSERT`
* SQL `SELECT`
* SQL `UPDATE`
* SQL `DELETE`
* Exception Handling
* MySQL Database Operations

---

## 🔎 Example Workflow

```text
User
 ↓
Java Console Application
 ↓
JDBC
 ↓
MySQL Database
 ↓
CRUD Operations
 ↓
Application Tracking & Statistics
```

---

## ⚙️ How to Run

### 1. Clone the repository

```bash
git clone <your-repository-url>
```

### 2. Create the MySQL database

Run:

```sql
CREATE DATABASE job_tracker;
```

### 3. Create the `applications` table

Run the table creation query provided above.

### 4. Configure MySQL credentials

Update the following values in the Java file:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/job_tracker";

private static final String USERNAME = "root";

private static final String PASSWORD = "your_password";
```

> Never upload your actual database password to GitHub.

### 5. Add MySQL Connector/J

Make sure the MySQL JDBC driver is available in the project classpath.

### 6. Run the application

Run:

```text
JobApplicationTracker.java
```

---

## 📊 Sample Statistics

```text
========== APPLICATION STATISTICS ==========

Applied       : 8
Shortlisted   : 3
Interview     : 2
Selected      : 1
Rejected      : 6

--------------------------------------------
Total Applications : 20
```

---

## 🎯 Learning Outcomes

Through this project, I strengthened my practical understanding of:

* Java database connectivity
* Writing SQL queries from Java
* CRUD operations
* PreparedStatement
* ResultSet
* Database-driven application design
* Exception handling
* Searching and filtering data
* SQL aggregation using `COUNT()` and `GROUP BY`

---

## 🔮 Future Improvements

* 🌐 Convert the console application into a Spring Boot REST API
* 🖥️ Add a React.js frontend
* 🔐 Add user authentication
* 📧 Add email reminders for interviews
* 📊 Add graphical application analytics
* ☁️ Deploy the application online

---

## 👨‍💻 Author

**Archit Nikam**

B.E. Computer Science & Engineering

### Technologies

`Java` `JDBC` `MySQL` `SQL` `Git` `GitHub`

---

⭐ If you find this project useful, feel free to explore the repository and share your feedback.

