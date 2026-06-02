package ui.shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.shop.BillDetails;
import Dao.shop.BillDetailsDao;

public class BillDetailsFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel model;
    private BillDetailsDao dao = new BillDetailsDao();

    public BillDetailsFrame(List<BillDetails> detailsList) {
        setTitle("Bill Details");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Product ID", "Quantity", "Subtotal"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Load details
        for (BillDetails bd : detailsList) {
            model.addRow(new Object[]{bd.getProductId(), bd.getQuantity(), bd.getSubtotal()});
        }

        setVisible(true);
    }

	public BillDetailsDao getDao() {
		return dao;
	}

	public void setDao(BillDetailsDao dao) {
		this.dao = dao;
	}
}
