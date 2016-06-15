package stadium.structure;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import stadium.user.AdminViewPanel;
import stadium.user.ClientViewPanel;
import stadium.user.LoginViewPanel;
import stadium.user.User;

/**
 * The Class StructureMainView.
 */
@SuppressWarnings("serial")
public class StructureMainView extends JFrame{

	private Structure structure;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem logoutMenuItem;
	private JPanel mainPanel;

	/**
	 * Instantiates a new structure main view.
	 *
	 * @param structure the main structure
	 */
	public StructureMainView(Structure structure) {
		this.structure = structure;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		this.setTitle("Struttura Sportiva " + this.structure.getName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridLayout(1, 1));

		this.menuBar = new JMenuBar();
		this.menu = new JMenu("Account");
		this.menuBar.add(menu);
		this.logoutMenuItem = new JMenuItem("Logout");
		this.logoutMenuItem.addActionListener(l -> logout());
		this.menu.add(logoutMenuItem);
		this.setJMenuBar(this.menuBar);
		this.menuBar.setVisible(false);

		this.mainPanel = new LoginViewPanel(this);
		this.getContentPane().add(mainPanel);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Login.
	 *
	 * @param username the user's name
	 * @param password the user's password
	 */
	public void login(String username, String password) {
		System.out.println(username + "-" + password);
		for(User user: structure.getRegistreredUsers()) {
			if(user.getName().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
				System.out.println("Someone has logged with: " + username + "-" + password);
				this.structure.setCurrentUser(user);
				
				if(this.structure.getCurrentUser().isAdmin()) {
					System.out.println("Admin has logged");
					this.remove(mainPanel);
					this.mainPanel = new AdminViewPanel(this.structure);
					this.add(mainPanel);
					this.menuBar.setVisible(true);
					this.pack();
					this.repaint();
					this.revalidate();
				}
				else if(!this.structure.getCurrentUser().isAdmin()) {
					System.out.println("Client has logged " + "(" + this.structure.getCurrentUser().getKind() + ")");
					this.remove(mainPanel);
					this.mainPanel = new ClientViewPanel(this.structure);
					this.add(mainPanel);
					this.setJMenuBar(this.menuBar);
					this.menuBar.setVisible(true);
					this.pack();
					this.repaint();
					this.revalidate();
				}
				break;
			}
		}
		if(this.structure.getCurrentUser() == null) {
			System.out.println("Account not found!");
			JOptionPane.showMessageDialog(this, "Nessun utente trovato!");
		}
	}

	/**
	 * Logout.
	 */
	public void logout() {
		System.out.println(this.structure.getCurrentUser().getName() + " Logged Out!");
		this.structure.setCurrentUser(null);
		this.remove(this.mainPanel);
		this.mainPanel = new LoginViewPanel(this);
		this.add(mainPanel);
		this.menuBar.setVisible(false);
		this.pack();
		this.repaint();
		this.revalidate();
	}
}
