package stadium.match;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.joda.time.DateTime;

import stadium.stadium.Stadium;
import stadium.structure.Structure;

/**
 * The Class ClientMatchesViewByWeek.
 */
@SuppressWarnings("serial")
public class ClientMatchesViewByWeek extends JPanel {

	private Structure structure;
	private ArrayList<Match> firstWeekMatches;
	private ArrayList<Match> secondWeekMatches;
	private ArrayList<Match> thirdWeekMatches;
	private ArrayList<Match> fourthWeekMatches;
	private ArrayList<Match> fifthWeekMatches;

	private JTabbedPane weeksTabs;

	/**
	 * Instantiates a new client matches view by week.
	 *
	 * @param structure the main structure
	 */
	public ClientMatchesViewByWeek(Structure structure) {
		this.structure = structure;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		
		this.setLayout(new GridLayout(1, 1));
		
		this.firstWeekMatches 	= new ArrayList<Match>();
		this.secondWeekMatches 	= new ArrayList<Match>();
		this.thirdWeekMatches 	= new ArrayList<Match>();
		this.fourthWeekMatches 	= new ArrayList<Match>();
		this.fifthWeekMatches 	= new ArrayList<Match>();

		for(Stadium stadium: this.structure.getStadiums()) {
			for(Match match: stadium.getMatches()) {
				if(match.getDate().getDayOfMonth() <= 1*7 && match.getDate().getMonthOfYear() == DateTime.now().getMonthOfYear() && !match.isFinished()) {
					this.firstWeekMatches.add(match);
				}
				else if(match.getDate().getDayOfMonth() <= 2*7 && match.getDate().getMonthOfYear() == DateTime.now().getMonthOfYear() && !match.isFinished()) {
					System.out.println(this.secondWeekMatches);
					this.secondWeekMatches.add(match);
				}
				else if(match.getDate().getDayOfMonth() <= 3*7 && match.getDate().getMonthOfYear() == DateTime.now().getMonthOfYear() && !match.isFinished()) {
					this.thirdWeekMatches.add(match);
				}
				else if(match.getDate().getDayOfMonth() <= 4*7 && match.getDate().getMonthOfYear() == DateTime.now().getMonthOfYear() && !match.isFinished()) {
					this.fourthWeekMatches.add(match);
				}
				else if(match.getDate().getDayOfMonth() >= 4*7 && match.getDate().getMonthOfYear() == DateTime.now().getMonthOfYear() && !match.isFinished()) {
					this.fifthWeekMatches.add(match);
				}
			}
		}
		
		this.weeksTabs = new JTabbedPane(JTabbedPane.TOP);
		this.weeksTabs.addTab("Prima settimana", new WeekMatchesPanel(this.firstWeekMatches, this.structure));
		this.weeksTabs.addTab("Seconda settimana", new WeekMatchesPanel(this.secondWeekMatches, this.structure));
		this.weeksTabs.addTab("Terza settimana", new WeekMatchesPanel(this.thirdWeekMatches, this.structure));
		this.weeksTabs.addTab("Quarta settimana", new WeekMatchesPanel(this.fourthWeekMatches, this.structure));
		this.weeksTabs.addTab("Quinta settimana", new WeekMatchesPanel(this.fifthWeekMatches, this.structure));
		this.add(this.weeksTabs);

	}

}
