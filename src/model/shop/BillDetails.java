package model.shop;

public class BillDetails {
    private int id;
    private int billId;
    private int productId;
    private int quantity;
    private double subtotal;

    // Constructors
    public BillDetails() {}

    public BillDetails(int id, int billId, int productId, int quantity, double subtotal) {
        this.id = id;
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Qty: " + quantity + ", Subtotal: $" + subtotal;
    }
}
