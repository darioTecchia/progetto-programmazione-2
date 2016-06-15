package stadium.user;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import stadium.match.ClientMainPanel;
import stadium.structure.Structure;
import stadium.ticket.ClientPurchasePanel;

/**
 * The Class ClientViewPanel.
 */
@SuppressWarnings("serial")
public class ClientViewPanel extends JPanel {
	
	private Structure structure;
	
	private JTabbedPane matchesVisualizationTabs;
	
	/**
	 * Instantiates a new client view panel.
	 *
	 * @param structure the main structure
	 */
	public ClientViewPanel(Structure structure) {
		this.structure = structure;
		initialize();
	}
	
	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		
		this.setLayout(new GridLayout(1, 1));
		
		this.matchesVisualizationTabs = new JTabbedPane(JTabbedPane.LEFT);
		this.matchesVisualizationTabs.addTab("Match", new ClientMainPanel(this.structure));
		this.matchesVisualizationTabs.addTab("Miei acquisti", new ClientPurchasePanel(this.structure));
		this.add(this.matchesVisualizationTabs);
		if(this.structure.getCurrentUser().getPreOrderedTickets().size() > 0) {
			JOptionPane.showMessageDialog(this, "Hai un totale di " + this.structure.getCurrentUser().getPreOrderedTickets().size() + " prenotazioni!");			
		}
		
	}

}
