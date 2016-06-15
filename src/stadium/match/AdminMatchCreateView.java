package stadium.match;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;

import stadium.stadium.Stadium;
import stadium.structure.Structure;
import stadium.team.Team;

/**
 * The Class AdminMatchCreateView.
 */
@SuppressWarnings("serial")
public class AdminMatchCreateView extends JFrame {

	private Structure structure;

	private JLabel addNewMatchLabel;
	private JPanel newMatchPanel;
	
	private DefaultTableModel matchesTableModel;
	
	private JPanel team_1Panel;
	private JLabel team_1Label;

	private DefaultComboBoxModel<String> team_1ComboModel;
	private JComboBox<String> team_1Combo;

	private JPanel team_2Panel;
	private JLabel team_2Label;

	private DefaultComboBoxModel<String> team_2ComboModel;
	private JComboBox<String> team_2Combo;

	private JPanel stadiumPanel;
	private JLabel stadiumLabel;

	private DefaultComboBoxModel<String> stadiumComboModel;
	private JComboBox<String> stadiumCombo;

	private JPanel timePanel;
	private JLabel timeLabel;

	private JSpinner hourSpinner;
	private SpinnerNumberModel hourSpinnerNumberModel;

	private JSpinner minuteSpinner;
	private SpinnerNumberModel minuteSpinnerNumberModel;

	private JPanel dayPanel;
	private JLabel dayLabel;
	
	private JSpinner daySpinner;
	private SpinnerNumberModel daySpinnerModel;

	private JSpinner monthSpinner;
	private SpinnerNumberModel monthSpinnerModel;

	private JSpinner yearSpinner;
	private SpinnerNumberModel yearSpinnerModel;

	private JPanel confirmPanel;
	private JLabel avaiableLabel;

	private JButton okButton;
	private JButton denyButton;

