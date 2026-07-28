package EcommerceProject;
import java.sql.*;
public class ManageCart {
	
	public void viewCartItems(Connection con,int userId) throws SQLException
	{
		String query="SELECT \r\n"
				+ "c.cart_id,\r\n"
				+ "u.username,\r\n"
				+ "p.product_name,\r\n"
				+ "c.quantity\r\n"
				+ "FROM cart c\r\n"
				+ "JOIN products p \r\n"
				+ "ON c.product_id = p.product_id\r\n"
				+ "JOIN users u \r\n"
				+ "ON c.user_id = u.user_id where user_id=?;";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, userId);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			System.out.println("CartId:"+rs.getInt("cart_id"));
			System.out.println("UserName:"+rs.getString("username"));
			System.out.println("ProductName:"+rs.getString("product_name"));
			System.out.println("Quantity:"+rs.getInt("quantity"));
		}
		if(rs!=null)
			rs.close();
		if(ps!=null)
			ps.close();
	}
	public void addQuantity(Connection con,int productId,int userId) throws SQLException
	{
		String s="SELECT stock FROM products WHERE product_id=?";
		PreparedStatement ps1=con.prepareStatement(s);
		ps1.setInt(1, productId);
		ResultSet check=ps1.executeQuery();
		if(check.next()) {
			int stock = check.getInt("stock");
			if(stock>0) {
				String que="update cart set quantity=quantity+1 where product_id=? and user_id=?";
				PreparedStatement ps=con.prepareStatement(que);
				ps.setInt(1, productId);
				ps.setInt(2, userId);
				int up=ps.executeUpdate();
				if(up==0)
					System.out.println("Sorry!! product did not added");
				else
					System.out.println("Yeah you are abt to buy the product....");
				if(ps!=null)
			        ps.close();
			}
			else
				System.out.println("Out of stock");
		}
		if(check!=null)
			check.close();
		if(ps1!=null)
			ps1.close();
		
	}
	public void minusQuantity(Connection con,int productId,int userId) throws SQLException
	{
	    String check="SELECT quantity FROM cart WHERE product_id=? and user_id=?";
	    PreparedStatement ps1=con.prepareStatement(check);
	    ps1.setInt(1, productId);
	    ps1.setInt(2, userId);
	    ResultSet rs=ps1.executeQuery();
	    if(rs.next())
	    {
	        int quantity=rs.getInt("quantity");
	        if(quantity>1)
	        {
	            String update="UPDATE cart SET quantity=quantity-1 WHERE product_id=? and user_id=?";
	            PreparedStatement ps2=con.prepareStatement(update);
	            ps2.setInt(1, productId);
	            ps2.setInt(2, userId);
	            ps2.executeUpdate();
	            System.out.println("Quantity decreased");
	            if(ps2!=null)
	            	ps2.close();
	        }
	        else
	        {
	            String delete="DELETE FROM cart WHERE product_id=? and user_id=?";
	            PreparedStatement ps3=con.prepareStatement(delete);
	            ps3.setInt(1, productId);
	            ps3.setInt(2, userId);
	            ps3.executeUpdate();
	            System.out.println("Product removed from cart");
	            if(ps3!=null)
	            	ps3.close();
	        }
	    }
	    if(ps1!=null)
	    	ps1.close();
	    if(rs!=null)
	    	rs.close();
	}
	public void removeProduct(Connection con,int userId,int productId) throws SQLException
	{
		String que="delete from cart where product_id=? and user_id=?";
		PreparedStatement ps=con.prepareStatement(que);
		ps.setInt(1, productId);
		ps.setInt(2, userId);
		int del=ps.executeUpdate();
		if(del>0)
			System.out.println("Deleted Done");
		else
			System.out.println("Not available in cart");
		if(ps!=null)
			ps.close();
	}
	public double totalAmount(Connection con,int userId) throws SQLException
	{
		String que="select sum(c.quantity*p.price) as total from cart c join products p on c.product_id=p.product_id where c.user_id=?";
		PreparedStatement ps=con.prepareStatement(que);
		ps.setInt(1, userId);
		ResultSet rs=ps.executeQuery();
		double amt=0.0;
		if(rs.next()) {
			amt=rs.getDouble("total");
			System.out.println("Total Amount:"+amt);
		}
		if(rs!=null)
			rs.close();
		if(ps!=null)
			ps.close();
		return amt;
	}
}
