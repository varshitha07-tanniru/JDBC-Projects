package jdbc_projects;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
public class BankingSystem {
	static String url="jdbc:mysql://localhost:3306/jdbclearning";
	static String user="root";
	static String password="Varshitha@09";
	static Scanner sc=new Scanner(System.in);
	static String transaction_history="";
	static int currentAccount=-1;
	static int loginSystem() throws SQLException
	{
		System.out.println("Enter the UserName:");
		String username=sc.next();
		System.out.println("Enter the password");
		String pass=sc.next();
		Connection con=DriverManager.getConnection(url,user,password);
		PreparedStatement ps=con.prepareStatement("select account_number from bank_account where username=? and password=?");
		ps.setString(1, username);
		ps.setString(2, pass);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
		    currentAccount = rs.getInt("account_number");
		    rs.close();
		    ps.close();
		    con.close();
		    return currentAccount;
		}
		rs.close();
		ps.close();
		con.close();
		return -1;
	}
	static int createAccount() throws SQLException
	{
		System.out.println("Enter the account_number:");
		int acc_no=sc.nextInt();
		System.out.println("Enter the account_holder_name");
		String name=sc.next();
		System.out.println("Enter the UserName:");
		String username=sc.next();
		System.out.println("Enter the password");
		String pass=sc.next();
		System.out.println("Enter the Account Type");
		String account_type=sc.next();
		System.out.println("Enter the Balance:");
		double bal=sc.nextDouble();
		System.out.println("Enter the mobile_number:");
		String phno=sc.next();
		System.out.println("Enter the Email:");
		String email=sc.next();
		System.out.println("Enter the City:");
		String city=sc.next();
		LocalDate today=LocalDate.now();
		Connection con = DriverManager.getConnection(url, user, password);
		PreparedStatement ps = con.prepareStatement(
		    "INSERT INTO bank_account(account_number, account_holder_name, username, password, account_type, balance, mobile_number, email, city, created_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
		);
		ps.setInt(1, acc_no);
		ps.setString(2, name);
		ps.setString(3, username);
		ps.setString(4, pass);
		ps.setString(5, account_type);
		ps.setDouble(6, bal);
		ps.setString(7, phno);
		ps.setString(8, email);
		ps.setString(9, city);
		ps.setDate(10, java.sql.Date.valueOf(today));
		int rows = ps.executeUpdate();
		ps.close();
		con.close();
		transaction_history+="Account created\n";
		return rows;

	}
	static void deposit() throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the account_number to be deposited");
		int acc_no=sc.nextInt();
		System.out.println("Enter the amount to be deposited");
		double amt=sc.nextDouble();
		Connection con = DriverManager.getConnection(url, user, password);
		String q="select balance from bank_account where account_number=?";
		PreparedStatement ps1=con.prepareStatement(q);
		ps1.setInt(1, acc_no);
		ResultSet rs=ps1.executeQuery();
		double bal=0;
		PreparedStatement ps=null;
		if(rs.next()) {
			bal=rs.getDouble("balance");
			String update = "UPDATE bank_account SET balance=? WHERE account_number=?";
			ps=con.prepareStatement(update);
			ps.setDouble(1,bal+amt);
			ps.setInt(2,acc_no);
			int rows=ps.executeUpdate();
			if(rows > 0) {
	            System.out.println("Amount deposited successfully.");
	        	ps.close();
	        	} 
			else {
	        System.out.println("Account not found.");
			}
		}
		else
		{
			System.out.println("Account Not Found");
		}
		rs.close();
		if(ps!=null)
			ps.close();
	    ps1.close();
	    con.close();
	    transaction_history+="Deposited Money : "+amt+"\n";
	  }
	public static void withdraw() throws SQLException {
		Connection con=DriverManager.getConnection(url,user,password);
		System.out.println("Enter the Account Number");
		int acc_no=sc.nextInt();
		String query="select balance from bank_account where account_number=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1,acc_no);
		ResultSet rs=ps.executeQuery();
		double bal=0;
		if(rs.next())
			bal=rs.getDouble("balance");
		else {
			System.out.println("Account Number is not found");
			rs.close();
		    ps.close();
		    con.close();
			return;
		}
		System.out.println("Enter the Amount to WithDraw");
		double amt=sc.nextDouble();
		if(amt<=0)
		{
		    System.out.println("Invalid Amount");
		}
		else if(amt>bal)
		{
		    System.out.println("Insufficient Balance");
		}
		else
		{
			//update the account
			String upQuery="update bank_account set balance=balance-? where account_number=?";
			PreparedStatement ps1=con.prepareStatement(upQuery);
			ps1.setDouble(1, amt);
			ps1.setInt(2,acc_no);
			ps1.executeUpdate();
			System.out.println("Amount withdrawn successfully");
			ps1.close();
		}
		ps.close();
		con.close();
		transaction_history+="Amount Withdrawn : "+amt+"\n";
	}
	public static void transferMoney() throws SQLException {
		Connection con=DriverManager.getConnection(url,user,password);
		con.setAutoCommit(false);
		System.out.println("Enter the Account Number(Transfer Account):");
		int acc_no=sc.nextInt();
		String query="select balance from bank_account where account_number=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, acc_no);
		System.out.println("Enter the account number(To Transfer Account):");
		int acc_no1=sc.nextInt();
		if(acc_no==acc_no1)
		{
		    System.out.println("Cannot transfer to same account");
		    return;
		}
		String q2="select balance from bank_account where account_number=?";
		PreparedStatement ps1=con.prepareStatement(q2);
		ps1.setInt(1, acc_no1);
		ResultSet rs1=ps.executeQuery();
		ResultSet rs2=ps1.executeQuery();
		System.out.println("Enter the amount to be Transfered");
		double amt=sc.nextDouble();
		double bal1 = 0,bal2=0;
		boolean senderExists=false;
		boolean receiverExists=false;

		if(rs1.next())
		{
		    senderExists=true;
		    bal1=rs1.getDouble("balance");
		}

		if(rs2.next())
		{
		    receiverExists=true;
		    bal2=rs2.getDouble("balance");
		}

		if(!senderExists || !receiverExists)
		{
		    System.out.println("Invalid account number");
		    return;
		}
		if(amt>bal1 || amt<=0)
			System.out.println("Insuffient Amount");
		else
		{
			try {
			bal1-=amt;
			bal2+=amt;
			String q="update bank_account set balance=? where account_number=?";
			PreparedStatement p=con.prepareStatement(q);
			p.setDouble(1, bal1);
			p.setInt(2, acc_no);
			p.executeUpdate();
			String que="update bank_account set balance=? where account_number=?";
			PreparedStatement prs=con.prepareStatement(que);
			prs.setDouble(1, bal2);
			prs.setInt(2, acc_no1);
			prs.executeUpdate();
			System.out.println("Transfer Succesfull!!");
			String sta="Transfered money from "+acc_no+" to "+acc_no1+"\t"+"currentBalance"+bal1+"\n";
			transaction_history+=sta;
			con.commit();
			}
			catch(Exception e)
			{
				con.rollback();
				e.printStackTrace();
			}
		}
	}
	public static void TransactionHistory(String statement)
	{
	    System.out.println(statement);
	    
	    if(transaction_history.equals(""))
	        System.out.println("No Transactions");

	    else
	        System.out.println(transaction_history);
	}
	public static void main(String[] args)
	{
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    }
	    catch(ClassNotFoundException e)
	    {
	        e.printStackTrace();
	    }

	    try {
	        while(true)
	        {
	            System.out.println("\n====== BANKING SYSTEM ======");
	            System.out.println("1. Create Account");
	            System.out.println("2. Login");
	            System.out.println("3. Deposit Money");
	            System.out.println("4. Withdraw Money");
	            System.out.println("5. Transfer Money");
	            System.out.println("6. Transaction History");
	            System.out.println("Enter your choice:");

	            int choice=sc.nextInt();

	            switch(choice)
	            {
	                case 1:
	                    int rows=createAccount();
	                    if(rows>0)
	                        System.out.println("Account created successfully.");
	                    else
	                        System.out.println("Account creation failed.");
	                    break;
	                case 2:
	                    int acc_no=loginSystem();

	                    if(acc_no!=-1)
	                    {
	                        System.out.println("Login Successful");
	                        System.out.println("Account Number : "+acc_no);
	                    }
	                    else
	                    {
	                        System.out.println("Invalid Username or Password");
	                    }
	                    break;
	                case 3:
	                    deposit();
	                    break;
	                case 4:
	                    withdraw();
	                    break;
	                case 5:
	                	transferMoney();
	                	break;
	                case 6:
	                	String statement="---Transaction History----";
	                	TransactionHistory(statement);
	                	break;
	                case 7:
	                    System.out.println("Thank you for using Banking System");
	                    System.exit(0);
	                default:
	                    System.out.println("Invalid Choice");
	            }
	        }
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	    }
	}
}
