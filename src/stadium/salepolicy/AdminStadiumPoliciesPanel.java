package stadium.salepolicy;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import stadium.stadium.Stadium;
import stadium.structure.Structure;

/**
 * The Class AdminStadiumPoliciesPanel.
 */
@SuppressWarnings("serial")
public class AdminStadiumPoliciesPanel extends JPanel {

	private Structure structure;
	private Stadium selectedStadium;

	private JLabel choiseAStadium;

	private DefaultComboBoxModel<String> comboBoxModel;
	private JComboBox<String> stadiumsCombo;

	private JLabel percentageLabel;
	private SpinnerNumberModel spinnerModel;
	private JSpinner percentageSpinner;

	private JLabel toggleLabel;
	private JCheckBox toggleCheckBox;

	private JLabel finalPriceLabel;
	private JLabel finalPrice;


	/**
	 * Instantiates a new admin stadium policies panel.
	 *
	 * @param structure the main structure
	 */
	public AdminStadiumPoliciesPanel(Structure structure) {
		this.structure = structure;
		this.selectedStadium = null;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new GridLayout(4, 2));

		this.choiseAStadium = new JLabel("Seleziona uno stadio");
		this.add(this.choiseAStadium);
		this.comboBoxModel = new DefaultComboBoxModel<String>();
		for(Stadium stadium: this.structure.getStadiums()) {
			this.comboBoxModel.addElement(stadium.getName());
		}
		this.stadiumsCombo = new JComboBox<String>(this.comboBoxModel);
		this.stadiumsCombo.addActionListener(l -> {
			updateGUI();
			this.percentageSpinner.setValue((int) this.selectedStadium.getDiscountPercentage());
		});
		if(this.structure.getStadiums().size() > 0) {
			this.selectedStadium = this.structure.getStadiumByName((String) this.comboBoxModel.getElementAt(0));			
		}
		this.add(this.stadiumsCombo);

		this.percentageLabel = new JLabel("Percentuale di sconto");
		this.add(this.percentageLabel);
		if(this.selectedStadium != null) {
			this.spinnerModel = new SpinnerNumberModel(this.selectedStadium.getDiscountPercentage(), 0, 100, 1);
		}
		else {
			this.spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
		}
		this.percentageSpinner = new JSpinner(this.spinnerModel);
		this.percentageSpinner.addChangeListener(l -> {
			updateGUI();
			save();
		});
		this.add(this.percentageSpinner);

		this.toggleLabel = new JLabel("Attiva/Disattiva sconto");
		this.add(this.toggleLabel);
		this.toggleCheckBox = new JCheckBox();
		if(!(this.selectedStadium == null)) {
			this.toggleCheckBox.setSelected(this.selectedStadium.isActiveSale());			
		}
		this.toggleCheckBox.addActionListener(l -> save());
		this.add(this.toggleCheckBox);

		this.finalPriceLabel = new JLabel("Prezzo finale per partita con sconto attivato");
		this.add(this.finalPriceLabel);
		if(!(this.selectedStadium == null)) {
			this.finalPrice = new JLabel(String.valueOf(SalePolicy.calcPerc(this.selectedStadium.getPricePerMatch(), (int) this.spinnerModel.getValue())));			
		}
		else {
			this.finalPrice = new JLabel("--");
		}
		this.add(this.finalPrice);

	}
	
	/**
	 * Update GUI.
	 */
	private void updateGUI() {
		this.selectedStadium = this.structure.getStadiumByName((String) this.comboBoxModel.getSelectedItem());
		this.toggleCheckBox.setSelected(this.selectedStadium.isActiveSale());
		this.finalPrice.setText(String.valueOf(SalePolicy.calcPerc(this.selectedStadium.getPricePerMatch(), (int) this.spinnerModel.getValue())));
	}
	
	/**
	 * Save this discount policy's settings.
	 */
	private void save() {
		this.selectedStadium.setDiscountPercentage((int) this.spinnerModel.getValue());
		this.selectedStadium.setActiveSale(this.toggleCheckBox.isSelected());
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
