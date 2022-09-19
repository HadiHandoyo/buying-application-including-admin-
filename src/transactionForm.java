import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

public class transactionForm extends JInternalFrame implements ActionListener {
JLabel mainLabel, htLabel, dtLabel, totalLabel;
JTable htTable, dtTable;
DefaultTableModel htDtm, dtDtm;
JScrollPane htScroll, dtScroll;
JTextField totText;
JButton checkButt;
JPanel totalPanel, innerPanel, mainPanel, upperPanel, lowerPanel;
ResultSet rs;
Connector connect = new Connector();

	public transactionForm() {
		super();
		String [] htHead = {"TransactionID", "Date of Transaction", "PaymentType"}; 
		String[] dtHead = {"TransactionID", "ProductName", "ProductType", "Quantity"};
		mainLabel = new JLabel("TRANSACTION");
		mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
		htLabel = new JLabel("Header Transaction");
		dtLabel = new JLabel("Detail Transaction");
		totalLabel = new JLabel("Total");
		totText = new JTextField();
		checkButt = new JButton("Check");
		checkButt.addActionListener(this);
		htDtm = new DefaultTableModel(htHead, 0);
		dtDtm = new DefaultTableModel(dtHead, 0);
		rs = connect.ExecuteQuery("SELECT * FROM headertransaction");
		try {
			while(rs.next()){
			int Id = rs.getInt("ID");
			String TransactionDate = rs.getString("TransactionDate");
			String PaymentType = rs.getString("PaymentType");
			Object [] data = {Id, TransactionDate, PaymentType};
			htDtm.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		htTable = new JTable(htDtm);
		dtTable = new JTable(dtDtm);
		htScroll = new JScrollPane(htTable);
		dtScroll = new JScrollPane(dtTable);
		totalPanel = new JPanel(new FlowLayout());
		upperPanel = new JPanel(new BorderLayout());
		lowerPanel = new JPanel(new BorderLayout());
		innerPanel = new JPanel(new BorderLayout());
		mainPanel = new JPanel(new BorderLayout());
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setVerticalAlignment(JLabel.CENTER);
		mainLabel.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
		totalPanel.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
		totText.setPreferredSize(new Dimension(100,40));
		htScroll.setPreferredSize(new Dimension(200,183));
		dtScroll.setPreferredSize(new Dimension(200,183));
		totalPanel.add(totalLabel);
		totalPanel.add(totText);
		totalPanel.add(checkButt);
		upperPanel.add(htLabel, BorderLayout.NORTH);
		upperPanel.add(htScroll, BorderLayout.CENTER);
		lowerPanel.add(dtLabel, BorderLayout.NORTH);
		lowerPanel.add(dtScroll, BorderLayout.CENTER);
		innerPanel.add(upperPanel, BorderLayout.NORTH);
		innerPanel.add(lowerPanel, BorderLayout.CENTER);
		mainPanel.add(mainLabel, BorderLayout.NORTH);
		mainPanel.add(innerPanel, BorderLayout.CENTER);
		mainPanel.add(totalPanel, BorderLayout.SOUTH);
		add(mainPanel);
		setSize(900,700);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == checkButt) {
			System.out.println("test");
			while(dtDtm.getRowCount() != 0) {
				dtDtm.removeRow(0);
			}
			int tempTotal = 0; 
			int index = htTable.getSelectedRow();
			String transactionId = htTable.getValueAt(index, 0).toString();
			System.out.println(transactionId);
			rs = connect.ExecuteQuery("SELECT * FROM detailtransaction dt JOIN product  p ON dt.ProductID = p.ID JOIN producttype pt ON p.ProductTypeID = pt.ID WHERE dt.TransactionID = '"+transactionId+"'");
			try {
				while(rs.next()) {
					System.out.println();
			String productName = rs.getString("ProductName");
			String productType = rs.getString("ProductTypeName");
			int quantity = rs.getInt("dt.Quantity");
			int price = rs.getInt("p.ProductPrice");
			tempTotal += (price*quantity);
			Object [] dtData = {transactionId, productName, productType, quantity};
			dtDtm.addRow(dtData);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			totText.setText(String.valueOf(tempTotal));
			totText.setEnabled(false);
	}
	}
		

}
