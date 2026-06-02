package Dao;

import java.sql.*;
import model.User;

public class LoginDao {

    public static User login(String username, String password) {

        User user = null;

        try {
            Connection con = DBconnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));

                // ✅ Null safe role
                String role = rs.getString("role");
                if (role == null || role.trim().isEmpty()) {
                    role = "UNKNOWN";
                }
                user.setRole(role.trim().toUpperCase());
                
                // ✅ Optional: set id if exists
                try {
                    user.setId(rs.getInt("id"));
                } catch (Exception e) {
                    // ignore
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user; // null only if invalid login
    }
}
