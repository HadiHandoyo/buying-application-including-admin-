import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class adminForm extends JFrame implements ActionListener, MouseListener{
JMenu accountMenu, manageMenu;
JMenuItem logoutItem, productItem, productTypeItem;
JMenuBar menuBar;
JDesktopPane desktopPane = new JDesktopPane();

	public adminForm() {
		accountMenu = new JMenu("Account");
		manageMenu = new JMenu("Manage");
		logoutItem = new JMenuItem("Log Out");
		productItem = new JMenuItem("Product");
		productTypeItem = new JMenuItem("Product Type");
		logoutItem.addActionListener(this);
		productItem.addActionListener(this);
		productTypeItem.addActionListener(this);
		
		menuBar = new JMenuBar();
		
		accountMenu.add(logoutItem);
		manageMenu.add(productItem);
		manageMenu.add(productTypeItem);
		menuBar.add(accountMenu);
		menuBar.add(manageMenu);
		setJMenuBar(menuBar);
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		setSize(new Dimension(1200,700));
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
		if (e.getSource() == productItem) {
			desktopPane.removeAll();
			desktopPane.add(new manageProductForm());
		}
		if (e.getSource() == productTypeItem) {
			desktopPane.removeAll();
			desktopPane.add(new manageProductType());
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
