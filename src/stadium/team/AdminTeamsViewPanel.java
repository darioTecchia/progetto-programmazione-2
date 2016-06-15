package stadium.team;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import stadium.structure.Structure;

/**
 * The Class AdminTeamsViewPanel.
 */
@SuppressWarnings("serial")
public class AdminTeamsViewPanel extends JPanel {

	private Structure structure;

	private JTabbedPane teamsTabs;	

	/**
	 * Instantiates a new admin teams view panel.
	 *
	 * @param structure the main structure
	 */
	public AdminTeamsViewPanel(Structure structure) {
		this.structure = structure;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		this.setLayout(new GridLayout(1, 1));
		this.teamsTabs = new JTabbedPane(JTabbedPane.LEFT);
		this.teamsTabs.addTab("Aggiungi", new AdminTeamCreatePanel(this.structure, this.teamsTabs));
		for(Team team: this.structure.getTeams()) {
			this.teamsTabs.addTab(team.getName(), new AdminTeamShowPanel(team, structure, teamsTabs));
		}
		this.add(teamsTabs);
	}

}
