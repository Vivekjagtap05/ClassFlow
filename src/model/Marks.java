package model;

public class Marks {

    private int studentId;
    private double percentage;
    private String grade;

    public Marks() {}

    public Marks(int studentId, double percentage, String grade) {
        this.studentId = studentId;
        this.percentage = percentage;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
