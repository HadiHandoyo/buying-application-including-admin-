import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;


public class manageProductType extends JInternalFrame implements ActionListener, MouseListener{
JLabel mainLabel, typeLabel;
JTable mainTable;
DefaultTableModel mainDtm;
JScrollPane mainScroll;
JTextField typeField;
JButton addButt, updateButt, deleteButt;
JPanel mainPanel, typePanel, buttonPanel, outerPanel;
ResultSet rs;
int index= 0, newId;
Connector connect = new Connector();
	public manageProductType() {
	mainLabel = new JLabel("MANAGE PRODUCT TYPE");
	mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
	mainLabel.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
	mainLabel.setHorizontalAlignment(JLabel.CENTER);
	mainLabel.setVerticalAlignment(JLabel.CENTER);
	String[] head = {"ProductTypeID", "ProductTypeName"};
	mainDtm = new DefaultTableModel(head, 0);
	mainTable = new JTable(mainDtm);
	mainScroll = new JScrollPane(mainTable);
	rs = connect.ExecuteQuery("SELECT * FROM producttype");
	try {
		while (rs.next()) {
		int productTypeId = rs.getInt("ID");
		String productTypeName = rs.getString("ProductTypeName");
		Object [] data = {productTypeId, productTypeName};
		mainDtm.addRow(data);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	mainTable.addMouseListener(this);
	typeLabel = new JLabel("Type");
	typeField = new JTextField();
	typeField.setPreferredSize(new Dimension(20,20));
	addButt = new JButton("Add");
	addButt.addActionListener(this);
	updateButt = new JButton("Update");
	updateButt.addActionListener(this);
	deleteButt = new JButton("Delete");
	deleteButt.addActionListener(this);
	typePanel = new JPanel(new BorderLayout());
	typePanel.add(typeLabel, BorderLayout.WEST);
	typePanel.add(typeField, BorderLayout.CENTER);
	mainPanel = new JPanel(new BorderLayout());
	mainPanel.add(mainScroll, BorderLayout.NORTH);
	mainPanel.add(typePanel, BorderLayout.CENTER);
	buttonPanel = new JPanel(new FlowLayout());
	buttonPanel.add(addButt);
	buttonPanel.add(updateButt);
	buttonPanel.add(deleteButt);
	mainScroll.setPreferredSize(new Dimension(500,200));
	typePanel.setBorder(BorderFactory.createEmptyBorder(40,400,70,400));
	buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,240,0));
	outerPanel = new JPanel(new BorderLayout());
	outerPanel.add(mainLabel, BorderLayout.NORTH);
	outerPanel.add(mainPanel, BorderLayout.CENTER);
	outerPanel.add(buttonPanel, BorderLayout.SOUTH);
	add(outerPanel);
	setSize(new Dimension(1200,800));
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButt) {
			String typeTemp = typeField.getText();
			rs = connect.ExecuteQuery("SELECT MAX(ID) FROM producttype");
			try {
				if (rs.next()) {
					newId = rs.getInt("MAX(ID)")+1;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Object[] dataInput = {newId, typeTemp};
			mainDtm.addRow(dataInput);
			
			connect.ExecuteUpdate("INSERT INTO producttype VALUES ('"+newId+"', '"+typeTemp+"')");
		}
		
		if (e.getSource() == updateButt) {
			String id = mainTable.getValueAt(index, 0).toString();
			String tempType = typeField.getText();
			mainTable.setValueAt(tempType, index, 1);
			connect.ExecuteUpdate("UPDATE producttype SET ProductTypeName = '"+tempType+"' WHERE ID = "+id+"");
		}
		
		if (e.getSource() == deleteButt) {
			String id = mainTable.getValueAt(index, 0).toString();
			mainDtm.removeRow(index);
			connect.ExecuteUpdate("DELETE FROM producttype WHERE ID = "+id+"");
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		typeField.setText(mainTable.getValueAt(mainTable.getSelectedRow(), 1).toString());
		index = mainTable.getSelectedRow();
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
