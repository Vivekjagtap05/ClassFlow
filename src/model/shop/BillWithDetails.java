package model.shop;

import java.util.List;

public class BillWithDetails {

    private Bill bill;
    private List<BillDetails> details;

    public BillWithDetails(Bill bill, List<BillDetails> details) {
        this.bill = bill;
        this.details = details;
    }

    public Bill getBill() {
        return bill;
    }

    public List<BillDetails> getDetails() {
        return details;
    }
}
