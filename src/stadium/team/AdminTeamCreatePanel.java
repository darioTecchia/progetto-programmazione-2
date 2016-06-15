package stadium.team;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import stadium.exception.ItemAlreadyPresentException;
import stadium.structure.Structure;

/**
 * The Class AdminTeamCreatePanel.
 */
@SuppressWarnings("serial")
public class AdminTeamCreatePanel extends JPanel {

	private Structure structure;

	private JTabbedPane teamsTabs;

	private JPanel teamNamePanel;
	private JLabel teamNameLabel;
	private JTextField teamName;

	private JPanel buttonPanel;
	private JButton saveButton;

	/**
	 * Instantiates a new admin team create panel.
	 *
	 * @param structure the main structure
	 * @param teamsTabs the teams tabs
	 */
	public AdminTeamCreatePanel(Structure structure, JTabbedPane teamsTabs) {
		this.structure = structure;
		this.teamsTabs = teamsTabs;
		initialize();		
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new BorderLayout());

		this.teamNamePanel = new JPanel(new GridLayout(1, 2));
		this.teamNameLabel = new JLabel("Nome Team");
		this.teamNamePanel.add(this.teamNameLabel);
		this.teamName = new JTextField();
		this.teamNamePanel.add(this.teamName);
		this.add(this.teamNamePanel, BorderLayout.CENTER);

		this.buttonPanel = new JPanel(new GridLayout(1, 1));
		this.saveButton = new JButton("Salva team");
		this.saveButton.addActionListener(l -> addTeam());
		this.buttonPanel.add(this.saveButton);
		this.add(this.buttonPanel, BorderLayout.SOUTH);

	}

	/**
	 * Create and add the team to the structure.
	 */
	private void addTeam() {
		System.out.println("Adding new team");
		try {
			Team tempTeam = new Team(this.teamName.getText());
			this.structure.addTeam(tempTeam);
			this.teamsTabs.addTab(tempTeam.getName(), new AdminTeamShowPanel(tempTeam, structure, teamsTabs));
			this.teamName.setText("");
			this.structure.save();
			System.out.println("Added " + tempTeam.getName());
		}
		catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "Il team non può avere il nome vuoto o non può iniziare con spazi");
			System.out.println("Can't save the team!");
		}
		catch(ItemAlreadyPresentException e) {
			JOptionPane.showMessageDialog(this, "Non posso salvare il team, è già pressente un team con il nome: " + this.teamName.getText());
			System.out.println("Can't save the team!");
		}
		catch(IOException e) {
			e.printStackTrace();
		}		
	}

}
