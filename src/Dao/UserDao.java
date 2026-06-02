package Dao;

import java.sql.*;
import model.User;

public class UserDao {

    public User login(String username, String password) {

        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT id, username, role FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));          // 🔥 IMPORTANT
                u.setUsername(rs.getString("username"));
                u.setRole(rs.getString("role").toUpperCase());
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // login failed
    }
}
