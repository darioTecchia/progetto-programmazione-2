package stadium.stadium;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import stadium.exception.ItemAlreadyPresentException;
import stadium.structure.Structure;

/**
 * The Class AdminStadiumCreatePanel.
 */
@SuppressWarnings("serial")
public class AdminStadiumCreatePanel extends JPanel {

	private Structure structure;

	private JTabbedPane stadiumsTabs;

	private JLabel createNewStadiumLabel;

	private JPanel stadiumSettings;

	private JLabel stadiumNameLabel;
	private JTextField stadiumName;

	private JLabel capiencyLabel;
	private JSpinner capiencySpinner;

	private JLabel priceLabel;
	private JSpinner priceSpinner;

	private JButton addStadiumButton;

	/**
	 * Instantiates a new admin stadium create panel.
	 *
	 * @param structure the main structure
	 * @param stadiumsTabs the stadiums tabs
	 */
	public AdminStadiumCreatePanel(Structure structure, JTabbedPane stadiumsTabs) {
		this.structure = structure;
		this.stadiumsTabs = stadiumsTabs;
		initialize();		
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new BorderLayout());

		this.createNewStadiumLabel = new JLabel("Aggiungi nuovo stadio", SwingConstants.CENTER);
		this.createNewStadiumLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.add(this.createNewStadiumLabel, BorderLayout.NORTH);

		this.stadiumSettings = new JPanel(new GridLayout(3, 2, 70, 70));
		this.add(this.stadiumSettings, BorderLayout.CENTER);

		this.stadiumNameLabel = new JLabel("Nome stadio", SwingConstants.CENTER);
		this.stadiumNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.stadiumSettings.add(this.stadiumNameLabel);

		this.stadiumName = new JTextField();
		this.stadiumSettings.add(this.stadiumName);

		this.capiencyLabel = new JLabel("Capienza Stadio", SwingConstants.CENTER);
		this.capiencyLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.stadiumSettings.add(this.capiencyLabel);

		this.capiencySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
		this.stadiumSettings.add(this.capiencySpinner);

		this.priceLabel = new JLabel("Prezzo per partita", SwingConstants.CENTER);
		this.priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.stadiumSettings.add(this.priceLabel);

		this.priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999.0, 0.1));
		this.stadiumSettings.add(this.priceSpinner);

		this.addStadiumButton = new JButton("Salva stadio");
		this.addStadiumButton.addActionListener(l -> addStadium());
		this.add(this.addStadiumButton, BorderLayout.SOUTH);

	}

	/**
	 * Create and add the stadium to the structure.
	 */
	private void addStadium() {
		System.out.println("Adding new stadium");
		try {
			Stadium tempStadium = new Stadium(this.stadiumName.getText(),(int) this.capiencySpinner.getValue(), (double) this.priceSpinner.getValue());
			this.structure.addStadium(tempStadium);
			this.stadiumsTabs.addTab(tempStadium.getName(), new AdminStadiumShowPanel(tempStadium, this.structure, this.stadiumsTabs));
			this.stadiumName.setText("");
			this.capiencySpinner.setValue(0);
			this.priceSpinner.setValue(0);
			this.structure.save();
			System.out.println("Added " + tempStadium.getName());
		}
		catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "Lo stadio non può avere il nome vuoto o iniziante per spazi");
			System.out.println("Can't save the stadium!");			
		}
		catch(ItemAlreadyPresentException e) {
			JOptionPane.showMessageDialog(this, "Non posso salvare lo stadio, è già pressente uno stadio con il nome: " + this.stadiumName.getText());
			System.out.println("Can't save the stadium!");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
