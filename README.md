# Banking System using Java JDBC

A console-based Banking System application developed using **Java and JDBC**.  
This project demonstrates database connectivity, CRUD operations, prepared statements, transactions, and exception handling using MySQL.

## Features

- Create a new bank account
- User login authentication using username and password
- Deposit money into an account
- Withdraw money with balance validation
- Transfer money between accounts
- Transaction history tracking
- MySQL database integration using JDBC
- Secure SQL queries using PreparedStatement
- Transaction management using Commit and Rollback

## Technologies Used

- Java
- JDBC (Java Database Connectivity)
- MySQL
- SQL
- Eclipse IDE

# Project Structure

```

BankingSystem
│
├── src
│   └── jdbc_projects
│       └── BankingSystem.java
│
└── README.md

````

# Database Setup

Create a MySQL database:

```sql
CREATE DATABASE jdbclearning;

USE jdbclearning;
````

Create the bank account table:

```sql
CREATE TABLE bank_account(
    account_number INT PRIMARY KEY,
    account_holder_name VARCHAR(50),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    account_type VARCHAR(20),
    balance DOUBLE,
    mobile_number VARCHAR(15),
    email VARCHAR(50),
    city VARCHAR(30),
    created_date DATE
);
```

## ⚙️ JDBC Configuration

Update your MySQL credentials in `BankingSystem.java`:

```java
static String url="jdbc:mysql://localhost:3306/jdbclearning";
static String user="root";
static String password="your_password";
```

Add the MySQL JDBC Driver to your project before running.

## ▶️ How to Run

1. Clone the repository

```bash
git clone <repository-url>
```

2. Open the project in Eclipse IDE.

3. Add MySQL Connector/J library.

4. Start MySQL server.

5. Run:

```
BankingSystem.java
```

## 💡 Application Flow

```
          Banking System

              |
              |
     ----------------------
     |         |          |
 Create     Login     Services
 Account              |
                      |
        ----------------------------
        |        |        |        |
     Deposit  Withdraw Transfer History
```

## 🔐 Security Features

* Uses PreparedStatement to prevent SQL Injection.
* Validates account availability before transactions.
* Checks sufficient balance before withdrawal and transfer.
* Uses database transactions with commit and rollback for money transfer operations.

## 📸 Sample Operations

### Account Creation

```
Enter account number:
101

Account created successfully.
```

### Deposit

```
Enter amount:
5000

Amount deposited successfully.
```

### Money Transfer

```
Transfer successful!!
```

## 📚 Concepts Implemented

* JDBC Connection
* DriverManager
* PreparedStatement
* ResultSet
* SQL Queries
* CRUD Operations
* Database Transactions
* Commit and Rollback
* Exception Handling

## 🔮 Future Enhancements

* Add GUI using JavaFX/Swing
* Store transaction history permanently in database
* Add OTP-based authentication
* Implement role-based access (Admin/User)
* Use Connection Pooling
* Build REST API using Spring Boot

## 👩‍💻 Author

**Varshitha Tanniru**
