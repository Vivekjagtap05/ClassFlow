package Dao;

import java.sql.*;
import java.util.*;
import model.Student;

public class StudentDao {

    // 🔹 INSERT STUDENT
    public boolean insertStudent(Student s) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students (id, name, department) VALUES (?,?,?)"
            );
            ps.setInt(1, s.getId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDepartment());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 GET ALL STUDENTS (ADMIN / TEACHER)
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();

        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM students");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔹 UPDATE STUDENT
    public boolean updateStudent(Student s) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE students SET name=?, department=? WHERE id=?"
            );
            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setInt(3, s.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 DELETE STUDENT
    public boolean deleteStudent(int id) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM students WHERE id=?"
            );
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 GET STUDENT BY ID (STUDENT LOGIN)
    public Student getStudentById(int id) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM students WHERE id=?"
            );
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 SMART SEARCH FILTER
    public List<Student> filterStudents(String filter, String value) {

        List<Student> list = new ArrayList<>();

        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = null;

            if (filter.equalsIgnoreCase("ID")) {
                ps = con.prepareStatement(
                    "SELECT * FROM students WHERE id=?"
                );
                ps.setInt(1, Integer.parseInt(value));

            } else if (filter.equalsIgnoreCase("NAME")) {
                ps = con.prepareStatement(
                    "SELECT * FROM students WHERE name LIKE ?"
                );
                ps.setString(1, "%" + value + "%");

            } else if (filter.equalsIgnoreCase("DEPARTMENT")) {
                ps = con.prepareStatement(
                    "SELECT * FROM students WHERE department LIKE ?"
                );
                ps.setString(1, "%" + value + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
