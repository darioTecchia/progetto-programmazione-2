package stadium.salepolicy;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import stadium.structure.Structure;

/**
 * The Class AdminSalePoliciesPanel.
 */
@SuppressWarnings("serial")
public class AdminSalePoliciesPanel extends JPanel {
	
	private Structure structure;
	private JTabbedPane policiesTabs;
	
	/**
	 * Instantiates a new admin sale policies panel.
	 *
	 * @param structure the main structure
	 */
	public AdminSalePoliciesPanel(Structure structure) {
		this.structure = structure;
		initialize();
	}
	
	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		
		this.setLayout(new GridLayout(1, 1));
		
		this.policiesTabs = new JTabbedPane();
		this.policiesTabs.add("Per tipologia d'utente", new AdminUserKindPoliciesPanel(this.structure));
		this.policiesTabs.add("Per giorno della settimana", new AdminDayOfWeekPoliciesPanel(this.structure));
		this.policiesTabs.add("Per stadio", new AdminStadiumPoliciesPanel(this.structure));
		
		this.add(this.policiesTabs);
		
	}
}
