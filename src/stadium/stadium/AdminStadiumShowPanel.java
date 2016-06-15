package stadium.stadium;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import stadium.match.Match;
import stadium.structure.Structure;

/**
 * The Class AdminStadiumShowPanel.
 */
@SuppressWarnings("serial")
public class AdminStadiumShowPanel extends JPanel {

	private Structure structure;
	private Stadium stadium;

	private JTabbedPane stadiumsTabs;

	private JPanel infoPanel;

	private JLabel stadiumName;

	private JPanel stadiumSettings;

	private JSpinner capiencySpinner;
	private JLabel capiencyLabel;

	private JSpinner priceSpinner;
	private JLabel priceLabel;
	private JLabel takenLabel;

	private JPanel matchesTablePanel;

	private JScrollPane scrollPane;
	private JTable matchesTable;

	private JPanel buttonPanel;
	private JButton saveButton;
	private JButton removeButton;

	/**
	 * Instantiates a new admin stadium show panel.
	 *
	 * @param stadium the stadium that will be showed
	 * @param structure the main structure
	 * @param stadiumsTabs the stadiums tabs
	 */
	public AdminStadiumShowPanel(Stadium stadium, Structure structure, JTabbedPane stadiumsTabs) {
		this.stadiumsTabs = stadiumsTabs;
		this.structure 		= structure;
		this.stadium 			= stadium;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		this.setLayout(new GridLayout(2, 1));

		this.infoPanel = new JPanel(new BorderLayout());
		this.add(infoPanel);

		this.stadiumName = new JLabel(stadium.getName());
		this.stadiumName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.stadiumName.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(this.stadiumName, BorderLayout.NORTH);

		this.stadiumSettings = new JPanel(new GridLayout(3, 2));
		this.infoPanel.add(stadiumSettings);

		this.capiencyLabel = new JLabel("Capienza");
		this.capiencyLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.stadiumSettings.add(capiencyLabel);

		this.capiencySpinner = new JSpinner(new SpinnerNumberModel(this.stadium.getCapiency(), 0, 9999, 1));
		this.stadiumSettings.add(capiencySpinner);

		this.priceLabel = new JLabel("Prezzo per partita");
		this.priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.stadiumSettings.add(priceLabel);

		this.priceSpinner = new JSpinner(new SpinnerNumberModel(this.stadium.getPricePerMatch(), 0, 9999, 1));
		this.stadiumSettings.add(priceSpinner);

		this.takenLabel = new JLabel("Incasso Totale: " + String.valueOf(this.stadium.getTaking()));
		this.takenLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.stadiumSettings.add(takenLabel);

		DefaultTableModel tableModel = new DefaultTableModel(
				new String[][]{},
				new String[]{
						"Data", "Team"
				}
				);
		for(Match match: this.stadium.getMatches()) {
			if(match.isFinished()) {
				stadium.seatsEntryCleaner();
				continue;
			}
			tableModel.addRow(new String[]{
					match.getDate().toString("EEEE dd MMMM yyyy HH:mm"),
					match.getTeam_1().getName() + " vs " + match.getTeam_2().getName()
			});
		}

		this.matchesTable = new JTable(tableModel);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.matchesTable.getModel());
		sorter.setComparator(0, new Comparator<String>() {
			@Override
			public int compare(String time_1, String time_2) {
				DateTime date1 = DateTime.parse(time_1, DateTimeFormat.forPattern("EEEE dd MMMM yyyy HH:mm"));
				DateTime date2 = DateTime.parse(time_2, DateTimeFormat.forPattern("EEEE dd MMMM yyyy HH:mm"));
				return date1.compareTo(date2);
			}
		});
		this.matchesTable.setRowSorter(sorter);
		this.matchesTable.setDragEnabled(false);
		this.matchesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.matchesTablePanel = new JPanel(new BorderLayout());
		this.add(matchesTablePanel);

		this.scrollPane = new JScrollPane(this.matchesTable);
		this.matchesTablePanel.add(scrollPane, BorderLayout.CENTER);

		this.buttonPanel = new JPanel(new GridLayout(1, 2));

		this.saveButton = new JButton("Salva modifiche");
		this.saveButton.addActionListener(l -> updateStadium());
		this.buttonPanel.add(this.saveButton);

		this.removeButton = new JButton("Elimina stadio");
		this.removeButton.addActionListener(l -> deleteStadium(this.stadium));
		this.buttonPanel.add(this.removeButton);

		this.matchesTablePanel.add(this.buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Update the stadium with the new settings.
	 */
	private void updateStadium() {
		this.stadium.setCapiency((int) this.capiencySpinner.getValue());
		this.stadium.setPricePerMatch((double) this.priceSpinner.getValue());
		System.out.println("New settings saved!");
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete stadium.
	 *
	 * @param stadium the stadium we want to delete
	 */
	private void deleteStadium(Stadium stadium) {
		this.structure.getStadiums().remove(stadium);
		this.stadiumsTabs.remove(this);
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
