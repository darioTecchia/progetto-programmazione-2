package stadium.user;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import stadium.match.AdminMatchesViewPanel;
import stadium.salepolicy.AdminSalePoliciesPanel;
import stadium.stadium.AdminStadiumProfitPanel;
import stadium.stadium.AdminStadiumsViewPanel;
import stadium.structure.Structure;
import stadium.team.AdminTeamsViewPanel;

/**
 * The Class AdminViewPanel.
 */
@SuppressWarnings("serial")
public class AdminViewPanel extends JPanel {
	
	private Structure structure;

	private JTabbedPane tabPane;
	
	/**
	 * Instantiates a new admin view panel.
	 *
	 * @param structure the main structure
	 */
	public AdminViewPanel(Structure structure) {
		this.structure 	= structure;
		initialize();		
	}
	
	/**
	 * Initialize GUI.
	 */
	public void initialize() {
		this.setLayout(new GridLayout(1, 1));
		this.tabPane = new JTabbedPane(JTabbedPane.LEFT);
		this.tabPane.addTab("Stadi", new AdminStadiumsViewPanel(this.structure));
		this.tabPane.addTab("Team", new AdminTeamsViewPanel(this.structure));
		this.tabPane.addTab("Partite", new AdminMatchesViewPanel(this.structure));
		this.tabPane.addTab("Politiche di sconto", new AdminSalePoliciesPanel(this.structure));
		this.tabPane.addTab("Incasso", new AdminStadiumProfitPanel(this.structure));
		this.add(tabPane);
	}
	
}