	/**
	 * Instantiates a new admin match create view.
	 *
	 * @param structure the main structure
	 * @param matchesTableModel the matches table model
	 */
	public AdminMatchCreateView(Structure structure, DefaultTableModel matchesTableModel) {
		this.matchesTableModel 	= matchesTableModel;
		this.structure 					= structure;
		initialize();
		setMaximumDay();
		check();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		this.setTitle("Aggiungi nuovo match");
		this.getContentPane().setLayout(new BorderLayout());
		this.setVisible(true);

		this.addNewMatchLabel = new JLabel("Aggiungi nuovo match", SwingConstants.CENTER);
		this.add(this.addNewMatchLabel, BorderLayout.NORTH);

		this.newMatchPanel = new JPanel(new GridLayout(5, 1));

		this.team_1Panel = new JPanel(new GridLayout(1, 2));
		this.team_1Label = new JLabel("Seleziona squadra 1:");
		this.team_1Panel.add(this.team_1Label);
		this.team_1ComboModel = new DefaultComboBoxModel<String>();
		this.team_1Combo = new JComboBox<String>(this.team_1ComboModel);
		for(Team team: this.structure.getTeams()) {
			this.team_1ComboModel.addElement(team.getName());
		}
		this.team_1Combo.addActionListener(l -> check());
		this.team_1Panel.add(this.team_1Combo);
		this.newMatchPanel.add(this.team_1Panel);

		this.team_2Panel = new JPanel(new GridLayout(1, 2));
		this.team_2Label = new JLabel("Seleziona squadra 2:");
		this.team_2Panel.add(this.team_2Label);
		this.team_2ComboModel = new DefaultComboBoxModel<String>();
		this.team_2Combo = new JComboBox<String>(this.team_2ComboModel);
		for(Team team: this.structure.getTeams()) {
			this.team_2ComboModel.addElement(team.getName());
		}
		this.team_2Combo.addActionListener(l -> check());
		this.team_2Panel.add(this.team_2Combo);
		this.newMatchPanel.add(this.team_2Panel);

		this.stadiumPanel = new JPanel(new GridLayout(1, 2));
		this.stadiumLabel = new JLabel("Seleziona stadio:");
		this.stadiumPanel.add(this.stadiumLabel);
		this.stadiumComboModel = new DefaultComboBoxModel<String>();
		this.stadiumCombo = new JComboBox<String>(this.stadiumComboModel);
		for(Stadium stadium: this.structure.getStadiums()) {
			this.stadiumComboModel.addElement(stadium.getName());
		}
		this.stadiumCombo.addActionListener(l -> check());
		this.stadiumPanel.add(this.stadiumCombo);
		this.newMatchPanel.add(this.stadiumPanel);

		this.timePanel = new JPanel(new GridLayout(1, 5));
		this.timeLabel = new JLabel("Ora inizio:");
		this.timePanel.add(this.timeLabel);
		this.hourSpinnerNumberModel = new SpinnerNumberModel(8, 8, 21, 1);
		this.hourSpinner = new JSpinner(this.hourSpinnerNumberModel);
		this.hourSpinner.addChangeListener(l -> check());
		this.timePanel.add(this.hourSpinner);

		this.timePanel.add(new JLabel(":", SwingConstants.CENTER));

		this.minuteSpinnerNumberModel = new SpinnerNumberModel(0, 0, 59, 1);
		this.minuteSpinner = new JSpinner(this.minuteSpinnerNumberModel);
		this.minuteSpinner.addChangeListener(l -> check());
		this.timePanel.add(this.minuteSpinner);
		this.newMatchPanel.add(this.timePanel);

		this.dayPanel = new JPanel(new GridLayout(1, 6));
		this.dayLabel = new JLabel("Giorno:");
		this.dayPanel.add(this.dayLabel);
		this.daySpinnerModel = new SpinnerNumberModel(DateTime.now().getDayOfMonth(), 1, 31, 1);
		this.daySpinner = new JSpinner(this.daySpinnerModel);
		this.daySpinner.addChangeListener(l -> {
			setMaximumDay();
			check();
		});
		this.dayPanel.add(this.daySpinner);
		this.dayPanel.add(new JLabel("/", SwingConstants.CENTER));
		this.monthSpinnerModel = new SpinnerNumberModel(DateTime.now().getMonthOfYear(), 1, 12, 1);
		this.monthSpinner = new JSpinner(this.monthSpinnerModel);
		this.monthSpinner.addChangeListener(l -> {
			setMaximumDay();
			check();
		});
		this.dayPanel.add(this.monthSpinner);
		this.dayPanel.add(new JLabel("/", SwingConstants.CENTER));
		this.yearSpinnerModel = new SpinnerNumberModel(DateTime.now().getYear(), DateTime.now().getYear(), DateTime.now().getYear() + 30, 1);
		this.yearSpinner = new JSpinner(this.yearSpinnerModel);
		this.yearSpinner.addChangeListener(l -> {
			setMaximumDay();
			check();
		});
		this.dayPanel.add(this.yearSpinner);
		this.newMatchPanel.add(this.dayPanel);
		this.add(this.newMatchPanel, BorderLayout.CENTER);

		this.confirmPanel = new JPanel(new GridLayout(1, 3));
		this.avaiableLabel = new JLabel("");
		this.confirmPanel.add(this.avaiableLabel);
		this.denyButton = new JButton("Annulla");
		this.denyButton.addActionListener(l -> this.dispose());
		this.confirmPanel.add(this.denyButton);
		this.okButton = new JButton("Crea Partita");
		if(this.team_1Combo.getItemCount() >= 1 && this.team_2Combo.getItemCount() >= 1) {
			if(this.team_1Combo.getSelectedItem().equals(this.team_2Combo.getSelectedItem())) {
				this.okButton.setEnabled(false);
			}
			else if(this.team_1Combo.getSelectedItem().equals(null) || 
					this.team_2Combo.getSelectedItem().equals(null) || 
					this.stadiumCombo.getSelectedItem().equals(null)) {
				this.okButton.setEnabled(false);
			}
		}
		else {
			this.okButton.setEnabled(false);
		}
		this.okButton.addActionListener(l -> addMatch());
		this.confirmPanel.add(this.okButton);
		this.add(this.confirmPanel, BorderLayout.SOUTH);

		this.pack();
	}

	/**
	 * Sets the maximum selectable day for a determinate month and year.
	 */
	private void setMaximumDay() {
		DateTime temp = new DateTime((int) this.yearSpinner.getValue(), (int) this.monthSpinner.getValue(), 1, 0, 0);
		daySpinnerModel.setMaximum(temp.dayOfMonth().getMaximumValue());
		if((int)this.daySpinner.getValue() > temp.dayOfMonth().getMaximumValue()) {
			daySpinnerModel.setValue((int) temp.dayOfMonth().getMaximumValue());				
		}
	}

