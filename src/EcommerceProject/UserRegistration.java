package EcommerceProject;
import java.sql.*;
import java.util.*;
public class UserRegistration {
	Connection con;
	Scanner sc=new Scanner(System.in);
	UserRegistration(Connection con)
	{
		this.con=con;
	}
	void userRegistration() throws SQLException
	{
		System.out.println("Enter the userName");
		String name=sc.next();
		System.out.println("Enter the email");
		String email=sc.next();
		System.out.println("Enter the phoneNumber");
		String phno=sc.next();
		System.out.println("Enter the Password");
		String password=sc.next();
		String q="insert into users(username,password,email,phone) values(?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(q);
		ps.setString(1,name);
		ps.setString(2, password);
		ps.setString(3, email);
		ps.setString(4, phno);
		int rows=ps.executeUpdate();
		if(rows>0)
			System.out.println("Registartion Succesful");
		else
			System.out.println("Registration Failed");
	}
}
