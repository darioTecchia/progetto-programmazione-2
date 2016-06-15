package stadium.match;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import stadium.structure.Structure;

/**
 * The Class WeekMatchesPanel.
 */
@SuppressWarnings("serial")
public class WeekMatchesPanel extends JPanel {

	private Structure structure;
	private ArrayList<Match> matches;

	private JPanel matchesPanel;
	private JScrollPane matchesScrollPane;

	/**
	 * Instantiates a new week matches panel.
	 *
	 * @param matches the matches of a week
	 * @param structure the main structure
	 */
	public WeekMatchesPanel(ArrayList<Match> matches, Structure structure) {
		this.structure 	= structure;
		this.matches 		= matches;
		initialize();
	}

	/**
	 * The Class MatchComponent.
	 */
	private class MatchComponent extends JPanel {

		private Structure structure;
		private Match match;

		JPanel matchInfoPanel;
		
		JLabel teamsLabel;
		JLabel dateLabel;
		JLabel stadiumLabel;

		JButton goToMatchButton;

		/**
		 * Instantiates a new match component.
		 *
		 * @param match the match
		 * @param structure the main structure
		 */
		public MatchComponent(Match match, Structure structure) {
			this.structure 	= structure;
			this.match 			= match;
			initialize();
		}

		/**
		 * Initialize GUI.
		 */
		private void initialize() {
			this.setLayout(new BorderLayout());

			this.matchInfoPanel = new JPanel(new GridLayout(3, 1));
			this.teamsLabel = new JLabel(this.match.getTeam_1().getName() + " vs " + this.match.getTeam_2().getName());
			this.matchInfoPanel.add(this.teamsLabel);
			this.dateLabel = new JLabel(this.match.getDate().toString("EEEE dd MMMM yyyy HH:mm"));
			this.matchInfoPanel.add(this.dateLabel);
			this.stadiumLabel = new JLabel(this.match.getStadium().getName());
			this.matchInfoPanel.add(this.stadiumLabel);
			this.add(this.matchInfoPanel, BorderLayout.CENTER);

			this.goToMatchButton = new JButton("Vai Al Match");
			this.goToMatchButton.addActionListener(l -> new ClientPurchaseTicketView(this.match, this.structure));
			this.add(this.goToMatchButton, BorderLayout.EAST);
		}
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		this.setLayout(new GridLayout(1, 1));
		this.matchesPanel = new JPanel(new GridLayout(this.matches.size()*2, 1));

		for(Match ticket: this.matches) {
			this.matchesPanel.add(new MatchComponent(ticket, this.structure));
			this.matchesPanel.add(new JLabel(""));				
		}
		this.matchesScrollPane = new JScrollPane();
		this.matchesScrollPane.getViewport().add(this.matchesPanel);
		this.add(this.matchesScrollPane);
	}
}
