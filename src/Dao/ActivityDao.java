package Dao;

import java.sql.*;
import java.util.*;
import model.Activity;

public class ActivityDao {

    public boolean insertActivity(Activity a) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO activities (student_id, activity_name, status, added_by) VALUES (?,?,?,?)"
            );

            ps.setInt(1, a.getStudentId());
            ps.setString(2, a.getActivityName());
            ps.setString(3, a.getStatus());
            ps.setString(4, a.getAddedBy());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Activity> getAllActivities() {
        List<Activity> list = new ArrayList<>();

        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM activities ORDER BY created_at DESC"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Activity a = new Activity();
                a.setActivityId(rs.getInt("activity_id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setActivityName(rs.getString("activity_name"));
                a.setStatus(rs.getString("status"));
                a.setAddedBy(rs.getString("added_by"));
                a.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // STUDENT ONLY
    public List<Activity> getActivitiesByStudentId(int studentId) {
        List<Activity> list = new ArrayList<>();

        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM activities WHERE student_id=?"
            );
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Activity a = new Activity();
                a.setActivityId(rs.getInt("activity_id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setActivityName(rs.getString("activity_name"));
                a.setStatus(rs.getString("status"));
                a.setAddedBy(rs.getString("added_by"));
                a.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
