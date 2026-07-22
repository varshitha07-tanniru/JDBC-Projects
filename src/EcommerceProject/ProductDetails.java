package EcommerceProject;
import java.sql.*;
public class ProductDetails {
	
	void productDetails(String pro_name,Connection con) throws SQLException
	{
		ResultSet rs=null;
		PreparedStatement ps=null;
		String query="select p.product_name,p.price,p.stock,c.category_name"+
				" from products p "+
				" join categories c on p.category_id=c.category_id "+
				" where p.product_name=? ";
		ps=con.prepareStatement(query);
		ps.setString(1,pro_name);
		rs=ps.executeQuery();
		if(rs.next())
		{
			String product_name=rs.getString("product_name");
			double price=rs.getDouble("price");
			int stock=rs.getInt("stock");
			String category_name=rs.getString("category_name");
			System.out.println("-------Product Details-------");
			System.out.println("Product_name :"+product_name);
			System.out.println("Price :"+price);
			System.out.println("Stock :"+stock);
			System.out.println("Category_Name :"+category_name);
		}
		else
		{
			System.out.println("Product not found.");
		}
		if(rs!=null)
			rs.close();
		if(ps!=null)
			ps.close();
	}
}
