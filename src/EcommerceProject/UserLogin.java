package EcommerceProject;
import java.util.*;
import java.sql.*;
public class UserLogin {
	Connection con;
	Scanner sc=new Scanner(System.in);
	UserLogin(Connection con)
	{
		this.con=con;
	}
	int userLogin() throws SQLException
	{
		System.out.println("Enter the username:");
		String username=sc.next();
		System.out.println("Enter the password");
		String password=sc.next();
		String q="select user_id,username from users where username=? and password=?";
		PreparedStatement ps=con.prepareStatement(q);
		ps.setString(1,username);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			System.out.println("Login Successful");
			int userId=rs.getInt("user_id");
			String name=rs.getString("username");
			System.out.println("Welcome "+name);
			rs.close();
			ps.close();
			return userId;
		}
		else {
			System.out.println("Login Unsuccessfu");
			rs.close();
			ps.close();
			return -1;
		}
	}
}
