package stadium.match;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import stadium.structure.Structure;

/**
 * The Class ClientMainPanel.
 */
@SuppressWarnings("serial")
public class ClientMainPanel extends JPanel {
	
	private Structure structure;
	
	private JTabbedPane matchesVisualizationTabs;
	
	/**
	 * Instantiates a new client main panel.
	 *
	 * @param structure the main structure
	 */
	public ClientMainPanel(Structure structure) {
		
		this.structure = structure;
		initialize();
		
	}
	
	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		
		this.setLayout(new GridLayout(1, 1));
		
		this.matchesVisualizationTabs = new JTabbedPane(JTabbedPane.TOP);
		this.matchesVisualizationTabs.addTab("Visualizza partite per stadio", new ClientMatchesViewByStadium(this.structure));
		this.matchesVisualizationTabs.addTab("Visualizza partite per settimana", new ClientMatchesViewByWeek(this.structure));
		this.matchesVisualizationTabs.addTab("Visualizza tutte le partite", new ClientMatchesViewByAll(this.structure));
		this.add(this.matchesVisualizationTabs);
		
	}

}
