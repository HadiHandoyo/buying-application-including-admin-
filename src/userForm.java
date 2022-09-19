import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class userForm extends JFrame implements ActionListener, MouseListener{
JMenu accountMenu, buyMenu, transactionMenu;
JMenuItem logoutItem;
JMenuBar menuBar;
JDesktopPane desktopPane = new JDesktopPane();
int userId;
	public userForm(int userId) {
		this.userId = userId;
		menuBar = new JMenuBar();
		accountMenu = new JMenu("Account");
		buyMenu = new JMenu("Buy");
		transactionMenu = new JMenu("Transaction");
		transactionMenu.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				desktopPane.removeAll();
				desktopPane.add(new transactionForm());	
			}
		});
		logoutItem = new JMenuItem("Log Out");
		logoutItem.addActionListener(this);
		transactionMenu.addActionListener(this);
		menuBar.add(accountMenu);
		menuBar.add(buyMenu);
		menuBar.add(transactionMenu);
		accountMenu.add(logoutItem);
		desktopPane.setLayout(new BorderLayout());
		buyMenu.addMouseListener(this);
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		setJMenuBar(menuBar);
		setSize(new Dimension(1400,900));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logoutItem) {
			firstForm loginForm = new firstForm();
			loginForm.setVisible(true);
			this.setVisible(false);
		}
		
		if (e.getSource().equals(transactionMenu)) {
			
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		desktopPane.removeAll();
		desktopPane.add(new buyForm(userId));
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
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
