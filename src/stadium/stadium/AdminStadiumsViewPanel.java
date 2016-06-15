package stadium.stadium;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import stadium.structure.Structure;

/**
 * The Class AdminStadiumsViewPanel.
 */
@SuppressWarnings("serial")
public class AdminStadiumsViewPanel extends JPanel {

	private Structure structure;

	private JTabbedPane stadiumsTabs;	

	/**
	 * Instantiates a new admin stadiums view panel.
	 *
	 * @param structure the main structure
	 */
	public AdminStadiumsViewPanel(Structure structure) {
		this.structure = structure;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		this.setLayout(new GridLayout(1, 1));
		this.stadiumsTabs = new JTabbedPane(JTabbedPane.LEFT);
		this.stadiumsTabs.addTab("Aggiungi", new AdminStadiumCreatePanel(this.structure, this.stadiumsTabs));
		for(Stadium stadium: this.structure.getStadiums()) {
			this.stadiumsTabs.addTab(stadium.getName(), new AdminStadiumShowPanel(stadium, this.structure, this.stadiumsTabs));
		}
		this.add(stadiumsTabs);
	}

}
