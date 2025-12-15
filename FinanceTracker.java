import java.util.Scanner;
/* 

Remove quarintine on JAR file :
/usr/bin/xattr -d com.apple.quarantine ~/Desktop/Finance\ tracker/Personal_Finance_Tracker/mysql-connector-j-9.5.0.jar

Compile :
cd ~/Desktop/Finance\ tracker/Personal_Finance_Tracker/
javac -cp .:mysql-connector-j-9.5.0.jar FinanceTracker.java ExpenseManager.java UserManager.java

Input user/psswrd :
export DB_USER= root
export DB_PASSWORD= '...'

Run:
java -cp .:mysql-connector-j-9.5.0.jar FinanceTracker

*/
class FinanceTracker{
	public static void main(String[] args){

        Scanner scan = new Scanner(System.in); //importing tool
        ExpenseManager get = new ExpenseManager();
        UserManager info = new UserManager();

        String userEmail = "";

        boolean isInSystem = false;
        while( !isInSystem ){ // while loop to ensure log-in

        System.out.println("1. Log-in"); // Get log-in info if chosen this
        System.out.println("2. Create Account"); // if chosen 2 create users , username , password.

        int n = scan.nextInt();
        scan.nextLine();
            switch(n) {

                case 1:
                    System.out.println("Enter your EMAIL : "); //log-in
                    String userEmailcheck = scan.nextLine();

                    System.out.println("Enter your PASSWORD : "); //log-in
                    String userPWcheck = scan.nextLine();

                    userEmail = userEmailcheck;
                    isInSystem = info.checkIfLogIn(userEmailcheck , userPWcheck); // return a boolean either true or false depending if they are in the system
                    break;
                case 2:
                    System.out.println("Enter a EMAIL : "); //making an account for user;
                    String newUserEmail = scan.nextLine();
                    String newUserPW = "";

                    boolean pswrdRequirement = false;
                    while(!pswrdRequirement){               // while loop to ensure they meet the password requirment

                        System.out.println("Enter a PASSWORD : \n ( At least 8 Characters long )");
                        newUserPW = scan.nextLine();

                        if(newUserPW.length() >= 8) pswrdRequirement = true; // checking password requirment
                        else System.out.println("Please enter a your PASSWORD with a minimun of 8 Characters .");
                    }
                    info.makeUser(newUserEmail,newUserPW);
                    userEmail = newUserEmail;
                    isInSystem = true;
                    break;

                default:
                    System.out.println("Please choose 1 or 2. 😡");
            }
        }
        boolean exit = false;
        while(!exit){
            System.out.println("----- Welcome -----" );
            System.out.println("1. Add income");
            System.out.println("2. Add expense");
            System.out.println("3. See monthly report");
            System.out.println("4. See all transaction history");
            System.out.println("5. Exit.");
            System.out.println("6. Delete Account.");

            int userInput = scan.nextInt();
            scan.nextLine();
            switch(userInput){
                case 1:
                    System.out.println("Add your Income!");

                    double userIncome = scan.nextDouble();

                    info.addIncome(userEmail , userIncome);
                    System.out.println("Income Added!💵");
                    break;
                case 2:

                    System.out.println("Add your Expense , how much money did you spend ?");
                    double expense = scan.nextDouble();
                    scan.nextLine();
                    boolean w = false;

                    String userCategory = "";
                    while(!w){
                        System.out.println("Now choose the category your expense fell under : ");
                        System.out.println("Food , Housing , Utilities , Transportation , HealthCare , Debt , Other");

                        userCategory = scan.nextLine();

                        String[] categories = {"food","housing","utilities","transportation","healthcare","debt","other"};
                        for(int i = 0 ; i<categories.length; i++){
                            if(userCategory.equalsIgnoreCase(categories[i])) w = true;
                        }
                        if(!w) {
                            System.out.println("Invalid category, try again.");
                        }
                    }
                    String lowerCaseCategory = userCategory.toLowerCase();
                    info.addExpense(userEmail,expense,lowerCaseCategory);

                    System.out.println("Expense Added!😊");
                    break;
                case 3:
                    System.out.println("----- Monthly report -----");
                    System.out.println("Income : " + get.expense(userEmail ,"income"));
                    System.out.println("Expenses : " + get.expenseTotal(userEmail) );
                    System.out.println("*** Categories ***");
                    System.out.println("Food : " + get.expense(userEmail ,"food"));
                    System.out.println("Housing : " + get.expense(userEmail ,"housing"));
                    System.out.println("Utilities : " + get.expense(userEmail ,"utilities"));
                    System.out.println("Transportation : " + get.expense(userEmail ,"transportation"));
                    System.out.println("HealthCare : " + get.expense(userEmail ,"healthcare"));
                    System.out.println("Debt : " + get.expense(userEmail ,"debt"));
                    System.out.println("Other : " + get.expense(userEmail ,"other"));
                    System.out.println("--------------------------");
                    break;

                case 4:
                    System.out.println("----- All Transactions History -----");
                    get.allExpenses(userEmail);
                    System.out.println("------------------------------------");
                    break;
                case 5:
                    System.out.println("Are you sure you want to EXIT (yes / no)");
                    String input = scan.nextLine();
                    if (input.equalsIgnoreCase("yes")) {
                        System.out.println("Bye 👋");
                        exit = true;
                    }else{
                        System.out.println("Okay 👍 ");
                    }
                    break;
                case 6:
                    System.out.println("Are you sure you want to DELETE your account. (yes / no)");
                    System.out.println("--------- This deletes all your data -------");
                    String deleteInput = scan.nextLine();

                    if (deleteInput.equalsIgnoreCase("yes")) {
                        System.out.println("Sorry I didnt meet your expectations , Bye 👋");
                        info.deleteAccount(userEmail);
                        exit = true;
                    }else{
                        System.out.println("Phew, Okay you got me a little worried 👍 ");
                    }
                    break;
                default:
                    System.out.println("Please choose 1, 2, 3, 4, or 5.😡");

            }
        }
        scan.close();
        get.closeConn();
        info.closeConn();
	}
}