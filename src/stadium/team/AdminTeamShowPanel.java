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
 * The Class AdminTeamShowPanel.
 */
@SuppressWarnings("serial")
public class AdminTeamShowPanel extends JPanel {

	private Structure structure;

	private Team team;

	private JTabbedPane tabbedPane;

	private JPanel teamNamePanel;

	private JLabel teamNameLabel;
	private JTextField teamName;

	private JPanel buttonPanel;
	private JButton saveButton;
	private JButton removeButton;	

	/**
	 * Instantiates a new admin team show panel.
	 *
	 * @param team the team that will bel showed
	 * @param structure the main structure
	 * @param tabbedPane the tabbed pane
	 */
	public AdminTeamShowPanel(Team team, Structure structure, JTabbedPane tabbedPane) {
		this.structure = structure;
		this.tabbedPane = tabbedPane;
		this.team = team;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new BorderLayout());

		this.setLayout(new BorderLayout());

		this.teamNamePanel = new JPanel(new GridLayout(1, 2));
		this.teamNameLabel = new JLabel("Nome Team");
		this.teamNamePanel.add(this.teamNameLabel);
		this.teamName = new JTextField(this.team.getName());
		this.teamNamePanel.add(this.teamName);
		this.add(this.teamNamePanel, BorderLayout.CENTER);

		this.buttonPanel = new JPanel(new GridLayout(1, 2));
		this.saveButton = new JButton("Salva modifiche");
		this.saveButton.addActionListener(l -> updateTeam());
		this.buttonPanel.add(this.saveButton);
		this.removeButton = new JButton("Elimina");
		this.removeButton.addActionListener(l -> deleteTeam(this.team));
		this.buttonPanel.add(this.removeButton);

		this.add(this.buttonPanel, BorderLayout.SOUTH);

	}

	/**
	 * Check all the settings, if are correct the team will be updated.
	 * 
	 * @return true, if are correct
	 */
	private boolean check() {
		if(!this.teamName.getText().matches("[a-zA-Z]{1,}(\\s)*[a-zA-Z]{1,}")) {
			throw new IllegalArgumentException("The name should be not empty or starts with space");
		}
		else {			
			Team tempTeam = this.structure.getTeamByName(this.teamName.getText());
			if(tempTeam != null) {
				return false;
			}
			else {
				return true;
			}
		}
	}

	/**
	 * Update the team with the new settings.
	 */
	private void updateTeam() {
		System.out.println("Updating " + this.team.getName() + " team");
		try {
			if(!check()) {
				throw new ItemAlreadyPresentException("There is already a team with this name");
			}
			this.team.setName(this.teamName.getText());
			this.tabbedPane.setTitleAt(this.tabbedPane.getSelectedIndex(), this.teamName.getText());
			this.structure.save();
		}
		catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "Il team non può avere il nome vuoto o iniziare con spazi");
			System.out.println("Can't save the team!");
		}
		catch(ItemAlreadyPresentException e) {
			JOptionPane.showMessageDialog(this, "E' già presente un team nella struttura con questo nome");
			System.out.println("Can't save the team!");
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Delete team.
	 *
	 * @param team the team
	 */
	private void deleteTeam(Team team) {
		this.structure.getTeams().remove(team);
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.tabbedPane.remove(this.tabbedPane.getSelectedIndex());
	}

}
