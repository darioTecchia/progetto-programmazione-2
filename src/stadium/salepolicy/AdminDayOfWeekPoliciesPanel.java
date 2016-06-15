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

import stadium.structure.Structure;

/**
 * The Class AdminDayOfWeekPoliciesPanel.
 */
@SuppressWarnings("serial")
public class AdminDayOfWeekPoliciesPanel extends JPanel {

	private Structure structure;
	private String[] days = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
	private JLabel choiseADayLabel;
	
	private DefaultComboBoxModel<String> comboBoxModel;
	private JComboBox<String> daysCombo;

	private JLabel percentageLabel;
	private SpinnerNumberModel spinnerModel;
	private JSpinner percentageSpinner;

	private JLabel toggleLabel;
	private JCheckBox toggleCheckBox;

	/**
	 * Instantiates a new admin day of week policies panel.
	 *
	 * @param structure the main structure
	 */
	public AdminDayOfWeekPoliciesPanel(Structure structure) {
		this.structure = structure;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new GridLayout(3, 2));

		this.choiseADayLabel = new JLabel("Seleziona giorno");
		this.add(this.choiseADayLabel);
		this.comboBoxModel = new DefaultComboBoxModel<String>(this.days);
		this.daysCombo = new JComboBox<String>(this.comboBoxModel);
		this.comboBoxModel.setSelectedItem((String) this.days[this.structure.getSaleDay()-1]);
		this.add(this.daysCombo);

		this.percentageLabel = new JLabel("Seleziona una percentuale di sconto per il giorno selezionato");
		this.add(this.percentageLabel);
		this.spinnerModel = new SpinnerNumberModel(this.structure.getDiscountPercentage(), 0, 100, 1);
		this.percentageSpinner = new JSpinner(this.spinnerModel);
		this.percentageSpinner.addChangeListener(l -> save());
		this.add(this.percentageSpinner);

		this.toggleLabel = new JLabel("Attiva/Disattiva sconto");
		this.add(this.toggleLabel);
		this.toggleCheckBox = new JCheckBox();
		this.toggleCheckBox.addActionListener(l -> save());
		this.add(this.toggleCheckBox);

	}

	/**
	 * Save this discount policy's settings.
	 */
	private void save() {
		int selectedDay = 1;
		switch ((String) this.comboBoxModel.getSelectedItem()) {
		case "Lunedì":
			selectedDay = 1;
			break;
		case "Martedì":
			selectedDay = 2;
			break;
		case "Mercoledì":
			selectedDay = 3;
			break;
		case "Giovedì":
			selectedDay = 4;
			break;
		case "Venerdì":
			selectedDay = 5;
			break;
		case "Sabato":
			selectedDay = 6;
			break;
		case "Domenica":
			selectedDay = 7;
			break;
		}
		this.structure.setSaleDay(selectedDay);
		this.structure.setDiscountPercentage((int) this.spinnerModel.getValue());
		this.structure.setSaleDayActivated(this.toggleCheckBox.isSelected());
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
