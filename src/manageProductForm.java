import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class manageProductForm extends JInternalFrame implements ActionListener, MouseListener{
JLabel mainLabel, nameLabel, typeLabel, priceLabel, quantityLabel;
DefaultTableModel productDtm;
JTable productTable;
JScrollPane productScroll;
JTextField nameField;
JComboBox<String> typeCombo;
JSpinner priceSpinner, quantitySpinner;
JButton addButt, updateButt, deleteButt;
JPanel outerPanel, buttPanel, formPanel, mainPanel;
String productType;
Vector<String> typeHolder = new Vector<>();
ResultSet rs1, rs2;
Connector connect = new Connector();
int index = -1;
boolean Ceker = true;

	public manageProductForm() {
	mainLabel = new JLabel("MANAGE PRODUCT");
	mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
	mainLabel.setHorizontalAlignment(JLabel.CENTER);
	mainLabel.setVerticalAlignment(JLabel.CENTER);
	nameLabel = new JLabel("Product Name :");
	typeLabel = new JLabel("Product Type :");
	priceLabel = new JLabel("Product Price :");
	quantityLabel = new JLabel("Product Quantity");
	nameField = new JTextField();
	priceSpinner = new JSpinner(new SpinnerNumberModel(0,0,null,1));
	quantitySpinner = new JSpinner(new SpinnerNumberModel(0,0,null,1));
	rs1 = connect.ExecuteQuery("SELECT * FROM producttype");
	try {
		while(rs1.next()) {
			productType = rs1.getString("ProductTypeName");
			typeHolder.add(productType);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	typeCombo = new JComboBox<String>(typeHolder);
	addButt = new JButton("Add");
	updateButt = new JButton("Update");
	deleteButt = new JButton("Delete");
	addButt.addActionListener(this);
	updateButt.addActionListener(this);
	deleteButt.addActionListener(this);
	String [] head = {"ProductID", "ProductName", "ProductTypeName", "ProductPrice", "ProductQuantity"};
	productDtm = new DefaultTableModel(head, 0);
	productTable = new JTable(productDtm);
	productTable.addMouseListener(this);
	productScroll = new JScrollPane(productTable);
	rs2 = connect.ExecuteQuery("SELECT * FROM product p JOIN producttype pt ON p.ProductTypeID = pt.ID");
	try {
		while (rs2.next()) {
		int productId = rs2.getInt("p.ID");
		String productName = rs2.getString("ProductName");
		String productTypeName = rs2.getString("ProductTypeName");
		int productPrice = rs2.getInt("p.ProductPrice");
		int productQuantity = rs2.getInt("p.ProductQuantity");
		Object [] data = {productId, productName, productTypeName, productPrice, productQuantity};
		productDtm.addRow(data);
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	buttPanel = new JPanel(new FlowLayout());
	buttPanel.add(addButt);
	buttPanel.add(updateButt);
	buttPanel.add(deleteButt);
	formPanel = new JPanel(new GridLayout(4,2));
	formPanel.add(nameLabel);
	formPanel.add(nameField);
	formPanel.add(typeLabel);
	formPanel.add(typeCombo);
	formPanel.add(priceLabel);
	formPanel.add(priceSpinner);
	formPanel.add(quantityLabel);
	formPanel.add(quantitySpinner);
	mainPanel = new JPanel(new GridLayout(2,1));
	mainPanel.add(productScroll);
	mainPanel.add(formPanel);
	outerPanel = new JPanel(new BorderLayout());
	outerPanel.add(mainLabel, BorderLayout.NORTH);
	outerPanel.add(mainPanel, BorderLayout.CENTER);
	outerPanel.add(buttPanel, BorderLayout.SOUTH);
	mainLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
	mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
	buttPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 80, 60));
	
	add(outerPanel);
	setSize(new Dimension(900,700));
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		nameField.setText(productTable.getValueAt(productTable.getSelectedRow(), 1).toString());
		typeCombo.setSelectedItem(productTable.getValueAt(productTable.getSelectedRow(), 2));
		priceSpinner.setValue(productTable.getValueAt(productTable.getSelectedRow(), 3));
		quantitySpinner.setValue(productTable.getValueAt(productTable.getSelectedRow(), 4));
		index = productTable.getSelectedRow();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButt) {
			Ceker = true;
			if (nameField.getText().length() < 5 || nameField.getText().length()>15) {
				JOptionPane.showMessageDialog(null, "Name length must be between 5 and 15", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			if (typeCombo.equals(null)) {
				JOptionPane.showMessageDialog(null, "Product Type Must Be Choose", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			if (Integer.parseInt(priceSpinner.getValue().toString()) <= 0) {
				JOptionPane.showMessageDialog(null, "Price must be more than 0", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			if (Integer.parseInt(quantitySpinner.getValue().toString()) <= 0) {
				JOptionPane.showMessageDialog(null, "Quantity must be more than 0", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			
			if (Ceker) {
			int ptId = typeCombo.getSelectedIndex()+1;
			String pName = nameField.getText();
			int pPrice = Integer.parseInt(priceSpinner.getValue().toString());
			int pQuantity = Integer.parseInt(quantitySpinner.getValue().toString());
			String pType = typeCombo.getSelectedItem().toString();
			Object[] data = {ptId, pName, pType, pPrice, pQuantity};
			connect.ExecuteUpdate("INSERT INTO product VALUES (NULL, "+ptId+", '"+pName+"', "+pPrice+", "+pQuantity+")");
			JOptionPane.showMessageDialog(null, "Product Added", "Message", JOptionPane.INFORMATION_MESSAGE, null);
			productDtm.addRow(data);
		}
		}
		
		if (e.getSource() == updateButt) {
			Ceker = true;
			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Select The product first!", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			if (nameField.getText().length() < 5 || nameField.getText().length()>15) {
				JOptionPane.showMessageDialog(null, "Name length must be between 5 and 15", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			if (typeCombo.equals(null)) {
				JOptionPane.showMessageDialog(null, "Product Type Must Be Choose", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			if (Integer.parseInt(priceSpinner.getValue().toString()) <= 0) {
				JOptionPane.showMessageDialog(null, "Price must be more than 0", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			if (Integer.parseInt(quantitySpinner.getValue().toString()) <= 0) {
				JOptionPane.showMessageDialog(null, "Quantity must be more than 0", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			
			if (Ceker) {
				int pId = Integer.parseInt(productTable.getValueAt(productTable.getSelectedRow(), 0).toString());
				System.out.println(pId);
				int ptId = typeCombo.getSelectedIndex()+1;
				String pName = nameField.getText();
				int pPrice = Integer.parseInt(priceSpinner.getValue().toString());
				int pQuantity = Integer.parseInt(quantitySpinner.getValue().toString());
				String pType = typeCombo.getItemAt(index);
				connect.ExecuteUpdate("UPDATE product SET ProductTypeID = "+ptId+", ProductName = '"+pName+"', ProductPrice = "+pPrice+", ProductQuantity = "+pQuantity+" WHERE product.ID  = "+pId+"");
				productDtm.fireTableDataChanged();
				JOptionPane.showMessageDialog(null, "Product Updated", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				productDtm.setValueAt(pName, index, 1);
				productDtm.setValueAt(pType, index, 2);
				productDtm.setValueAt(pPrice, index, 3);
				productDtm.setValueAt(pQuantity, index, 4);
			}
			
		}
		
		if (e.getSource() == deleteButt) {
			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Select The product first!", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				Ceker = false;
			}
			if(Ceker) {
			int Id = Integer.parseInt(productTable.getValueAt(index, 0).toString());
			connect.ExecuteUpdate("DELETE FROM product WHERE ID = "+Id+"");
			JOptionPane.showMessageDialog(null, "Product Deleted", "Message", JOptionPane.INFORMATION_MESSAGE, null);
			productDtm.removeRow(index);
			}
			
		}
		
	}

}
