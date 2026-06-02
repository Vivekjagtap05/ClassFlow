package model.shop;

import java.util.Date;

public class Bill {
    private int id;
    private int studentId;
    private int shopKeeperId;
    private Date billDate;
    private double totalAmount;

    // Constructors
    public Bill() {}

    public Bill(int id, int studentId, int shopKeeperId, Date billDate, double totalAmount) {
        this.id = id;
        this.studentId = studentId;
        this.shopKeeperId = shopKeeperId;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getShopKeeperId() { return shopKeeperId; }
    public void setShopKeeperId(int shopKeeperId) { this.shopKeeperId = shopKeeperId; }

    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    @Override
    public String toString() {
        return "Bill ID: " + id + ", Student ID: " + studentId + ", Total: $" + totalAmount;
    }
}
