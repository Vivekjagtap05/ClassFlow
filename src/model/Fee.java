package model;

import java.util.Date;

public class Fee {

    private int studentId;
    private double amountPaid;
    private double amountPending;
    private String status;
    private Date paymentDate;
    private String addedBy;

    public Fee() {}

    public Fee(int studentId, double amountPaid, double amountPending, String status, Date paymentDate, String addedBy) {
        this.studentId = studentId;
        this.amountPaid = amountPaid;
        this.amountPending = amountPending;
        this.status = status;
        this.paymentDate = paymentDate;
        this.addedBy = addedBy;
    }

    // ===== Getters and Setters =====
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public double getAmountPending() { return amountPending; }
    public void setAmountPending(double amountPending) { this.amountPending = amountPending; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public String getAddedBy() { return addedBy; }
    public void setAddedBy(String addedBy) { this.addedBy = addedBy; }
}
