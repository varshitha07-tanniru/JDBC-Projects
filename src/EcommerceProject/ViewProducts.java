package EcommerceProject;
import java.sql.*;
public class ViewProducts {
	
	void ViewProducts(Connection con) throws SQLException
	{
		String query="select * from products";
		PreparedStatement ps=con.prepareStatement(query);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			int product_id=rs.getInt("product_id");
			String product_name=rs.getString("product_name");
			double price=rs.getDouble("price");
			int stock=rs.getInt("stock");
			int category_id=rs.getInt("category_id");
			System.out.println("Product_id:"+product_id);
			System.out.println("Product_name:"+product_name);
			System.out.println("Price:"+price);
			System.out.println("Stock:"+stock);
			System.out.println("Category_id:"+category_id);
		}
		
	}
}
