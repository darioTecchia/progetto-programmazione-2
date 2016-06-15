package stadium.ticket;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import stadium.structure.Structure;

/**
 * The Class ClientPurchasePanel.
 */
@SuppressWarnings("serial")
public class ClientPurchasePanel extends JPanel {
	
	private Structure structure;

	JTabbedPane ticketsTabs;
	
	/**
	 * Instantiates a new client purchase panel.
	 *
	 * @param structure the main structure
	 */
	public ClientPurchasePanel(Structure structure) {
		this.structure = structure;
		initialize();
	}
	
	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		
		this.setLayout(new GridLayout(1, 1));
		
		this.ticketsTabs = new JTabbedPane(JTabbedPane.LEFT);
		this.ticketsTabs.addTab("I Miei Acquisti", new ClientPurchasesPanel(this.structure));
		this.ticketsTabs.addTab("Le Mie Prenotazioni", new ClientPrenotationsPanel(this.structure));
		this.add(this.ticketsTabs);
		
	}
}