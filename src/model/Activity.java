package model;

import java.sql.Timestamp;

public class Activity {

    private int activityId;
    private int studentId;
    private String activityName;
    private String status;
    private String addedBy;
    private Timestamp createdAt;

    public Activity() {}

    // used while inserting
    public Activity(int studentId, String activityName, String status, String addedBy) {
        this.studentId = studentId;
        this.activityName = activityName;
        this.status = status;
        this.addedBy = addedBy;
    }

    // getters & setters
    public int getActivityId() {
        return activityId;
    }
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddedBy() {
        return addedBy;
    }
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
