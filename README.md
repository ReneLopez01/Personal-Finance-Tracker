### Personal Finance Tracker
A Java based CLI (Command Line Interface) application that allows users to track income and expenses using a MySQL database.

### Features
- User registration and login
- Add income and expenses with categories
- Generate monthly expense reports
- View all transaction history
- Delete account

### Technologies
- Java 17
- MySQL
- JDBC (mysql connector)
- CLI (command line interface)

### Prerequisites
- Java JDK 17 or higher
- MySQL Server
- MySQL JDBC Driver (I used;  mysql-connector-j-9.5.0.jar)

### Installation
1. Clone the repository:
   ```Run in terminal```
   git clone https://github.com/ReneLopez01/Personal_Finance_Tracker.git

2. Download mySQL JBDC driver and insert into folder,
   
3. Create the database on MySQL
   
CREATE DATABASE finance_db;
USE finance_db;

CREATE TABLE finance_info (
    user_email VARCHAR(50) UNIQUE,
    user_password VARCHAR(100)
);

CREATE TABLE finance_expense (
    user_email VARCHAR(50),
    user_expense DECIMAL(12,2),
    expense_category VARCHAR(13),
    time_of_expense DATE
);

4. Then enter your User / Password for your MySQL
   ```Run this in terminal```
   export DB_USER=root
   
   export DB_PASSWORD=yourSQLpassword

5. Then Compile project
   ```Run this in terminal```
   javac -cp .:mysql-connector-j-9.5.0.jar FinanceTracker.java ExpenseManager.java UserManager.java

6. Run project
   ```Run this in terminal```
   java -cp .:mysql-connector-j-9.5.0.jar FinanceTracker


### Usage 
-how to use application , provided with screenshots & instructions;

1. Log in or create a new account
   ![Login Screen](screenshots/login.png)
   
3. Add income or expenses
   ![Add Expense](screenshots/add-expense.png)

4. Generate monthly reports
   ![Monthly Report](screenshots/monthly-report.png)
   
6. View all transactions
   
7. Exit the program

8. Delete Account


## Project Structure
- FinanceTracker.java          # Main program
- ExpenseManager.java          # Handles fetching data from the database
- UserManager.java             # Handles adding data and checking user info
- mysql-connector-j-9.5.0.jar  # JDBC driver 

## Final Notes
- CLI-based
- Make sure MySQL is running and credentials are correct