	/**
	 * Function that check if you have selected the same team.
	 *
	 * @return true, if same team are selected
	 */
	private boolean sameTeamSelected() {
		if((String) this.team_1Combo.getSelectedItem() == ((String) this.team_2Combo.getSelectedItem())) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Checks if the selected team is busy in an another match.
	 *
	 * @param 	teamName the name of the team we want to check
	 * @return 	true, if is busy
	 */
	private boolean isBusy(String teamName) {
		for(Stadium stadium: this.structure.getStadiums()) {
			for(Match match: stadium.getMatches()) {
				if((match.getTeam_1().getName().equalsIgnoreCase(teamName) || 
						match.getTeam_2().getName().equalsIgnoreCase(teamName)) && 
						match.getDate().getDayOfMonth() == (int) this.daySpinner.getValue() &&
						match.getDate().getMonthOfYear() == (int) this.monthSpinner.getValue() &&
						match.getDate().getYear() == (int) this.yearSpinner.getValue()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if is stadium busy with an another match on the selected date.
	 *
	 * @param 	stadiumName the stadium name
	 * @return 	true, if is stadium busy
	 */
	private boolean isStadiumBusy(String stadiumName) {
		Stadium stadium = this.structure.getStadiumByName(stadiumName);
		DateTime selectedDate = new DateTime(
				(int) this.yearSpinnerModel.getValue(), 
				(int) this.monthSpinnerModel.getValue(), 
				(int) this.daySpinnerModel.getValue(), 
				(int) this.hourSpinnerNumberModel.getValue(), 
				(int) this.minuteSpinnerNumberModel.getValue());
		for(Match match: stadium.getMatches()) {
			if(selectedDate.isAfter(match.getDate().getMillis() - 5400000) && selectedDate.isBefore(match.getDate().getMillis() + 5400000)) {
				System.out.println("Stadium already have a match on this date: " + match.getTeam_1().getName() + " vs " + match.getTeam_2().getName());
				return true;
			}
		}
		return false;
	}

	/**
	 * Check all the settings, if are correct the creation button will be enabled.
	 */
	private void check() {
		boolean correctDay;
		boolean comboEmpty;
		/*
		 * Check if the comboboxs are empty
		 */
		if(this.team_1Combo.getItemCount() == 0 || 
				this.team_2Combo.getItemCount() == 0 || 
				this.stadiumCombo.getItemCount() == 0) {
			comboEmpty = true;
		}
		else {
			comboEmpty = false;
		}

		/*
		 * Check if the selcted start hour and day are correct
		 */
		DateTime tempDate = new DateTime(
				(int) this.yearSpinnerModel.getValue(),
				(int) this.monthSpinnerModel.getValue(),
				(int) this.daySpinnerModel.getValue(),
				(int) this.hourSpinnerNumberModel.getValue(),
				(int) this.minuteSpinnerNumberModel.getValue()
				);
		if(tempDate.isBeforeNow()) {
			correctDay = false;
		}
		else {
			correctDay = true;
		}

		if( !sameTeamSelected() && 
				correctDay && 
				!comboEmpty && 
				!isBusy((String) this.team_1Combo.getSelectedItem()) && 
				!isBusy((String) this.team_2Combo.getSelectedItem()) &&
				!isStadiumBusy((String) this.stadiumComboModel.getSelectedItem())) {
			this.okButton.setEnabled(true);
			System.out.println("All the settings are legal!");
		}
		if( sameTeamSelected() || 
				!correctDay || 
				comboEmpty || 
				isBusy((String) this.team_1Combo.getSelectedItem()) || 
				isBusy((String) this.team_1Combo.getSelectedItem()) ||
				isStadiumBusy((String) this.stadiumComboModel.getSelectedItem())) {
			this.okButton.setEnabled(false);
			System.out.println("Illegal settings!");
		}
		System.out.println("correctDay: " + correctDay + ", comboEmpty: " + comboEmpty);
		System.out.println((String) team_1Combo.getSelectedItem() + " is busy? " + isBusy((String) this.team_1Combo.getSelectedItem()));
		System.out.println((String) team_2Combo.getSelectedItem() + " is busy? " + isBusy((String) this.team_2Combo.getSelectedItem()));
		System.out.println("The selected stadium is busy? " + isStadiumBusy((String) this.stadiumComboModel.getSelectedItem()));
	}

	/**
	 * Create and add the match to the selected stadium
	 */
	private void addMatch() {
		Match match = new Match(new DateTime((int) this.yearSpinner.getValue(),
				(int) this.monthSpinner.getValue(), 
				(int) this.daySpinner.getValue(), 
				(int) this.hourSpinner.getValue(), 
				(int) this.minuteSpinner.getValue()), 
				structure.getTeamByName((String) this.team_1Combo.getSelectedItem()), 
				structure.getTeamByName((String) this.team_2Combo.getSelectedItem()), 
				structure.getStadiumByName((String) this.stadiumCombo.getSelectedItem()));
		this.structure.getStadiumByName((String) this.stadiumCombo.getSelectedItem()).getMatches().add(match);
		this.matchesTableModel.addRow(new String[] {
				match.getDate().toString("dd MMMM yyyy HH:mm"), 
				match.getTeam_1().getName() + " vs " + match.getTeam_2().getName(), 
				match.getStadium().getName(), String.valueOf(match.getStadium().getCapiency()),
				String.valueOf(match.getID())
		});
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.structure.getStadiumByName((String) this.stadiumCombo.getSelectedItem()).seatsInitializer();
		this.dispose();
	}

}
