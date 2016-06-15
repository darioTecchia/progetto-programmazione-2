package stadium.stadium;

import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import stadium.structure.Structure;

/**
 * The Class AdminStadiumProfitPanel.
 */
@SuppressWarnings("serial")
public class AdminStadiumProfitPanel extends JPanel {

	private Structure structure;
	private Stadium selectedStadium;

	private JPanel totalProfitPanel;
	
	private JLabel totalProfitLabel;
	private JLabel totalProfit;

	private JPanel profitPerStadiumPanel;
	
	private JLabel selectAStadiumLabel;
	private DefaultComboBoxModel<String> selectAStadiumComboBoxModel;
	private JComboBox<String> selectAStadiumComboBox;
	private JLabel profitPerStadiumLabel;

	/**
	 * Instantiates a new admin stadium profit panel.
	 *
	 * @param structure the main structure
	 */
	public AdminStadiumProfitPanel(Structure structure) {
		this.structure = structure;
		this.selectedStadium = null;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new GridLayout(2, 1));		

		this.profitPerStadiumPanel = new JPanel(new GridLayout(1, 3));
		this.selectAStadiumLabel = new JLabel("Seleziona uno stadio per visualizzare il suo incasso");
		this.profitPerStadiumPanel.add(this.selectAStadiumLabel);
		this.selectAStadiumComboBoxModel = new DefaultComboBoxModel<String>();
		for(Stadium stadium: this.structure.getStadiums()) {
			this.selectAStadiumComboBoxModel.addElement(stadium.getName());
		}
		this.selectAStadiumComboBox = new JComboBox<String>(this.selectAStadiumComboBoxModel);
		this.selectAStadiumComboBox.addActionListener(l -> updateGUI());
		this.profitPerStadiumPanel.add(this.selectAStadiumComboBox);
		if(this.structure.getStadiums().size() > 0) {
			this.selectedStadium = this.structure.getStadiumByName(this.selectAStadiumComboBoxModel.getElementAt(0));			
		}
		if(this.selectedStadium != null) {
			this.profitPerStadiumLabel = new JLabel(String.valueOf(this.selectedStadium.getTaking()));			
		}
		else {
			this.profitPerStadiumLabel = new JLabel("--");
		}
		this.profitPerStadiumPanel.add(this.profitPerStadiumLabel);
		this.add(this.profitPerStadiumPanel);

		this.totalProfitPanel = new JPanel(new GridLayout(1, 2));
		this.totalProfitLabel = new JLabel("Profitto totale");
		this.totalProfitPanel.add(this.totalProfitLabel);
		this.totalProfit = new JLabel(String.valueOf(this.structure.getTaking()));
		this.totalProfitPanel.add(this.totalProfit);
		this.add(this.totalProfitPanel);

	}

	/**
	 * Update GUI.
	 */
	private void updateGUI() {
		this.selectedStadium = this.structure.getStadiumByName((String) this.selectAStadiumComboBox.getSelectedItem());
		this.profitPerStadiumLabel.setText(String.valueOf(this.selectedStadium.getTaking()));
	}

}
