import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class firstForm extends JFrame implements ActionListener{
	boolean cek1 = false;
	boolean cek2 = false;
	boolean Cek = true;
JLabel loginLabel;
JLabel emailLabel;
JLabel passwordLabel;
JTextField emailField;
JPasswordField passwordField;
JButton loginButt;
JButton CreateButt;
JPanel mainPanel;
JPanel innerPanel;
JPanel buttPanel;
JPanel loginPanel;
Font Font;
Connector connect = new Connector();
ResultSet rs;
String EmailCek = "";
String PassCek = "";
String fEmail = "", fPassword = "";
protected static int Id;


	public firstForm() {
		loginLabel = new JLabel("LOGIN");
		Font = new Font("Arial", Font.BOLD, 30);
		loginLabel.setFont(Font);
		loginLabel.setHorizontalAlignment(JLabel.CENTER);
		loginLabel.setVerticalAlignment(JLabel.CENTER);
		emailLabel = new JLabel("Email : ");
		passwordLabel = new JLabel("Password : ");
		emailField = new JTextField(20);
		passwordField = new JPasswordField(20);
		loginButt = new JButton("Login");
		CreateButt = new JButton("Create Account");
		mainPanel = new JPanel(new BorderLayout());
		innerPanel = new JPanel(new GridLayout(2,2));
		buttPanel = new JPanel(new FlowLayout());
		loginPanel = new JPanel(new BorderLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
		loginPanel.setBorder(BorderFactory.createEmptyBorder(80, 20, 0, 20));
		buttPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
		loginButt.addActionListener(this);
		CreateButt.addActionListener(this);
		loginPanel.add(loginLabel);
		innerPanel.add(emailLabel);
		innerPanel.add(emailField);
		innerPanel.add(passwordLabel);
		innerPanel.add(passwordField);
		buttPanel.add(loginButt);
		buttPanel.add(CreateButt);
		mainPanel.add(loginPanel, BorderLayout.NORTH);
		mainPanel.add(innerPanel, BorderLayout.CENTER);
		mainPanel.add(buttPanel, BorderLayout.SOUTH);
		add(mainPanel);		
		
		setSize(400,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("Stophee");
	
//		 try {
//		rs = connect.ExecuteQuery("SELECT Email FROM users WHERE Email = '"+emailField.getText()+"'");
//			if(rs.next()) {
//				cek1 = true;
//				System.out.println("ok");
//			}else {
//				cek1 = false;
//				System.out.println("sds");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	    
//		 try {
//	    rs = connect.ExecuteQuery("SELECT Password FROM users WHERE Email = '"+emailField.getText()+"'");
//			if (rs.next()) {
//				cek2= true;
//				System.out.println("ok");
//			}else {
//				cek2=false;
//				System.out.println("sds");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButt) {
					 rs = connect.ExecuteQuery("SELECT * FROM users WHERE Email = '"+emailField.getText()+"' AND Password = '"+String.valueOf(passwordField.getPassword())+"'");
					 try {
						while (rs.next()) {
							 fEmail = rs.getString("Email");
							 fPassword = rs.getString("Password");
							Id = rs.getInt("ID");
							if (rs.getString("Role").equalsIgnoreCase("user")) {
								userForm users = new userForm(Id);
								users.setVisible(true);
								this.setVisible(false);
							}else if (rs.getString("Role").equalsIgnoreCase("admin")) {
								adminForm admins = new adminForm();
								admins.setVisible(true);
								this.setVisible(false);
							}
						}if(!emailField.getText().equals(fEmail) || !String.valueOf(passwordField.getPassword()).equals(fPassword)) {
							JOptionPane.showMessageDialog(null,"Wrong Email or Password","Message",JOptionPane.INFORMATION_MESSAGE,null);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 
					 }else {
						 registForm register = new registForm();
						register.setVisible(true);
						this.setVisible(false);
					}
	}
}

