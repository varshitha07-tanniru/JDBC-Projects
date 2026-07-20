package EcommerceProject;
import java.util.*;
import java.sql.*;
public class UserAccount {
	Connection con=null;
	PreparedStatement ps1=null;
	Scanner sc=new Scanner(System.in);
	UserAccount(Connection con)
	{
		this.con=con;
	}
	void userAccount(int userid) throws SQLException
	{
		String q="select username,password from users where user_id=?";
		PreparedStatement ps=con.prepareStatement(q);
		ps.setInt(1,userid);
		ResultSet rs=ps.executeQuery();
		System.out.println("Would you like to Change Password?");
		boolean flag=sc.nextBoolean();
		if(flag)
		{
			System.out.println("Enter the new Password");
			String pass=sc.next();
			String query="update users set password=? where user_id=?";
			ps1=con.prepareStatement(query);
			ps1.setString(1,pass);
			ps1.setInt(2, userid);
			int rows=ps1.executeUpdate();
			if(rows>0)
			    System.out.println("Password Updated Successfully");
			else
			    System.out.println("Password Update Failed");
		}
		else
		{
			System.out.println("Updation Unsuccessful");
		}
		if(ps!=null)
			ps.close();
		if(rs!=null)
			rs.close();
		if(ps1!=null)
			ps1.close();
	}
}
