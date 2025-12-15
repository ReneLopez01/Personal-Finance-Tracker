import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

class ExpenseManager{

	String url = "jdbc:mysql://localhost:3306/finance_db"; 					//sql url 
    String user = System.getenv("DB_USER");   											// my mysql user 
    String password = System.getenv("DB_PASSWORD");  									// my mysql password
    Connection conn;

    public ExpenseManager (){ // Making a constructor to allow a try & catch 
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection failed.");
        }
    }

    public void closeConn(){                                            //close connection (used in main)
        try{
            conn.close();
        }catch(SQLException e){
            System.out.println("Closing Connection Failed");
        }
    }

    Calendar calendar = Calendar.getInstance();                   //SETTING CALENDAR ( to track monthly expense report )




    public void allExpenses(String email){                            //GET ALL EXPENSES
        privAllExpenses(email);
    }
    private void privAllExpenses(String email){
        try{
            String sqlStatement = "SELECT * FROM finance_expense WHERE user_email = ?;";
            PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setString(1 , email );
            ResultSet resultset = statement.executeQuery();

            while(resultset.next()){

            double expense = resultset.getDouble("user_expense");
            String category = resultset.getString("expense_category");
            Date date = resultset.getDate("time_of_expense");

            System.out.println(date + " | " + expense + " | " + category);

            }
        }catch(SQLException e){
            System.out.println("Error in getting all expenses.");
        }
        return;
    }

    public double expenseTotal(String email){
        double total = 0;
        total += expense(email , "food");
        total += expense(email , "housing");
        total += expense(email , "utilities");
        total += expense(email , "transportation");
        total += expense(email , "healthcare");
        total += expense(email , "debt");
        total += expense(email , "other");

        return total;
    }

    private Date startOfMonth(){
        calendar.set(Calendar.DAY_OF_MONTH, 1);                                //curent first day of the month 
        Date startOfMonth = new Date(calendar.getTimeInMillis());
        return startOfMonth;
    }

    private Date startOfNextMonth(){
        Calendar nextMonth = (Calendar) calendar.clone(); 
        nextMonth.set(Calendar.DAY_OF_MONTH, 1);         
        nextMonth.add(Calendar.MONTH, 1);                                       // add a month to make it the start of next month
        Date startOfNextMonth = new Date(nextMonth.getTimeInMillis());
        return startOfNextMonth;
    }



    public double expense (String email, String category ){                      //GET CATEGORY EXPENSE
        double sum = privExpense(email,category);
        return sum;
    }   

    private double privExpense(String email, String category){
        double sumOfCategory = 0.0; //Sum of expense in category for monthly report
        try{
            String sqlStatement = "SELECT * FROM finance_expense WHERE user_email = ? AND expense_category = ? AND time_of_expense >= ? AND time_of_expense < ? ;";
            PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setString(1 , email );
            statement.setString(2 , category);
            statement.setDate(3 , startOfMonth() );
            statement.setDate(4 , startOfNextMonth() );

            ResultSet resultset = statement.executeQuery();

            while(resultset.next()){
                sumOfCategory += resultset.getDouble("user_expense");
            }   
        }catch(SQLException e){
            System.out.println("Error in getting expense.");
        }
        return sumOfCategory;
    }
}