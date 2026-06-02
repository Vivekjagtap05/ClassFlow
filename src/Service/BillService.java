package Service;

import java.util.List;

import Dao.shop.BillDao;
import Dao.shop.BillDetailsDao;
import model.shop.Bill;
import model.shop.BillDetails;
import model.shop.BillWithDetails;

public class BillService {

    private BillDao billDao = new BillDao();
    private BillDetailsDao detailsDao = new BillDetailsDao();

    // Full bill with items
    public BillWithDetails getCompleteBill(int billId) {
        Bill bill = billDao.getBillById(billId);
        List<BillDetails> items = detailsDao.getDetailsByBillId(billId);
        return new BillWithDetails(bill, items);
    }
}
