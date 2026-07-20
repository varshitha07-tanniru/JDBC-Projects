package EcommerceProject;
import java.sql.*;
public class SqlConnection {
	public static void main(String[] args) {
		Connection con=null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Connection 
		String url="jdbc:mysql://localhost:3306/ecommerce_db";
		String user="root";
		String password="Varshitha@09";
		con=DriverManager.getConnection(url,user,password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			try {
				if(con!=null)
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

