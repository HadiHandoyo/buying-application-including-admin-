import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
//
//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class registForm extends JFrame implements ActionListener{
JPanel mainPanel, genderPanel, formPanel, registerPanel, headPanel, buttPanel;
JLabel registerLabel, nameLabel, emailLabel, passwordLabel, phoneLabel, genderLabel;
JTextField nameField, emailField, phoneField;
JPasswordField passwordField;
JRadioButton male, female;
JButton submitButt, backButt;
ButtonGroup genderGroup;
Connector connect = new Connector();
ResultSet rs;
int maxID, indexakeong, dotCounter, akeongCounter;
boolean InsertCek = true, cekPwDigit = false, cekPwAlpha = false;
int indexPw = 0;

	public registForm() {
	registerLabel = new JLabel("REGISTER");
	registerLabel.setFont(new Font("Arial", Font.BOLD, 20));
	registerLabel.setHorizontalAlignment(JLabel.CENTER);
	registerLabel.setVerticalAlignment(JLabel.CENTER);
	nameLabel = new JLabel("Name :");
	emailLabel = new JLabel("Email :");
	passwordLabel = new JLabel("Password :");
	phoneLabel = new JLabel("Phone Number :");
	genderLabel = new JLabel("Gender :");
	nameField = new JTextField();
	emailField = new JTextField();
	passwordField = new JPasswordField();
	phoneField = new JTextField();
	male = new JRadioButton("Male");
	male.setActionCommand("Male");
	female = new JRadioButton("Female");
	female.setActionCommand("Female");
	backButt = new JButton("<<<");
	backButt.setHorizontalAlignment(JButton.LEFT);
	genderGroup = new ButtonGroup();
	submitButt = new JButton("Submit");
	mainPanel = new JPanel(new BorderLayout());
	genderPanel = new JPanel(new FlowLayout());
	formPanel = new JPanel(new GridLayout(5,2));
	headPanel = new JPanel(new BorderLayout());
	registerPanel = new JPanel(new BorderLayout());
	buttPanel = new JPanel(new BorderLayout());
	submitButt.addActionListener(this);
	backButt.addActionListener(this);
	submitButt.setPreferredSize(new Dimension(80,30));
	buttPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 40, 150));
	buttPanel.add(submitButt, BorderLayout.CENTER);
	registerPanel.add(registerLabel, BorderLayout.CENTER);
	headPanel.add(backButt, BorderLayout.WEST);
	headPanel.add(registerPanel, BorderLayout.CENTER);
	genderPanel.add(male);
	genderPanel.add(female);
	genderGroup.add(male);
	genderGroup.add(female);
	formPanel.add(nameLabel);
	formPanel.add(nameField);
	formPanel.add(emailLabel);
	formPanel.add(emailField);
	formPanel.add(passwordLabel);
	formPanel.add(passwordField);
	formPanel.add(phoneLabel);
	formPanel.add(phoneField);
	formPanel.add(genderLabel);
	formPanel.add(genderPanel);	
	registerLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
	registerPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 140));
	formPanel.setBorder(BorderFactory.createEmptyBorder(80, 30, 80, 60));
	
	
	mainPanel.add(headPanel, BorderLayout.NORTH);
	mainPanel.add(formPanel, BorderLayout.CENTER);
	mainPanel.add(buttPanel, BorderLayout.SOUTH);

	add(mainPanel);
	setSize(new Dimension(400,500));
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setVisible(true);
	setTitle("Stophee");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButt) {
			firstForm home = new firstForm();
			home.setVisible(true);
			this.setVisible(false);
		}
		
		if (e.getSource() == submitButt) {
			indexakeong = emailField.getText().indexOf("@");
			
			if (nameField.getText().length()<3 || nameField.getText().length()>30) {
				JOptionPane.showMessageDialog(null,"Name length must be between 3-30","Message",JOptionPane.INFORMATION_MESSAGE,null);
				InsertCek = false;
			}
			
			if (!(emailField.getText().contains("@")) || !(emailField.getText().contains("."))) {
				JOptionPane.showMessageDialog(null, "Email must contains @ and ends with .com","Message", JOptionPane.INFORMATION_MESSAGE, null);
				InsertCek = false;
			}else {
			
			for (int i = indexakeong; i < emailField.getText().length(); i++) {
				if (emailField.getText().charAt(i) == '.') {
					dotCounter++;
				}
			}
			
			for (int i = 0; i < emailField.getText().length(); i++) {
				if (emailField.getText().charAt(i) == '@') {
					akeongCounter++;
				}
			}
			
			rs = connect.ExecuteQuery("SELECT MAX(ID) FROM users");
			try {
				if (rs.next()) {
					maxID = rs.getInt("MAX(ID)");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (emailField.getText().charAt(indexakeong-1) == '.' || dotCounter != 1 || akeongCounter != 1 
					|| emailField.getText().startsWith("@") || emailField.getText().startsWith(".") || emailField.getText().endsWith("@")
					|| emailField.getText().endsWith(".") || !(emailField.getText().endsWith(".com"))) {
				JOptionPane.showMessageDialog(null, "Email must contains @ and ends with .com","Message", JOptionPane.INFORMATION_MESSAGE, null);
				InsertCek = false;
			}
			
			
			
			dotCounter = 0;
			akeongCounter = 0;
			
			}
			
			if (phoneField.getText().equals("") || phoneField.getText().length()<12 || phoneField.getText().length()>15) {
				JOptionPane.showMessageDialog(null, "Phone can't be empty & Numeric","Message", JOptionPane.INFORMATION_MESSAGE, null);
				InsertCek = false;
			}else {
			for (int i = 0; i < phoneField.getText().length(); i++) {
				if (!(Character.isDigit((phoneField.getText().charAt(i))))) {
					JOptionPane.showMessageDialog(null, "Phone can't be empty & Numeric","Message", JOptionPane.INFORMATION_MESSAGE, null);
					InsertCek = false;
				}
			}
			}
			
			for (int i = 0; i < passwordField.getPassword().length; i++) {
				  int ascii = (int)passwordField.getPassword()[i];
		            if (ascii >= '0' && ascii <= '9') {
		                cekPwDigit = true;
		                break;
			}
			}
		            
		    for (int i = 0; i < passwordField.getPassword().length; i++) {
				int ascii = (int)passwordField.getPassword()[i];
				   if (ascii >= 'a' && ascii <= 'z') {
		                cekPwAlpha = true;
		                break;
		            }
		    }
			
			if ((passwordField.getPassword().length<5 || passwordField.getPassword().length>20) || !cekPwDigit || !cekPwAlpha) {
				JOptionPane.showMessageDialog(null, "Password length must be 5-20 characters", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				cekPwAlpha = false;
				cekPwDigit = false;
				InsertCek = false;
			}
			
			if (genderGroup.getSelection() == null) {
				JOptionPane.showMessageDialog(null, "Gender must be chosen", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				InsertCek = false;
			}
			
			if (InsertCek == true) {
				JOptionPane.showMessageDialog(null, "Account created", "Message", JOptionPane.INFORMATION_MESSAGE, null);
				connect.ExecuteUpdate("INSERT INTO users VALUES('"+(int)(maxID+1)+"', '"+nameField.getText()+"', '"+emailField.getText()+"', '"+String.valueOf(passwordField.getPassword())+"', '"+phoneField.getText()+"', '"+genderGroup.getSelection().getActionCommand()+"', 'user')");	
			}
		
		
	}

}
}
