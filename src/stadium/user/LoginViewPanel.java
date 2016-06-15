package stadium.user;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import stadium.structure.StructureMainView;

/**
 * The Class LoginViewPanel.
 */
@SuppressWarnings("serial")
public class LoginViewPanel extends JPanel {

	JLabel userLabel;
	JTextField userField;

	JLabel passLabel;
	JPasswordField passField;

	JButton loginButton;
	StructureMainView mv;

	/**
	 * Instantiates a new login view panel.
	 *
	 * @param mv the structure main view
	 */
	public LoginViewPanel(StructureMainView mv) {
		this.mv = mv;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		this.setLayout(new GridLayout(5, 1));

		this.userLabel = new JLabel("Username");
		this.add(userLabel);

		this.userField = new JTextField();
		this.add(userField);

		this.passLabel = new JLabel("Password");
		this.add(passLabel);

		this.passField = new JPasswordField();
		this.add(passField);

		this.loginButton = new JButton("Accedi");
		this.loginButton.addActionListener(l->mv.login(this.userField.getText(), String.valueOf(this.passField.getPassword())));
		this.add(loginButton);
	}
	
}