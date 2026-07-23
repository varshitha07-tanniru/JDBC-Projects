package EcommerceProject;
import java.sql.*;
public class AddToCart {
    int totalItems = 0;
    int totalPrice = 0;
    void addToCart(String Pro_name, int capacity, Connection con, int user_id) throws SQLException {
        String query = "select price,stock,category_id,product_id from products where product_name=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, Pro_name);
        ResultSet rs = ps.executeQuery();
        int stock = 0, price = 0, cartegory_id = 0, pro_id = 0;
        if (rs.next()) {
            pro_id = rs.getInt("product_id");
            price = rs.getInt("price");
            stock = rs.getInt("stock");
            cartegory_id = rs.getInt("category_id");
            if (capacity <= stock) {
                totalItems += capacity;
                totalPrice = price * capacity;
                // Update product stock
                String Query = "update products set stock=stock-? where product_name=?";
                PreparedStatement ps1 = con.prepareStatement(Query);
                ps1.setInt(1, capacity);
                ps1.setString(2, Pro_name);
                ps1.executeUpdate();
                ps1.close();
                System.out.println("Added to cart");
                // Updating the cart table
                String que = "select * from cart where user_id=? and product_id=? and cart_id=?";
                PreparedStatement ps3 = con.prepareStatement(que);
                ps3.setInt(1, user_id);
                ps3.setInt(2, pro_id);
                ps3.setInt(3, cartegory_id);
                ResultSet rs1 = ps3.executeQuery();
                if (rs1.next()) {
                    String q = "update cart set quantity=quantity+? where user_id=? and product_id=? and cart_id=?";
                    PreparedStatement ps2 = con.prepareStatement(q);
                    ps2.setInt(1, capacity);
                    ps2.setInt(2, user_id);
                    ps2.setInt(3, pro_id);
                    ps2.setInt(4, cartegory_id);
                    ps2.executeUpdate();
                    ps2.close();
                } else {
                    String q = "insert into cart(cart_id,user_id,product_id,quantity) values(?,?,?,?)";
                    PreparedStatement ps2 = con.prepareStatement(q);
                    ps2.setInt(1, cartegory_id);
                    ps2.setInt(2, user_id);
                    ps2.setInt(3, pro_id);
                    ps2.setInt(4, capacity);
                    ps2.executeUpdate();
                    ps2.close();
                }
                rs1.close();
                ps3.close();
            } else {
                System.out.println("Not Enough Stock");
            }
        } else {
            System.out.println("Incorrect Product name");
        }

        if (rs != null)
            rs.close();
        if (ps != null)
            ps.close();
    }
}