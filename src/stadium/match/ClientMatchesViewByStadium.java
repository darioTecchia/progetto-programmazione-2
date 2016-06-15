package stadium.match;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import stadium.stadium.ClientStadiumShowPanel;
import stadium.stadium.Stadium;
import stadium.structure.Structure;

/**
 * The Class ClientMatchesViewByStadium.
 */
@SuppressWarnings("serial")
public class ClientMatchesViewByStadium extends JPanel {
	
	private Structure structure;
	
	private JTabbedPane stadiumTabs;
	
	/**
	 * Instantiates a new client matches view by stadium.
	 *
	 * @param structure the main structure
	 */
	public ClientMatchesViewByStadium(Structure structure) {
		this.structure = structure;
		initialize();
	}
	
	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		
		this.setLayout(new BorderLayout());
		
		this.stadiumTabs = new JTabbedPane(JTabbedPane.LEFT);
		for(Stadium stadium: this.structure.getStadiums()) {
			this.stadiumTabs.addTab(stadium.getName(), new ClientStadiumShowPanel(this.structure, stadium));
		}
		this.add(this.stadiumTabs);
		
	}

}
