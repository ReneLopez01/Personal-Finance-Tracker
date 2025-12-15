import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class UserManager{

	String url = "jdbc:mysql://localhost:3306/finance_db"; 					//sql url 
    String user = System.getenv("DB_USER"); 										    // my mysql user 
    String password = System.getenv("DB_PASSWORD"); 									// my mysql password
    Connection conn;

    public UserManager (){ // Making a constructor to allow a try & catch 
        try {
            conn = DriverManager.getConnection(url, user, password);
        }catch (SQLException e) {
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
    																		// ADD EXPENSES
    public void addExpense(String email, double num , String category){
    	privateAddExpense(email,num,category);
    }
    private void privateAddExpense(String email, double num , String category){
        try{

        	String sqlStatement = "INSERT INTO finance_expense (user_email, user_expense, expense_category, time_of_expense) VALUES(?,?,?,CURRENT_DATE);";
        	PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setString(1 , email );
        	statement.setDouble(2 , num );
        	statement.setString(3 , category );
        	int r = statement.executeUpdate();

        }catch(SQLException e){
            System.out.println("Error in adding expense.");
        }
    	return;
    }

    																		// ADD INCOME
    public void addIncome(String email , double num){
    	privateAddIncome(email,num);
    }
    private void privateAddIncome(String email, double num){
        try{

        	String sqlStatement = "INSERT INTO finance_expense (user_email, user_expense, expense_category, time_of_expense) VALUES(?,?,'income',CURRENT_DATE);";
        	PreparedStatement statement = conn.prepareStatement(sqlStatement);
        	statement.setString(1 , email );
        	statement.setDouble(2 , num );
        	int r = statement.executeUpdate();

        }catch(SQLException e){
            System.out.println("Error in adding income");
        }
    	return;
    }
    																		//DELETE ACCOUNT 
    public  void deleteAccount(String email){
    	privateDeleteAccount(email);
    }
    private  void privateDeleteAccount(String email){
        try{

        	String sqlStatement = "DELETE FROM finance_expense WHERE user_email = ?";
        	PreparedStatement statement = conn.prepareStatement(sqlStatement);
        	statement.setString(1 , email );
        	int r = statement.executeUpdate();

        }catch(SQLException e){
            System.out.println("Error in deleting from finance_expense");
        }
        try{

        	String stmnt = "DELETE FROM finance_info WHERE user_email = ?";
        	PreparedStatement statement2 = conn.prepareStatement(stmnt);
        	statement2.setString(1 , email );
        	int r2 = statement2.executeUpdate();

        }catch(SQLException e){
            System.out.println("Error in deleting from finance_info");
        }
    	return;												
    }		
                        																//CHECKING IF LOGGED IN
    public boolean checkIfLogIn(String email, String password){
        boolean x = privCheckIfLogIn(email,password);
        return x;
    }
    private boolean privCheckIfLogIn(String email , String password){
        try{
        	String sqlStatement = "SELECT * FROM finance_info WHERE user_email = ? AND user_password = ?";
            PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setString(1 , email );
            statement.setString(2 , password);
            ResultSet resultset = statement.executeQuery();
            if(resultset.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println("Error in checking if user is logged in");
        }
        return false;
    }

    public void makeUser(String email , String password ){                  
        privateMakeUser(email,password);                                    // MAKING NEW USER
    }

    private void privateMakeUser(String email , String password ){   
        try{
            String sqlStatement = "INSERT INTO finance_info (user_email , user_password) VALUES(?,?);";
            PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setString(1 , email );
            statement.setString(2 , password );
            int r = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in making a new user.");
        }
        return;
    }	
}