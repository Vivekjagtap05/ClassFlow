package model;

public class Attendance {

    private int studentId;
    private int totalDays;
    private int presentDays;

    public Attendance(int studentId, int totalDays, int presentDays) {
        this.studentId = studentId;
        this.totalDays = totalDays;
        this.presentDays = presentDays;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getPresentDays() {
        return presentDays;
    }
}
