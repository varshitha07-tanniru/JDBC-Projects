package EcommerceProject;
import java.sql.*;
public class SearchProducts {
	
	void searchByProductName(String pro_name,Connection con) throws SQLException
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
	void searchByCategory(String cate_name,Connection con) throws SQLException
	{
		ResultSet rs=null;
		PreparedStatement ps=null;
		String query="select p.product_name,p.price,p.stock,c.category_name"+
				" from products p "+
				" join categories c on p.category_id=c.category_id "+
				" where c.category_name=? ";
		ps=con.prepareStatement(query);
		ps.setString(1,cate_name);
		rs=ps.executeQuery();
		boolean found=false;
		while(rs.next())
		{
			found=true;
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
		if(!found)
		{
			System.out.println("Product not found.");
		}
		if(rs!=null)
			rs.close();
		if(ps!=null)
			ps.close();
	}
	void searchByPriceRange(Connection con, boolean increasing) throws SQLException
	{
	    String order = increasing ? "ASC" : "DESC";
	    String query =
	        "select p.product_name,p.price,p.stock,c.category_name " +
	        "from products p " +
	        "join categories c on p.category_id=c.category_id " +
	        "order by p.price " + order;
	    PreparedStatement ps = con.prepareStatement(query);
	    ResultSet rs = ps.executeQuery();
	    boolean found = false;
	    while(rs.next())
	    {
	        found = true;
	        System.out.println("-------Product Details-------");
	        System.out.println("Product_name :" + rs.getString("product_name"));
	        System.out.println("Price :" + rs.getDouble("price"));
	        System.out.println("Stock :" + rs.getInt("stock"));
	        System.out.println("Category_Name :" + rs.getString("category_name"));
	    }
	    if(!found)
	        System.out.println("Product not found.");
	    rs.close();
	    ps.close();
	}
	
}
