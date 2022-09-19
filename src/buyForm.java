import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.PageAttributes.PrintQualityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

public class buyForm extends JInternalFrame implements ActionListener, MouseListener{
JLabel mainLabel, qtyLabel, paymentLabel, productLabel, cartLabel;
JSpinner quantitySpinner;
JRadioButton Cash, nonCash;
JTable productTable, cartTable;
DefaultTableModel productDtm, cartDtm;
JButton addButt, coButt;
ButtonGroup paymentGroup;
JPanel outerPanel, mainPanel, upperPanel, lowerPanel, buttPanel, radioButtPanel, paymentPanel;
JScrollPane cartScroll, productScroll;
Vector<Object> isianCart = new Vector<Object>();
ResultSet rs = null;
Connector connect = new Connector();
String productId, productName, productType;
int productPrice, quantity;
JDesktopPane desktopPane;
HashMap< String, Integer> quantCek = new HashMap<>();
boolean coCek = true;
int quantityTemp = 0, userId=0;

	public buyForm(int userId) {
		super("Buy Form", true, true, true);
		this.userId = userId;
	rs = connect.ExecuteQuery("SELECT * FROM product");
	try {
		while (rs.next()) {
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String[] head = {"ProductID", "ProductName", "ProductType", "ProductPrice", "ProductQty"};
	mainLabel = new JLabel("BUY PRODUCT");
	mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
	mainLabel.setHorizontalAlignment(JLabel.CENTER);
	mainLabel.setVerticalAlignment(JLabel.CENTER);
	qtyLabel = new JLabel("Quantity :");
	paymentLabel = new JLabel("Payment Type :");
	quantitySpinner = new JSpinner(new SpinnerNumberModel(0,0,null,1));
	Cash = new JRadioButton("Cash");
	Cash.setActionCommand("Cash");
	nonCash = new JRadioButton("Debit / Credit");
	nonCash.setActionCommand("Debit/Credit");
	paymentGroup = new ButtonGroup();
	paymentGroup.add(Cash);
	paymentGroup.add(nonCash);
	productLabel = new JLabel("Product");
	cartLabel = new JLabel("Cart");
	cartDtm = new DefaultTableModel(head, 0);
	cartTable = new JTable();
	cartTable.setModel(cartDtm);
	cartScroll = new JScrollPane(cartTable);
	productLabel = new JLabel("Product");
	productDtm = new DefaultTableModel(head, 0);
	rs = connect.ExecuteQuery("SELECT product.ID, ProductName, ProductTypeName, ProductPrice, ProductQuantity "
			+ "FROM `product` JOIN producttype ON product.ProductTypeID = producttype.ID;");
	try {
		while(rs.next()){
		int Id = rs.getInt("product.ID");
		String ProductTypeName = rs.getString("ProductTypeName");
		String ProductName = rs.getString("ProductName");
		int ProductPrice = rs.getInt("ProductPrice");
		int ProductQuantity = rs.getInt("ProductQuantity");
		Object [] data = {Id, ProductName, ProductTypeName, ProductPrice, ProductQuantity};
		productDtm.addRow(data);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	productTable = new JTable(productDtm);
	productScroll = new JScrollPane(productTable);
	addButt = new JButton("Add to cart");
	addButt.addActionListener(this);
	coButt = new JButton("Check out");
	coButt.addActionListener(this);
	addButt.setPreferredSize(new Dimension(10,10));
	buttPanel = new JPanel(new GridLayout(1,2));
	buttPanel.add(addButt);
	buttPanel.add(coButt);
	paymentPanel = new JPanel(new FlowLayout());
	paymentPanel.add(Cash);
	paymentPanel.add(nonCash);
	upperPanel = new JPanel(new GridLayout(2,2));
	lowerPanel = new JPanel(new GridLayout(2,2));
	mainLabel.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
	buttPanel.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
	upperPanel.add(qtyLabel);
	upperPanel.add(quantitySpinner);
	upperPanel.add(paymentLabel);
	upperPanel.add(paymentPanel);
	lowerPanel.add(productLabel);
	lowerPanel.add(cartLabel);	
	lowerPanel.add(productScroll);
	lowerPanel.add(cartScroll);	
	mainPanel = new JPanel(new BorderLayout());
	outerPanel = new JPanel(new BorderLayout());
	mainPanel.add(upperPanel, BorderLayout.NORTH);
	mainPanel.add(lowerPanel, BorderLayout.CENTER);
	outerPanel.add(mainLabel, BorderLayout.NORTH);
	outerPanel.add(buttPanel, BorderLayout.SOUTH);
	outerPanel.add(mainPanel, BorderLayout.CENTER);
	add(outerPanel);
	setSize(900,700);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setVisible(true);
	userId = firstForm.Id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButt) {
			System.out.println(userId);
		if(productTable.getSelectedRow() == -1) {
		JOptionPane.showMessageDialog(null, "You must choose a row first", "Message", JOptionPane.INFORMATION_MESSAGE, null);
		}else {
		int quantity = 0;
		String ProductId = productTable.getValueAt(productTable.getSelectedRow(), 0).toString();
		String ProductTypeName = productTable.getValueAt(productTable.getSelectedRow(), 2).toString();
		String ProductName = productTable.getValueAt(productTable.getSelectedRow(), 1).toString();
		String ProductPrice = productTable.getValueAt(productTable.getSelectedRow(), 3).toString();
		int ProductQuantity = Integer.parseInt(productTable.getValueAt(productTable.getSelectedRow(), 4).toString());
		quantity = Integer.parseInt(quantitySpinner.getValue()+"");
		if(quantity > ProductQuantity) {
			JOptionPane.showMessageDialog(null, "Not enough item to be bought", "Message", JOptionPane.INFORMATION_MESSAGE, null);
		}else{if(quantity == 0){
			JOptionPane.showMessageDialog(null, "Quantity can't be 0", "Message", JOptionPane.INFORMATION_MESSAGE, null);
		}else {
			int productQuant = 0;
			for(int i = 0; i < cartTable.getRowCount(); i++) {
			if(cartTable.getValueAt(i, 0).equals(ProductId)) {
				productQuant += Integer.parseInt(cartTable.getValueAt(i, 4).toString());
			}
			}
			productQuant += quantity;
//			quantityTemp = Integer.parseInt(productTable.getValueAt(productTable.getSelectedRow(),4).toString());
//			System.out.println(quantityTemp);
//			quantityTemp-=quantity;
//			System.out.println(quantityTemp);
			if (productQuant > Integer.parseInt(productTable.getValueAt(productTable.getSelectedRow(),4).toString())) {
				JOptionPane.showMessageDialog(null, "Not enough item to be bought", "Message", JOptionPane.INFORMATION_MESSAGE, null);
			}else {
				int quantities = 0;
				boolean ceker = false;
				int index = -1;
				for(int i = 0 ; i<cartTable.getRowCount(); i++) {
					if (cartTable.getValueAt(i, 0).equals(ProductId)) {
					ceker = true;
					index = i;
					quantities = Integer.parseInt(cartTable.getValueAt(i, 4).toString());
					break;
					}
				}
				if (ceker) {
				int quantAfter = quantities + quantity;
				cartTable.setValueAt(quantAfter, index, 4);
				}else {
					Object [] cartData = {ProductId, ProductName,ProductTypeName, ProductPrice, quantity};
					cartDtm.addRow(cartData);					
				}
			}
		}
		}
		}
		}
		
		if (e.getSource() == coButt) {
			rs = connect.ExecuteQuery("SELECT MAX(ID) FROM headertransaction");
			int Id = 0;
			try {
				if(rs.next()) {
				Id = rs.getInt("MAX(ID)")+1;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		System.out.println(Id);
			coCek = true;
		if (paymentGroup.getSelection() == null) {
			JOptionPane.showMessageDialog(null, "Payment Type Must Be Choosen", "Message", JOptionPane.INFORMATION_MESSAGE, null);
			coCek = false;
		}
		if (cartTable.getRowCount() == -1) {
			JOptionPane.showMessageDialog(null, "Cart Is Empty", "Message", JOptionPane.INFORMATION_MESSAGE, null);
			coCek = false;
		}
//		for (int i = 0; i < cartTable.getRowCount(); i++) {
//			quantCek.put(cartTable.getValueAt(i, 1).toString(), quantCek.getOrDefault(cartTable.getValueAt(i, 1).toString(),0));
//			System.out.println(quantCek.get(cartTable.getValueAt(i, 0))); 
//		}
//		
//		for (Map.Entry<String, Integer> entry : quantCek.entrySet()) {
//			String key = entry.getKey();
//			Integer value = entry.getValue();
//			
//			value++;
//		}
		if (coCek == true) {
			int Option = JOptionPane.showConfirmDialog(null, "Are you sure want to check out?", "Select an option", JOptionPane.YES_NO_CANCEL_OPTION);
			if (Option == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null, "Transaction Completed", "Message", JOptionPane.INFORMATION_MESSAGE, null);
			int userId = this.userId;
			Date CurrentDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String datesql = format.format(CurrentDate);
			String paymentType = paymentGroup.getSelection().getActionCommand();
			connect.ExecuteUpdate("INSERT INTO headertransaction VALUES('"+Id+"','"+userId+"','"+datesql+"', '"+paymentType+"')");
			for (int i = 0; i < cartTable.getRowCount(); i++) {
			int productId = Integer.parseInt(cartTable.getValueAt(i, 0).toString());
			String productName = cartTable.getValueAt(i, 1).toString();
			String productType = cartTable.getValueAt(i, 2).toString();
			int productPrice = Integer.parseInt(cartTable.getValueAt(i, 3).toString());
			int productQuantity = Integer.parseInt(cartTable.getValueAt(i, 4).toString());
			connect.ExecuteUpdate("INSERT INTO detailtransaction VALUES ("+Id+", "+productId+", "+productQuantity+")");
			int index = -1;
			for (int j = 0; j <productTable.getRowCount(); j++) {
			if(productTable.getValueAt(j, 0).equals(productId)) {
				index = j;
				break;
			}
			}
				int quantityAfter = Integer.parseInt(productTable.getValueAt(index, 4).toString()) - productQuantity;
				connect.ExecuteUpdate("UPDATE product SET ProductQuantity = "+quantityAfter+" WHERE ID = "+productId+"");
				productTable.setValueAt(quantityAfter, index, 4);
			}
			DefaultTableModel dtm = (DefaultTableModel) cartTable.getModel();
			while(cartTable.getRowCount() != 0){
			dtm.removeRow(0);
			}
			}
		}
		}
		}
		

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
