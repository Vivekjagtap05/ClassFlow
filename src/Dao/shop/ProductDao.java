package Dao.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.shop.Products;
import Dao.DBconnection;

public class ProductDao {

    // Add a product
    public void addProduct(Products p) throws Exception {
        String sql = "INSERT INTO products(name, category, price, quantity_in_stock) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, p.getName());
            pst.setString(2, p.getCategory());
            pst.setDouble(3, p.getPrice());
            pst.setInt(4, p.getQuantity());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all products
    public List<Products> getAllProducts() throws Exception {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DBconnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Products p = new Products(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("quantity_in_stock")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update stock
    public void updateProductStock(int productId, int newQuantity) throws Exception {
        String sql = "UPDATE products SET quantity_in_stock=? WHERE id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, newQuantity);
            pst.setInt(2, productId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
