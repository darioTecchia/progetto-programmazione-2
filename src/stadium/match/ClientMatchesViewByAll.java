package stadium.match;

import java.awt.BorderLayout;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import stadium.stadium.Stadium;
import stadium.structure.Structure;

/**
 * The Class ClientMatchesViewByAll.
 */
@SuppressWarnings("serial")
public class ClientMatchesViewByAll extends JPanel {

	private Structure structure;

	private JLabel selectAMatchLabel;
	
	private DefaultTableModel matchesTableModel;
	private JScrollPane tableScrollPane;
	private JTable matchesTable;
	
	JButton purchaseButton;

	/**
	 * Instantiates a new client matches view by all.
	 *
	 * @param structure the main structure
	 */
	public ClientMatchesViewByAll(Structure structure) {
		this.structure = structure;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new BorderLayout());

		this.selectAMatchLabel = new JLabel("Seleziona una partita nella tabella");
		this.add(this.selectAMatchLabel, BorderLayout.NORTH);

		this.matchesTableModel = new DefaultTableModel(
				new String[][]{},
				new String[]{ "Data", "Team", "Stadio", "Posti Rimanenti", "Prezzo", "ID" }
				);
		for(Stadium stadium: this.structure.getStadiums()) {
			for(Match match: stadium.getMatches()) {
				if(match.isFinished()) {
					stadium.seatsEntryCleaner();
					continue;
				}
				this.matchesTableModel.addRow(
						new String[] {
								match.getDate().toString("EEEE dd MMMM yyyy HH:mm"),
								match.getTeam_1().getName() + " vs " + match.getTeam_2().getName(),
								match.getStadium().getName(),
								String.valueOf(match.getAvaiableSeat()),
								String.valueOf(match.getStadium().getPricePerMatch()),
								String.valueOf(match.getID())
						}
						);
			}
		}
		
		this.matchesTable = new JTable(this.matchesTableModel);
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
		
		this.tableScrollPane = new JScrollPane(this.matchesTable);
		this.add(this.tableScrollPane, BorderLayout.CENTER);

		this.purchaseButton = new JButton("Acquista/Prenota un biglietto");
		this.purchaseButton.addActionListener(l -> viewMatch(Integer.parseInt((String) this.matchesTable.getValueAt(this.matchesTable.getSelectedRow(), 5))));
		this.add(this.purchaseButton, BorderLayout.SOUTH);

	}

	/**
	 * Open a new match window.
	 *
	 * @param ID the match's ID
	 */
	private void viewMatch(int ID) {
		Match selectedMatch = this.structure.getStadiumByName((String)this.matchesTable.getValueAt(this.matchesTable.getSelectedRow(), 2)).getMatchesByID(ID);
		new ClientPurchaseTicketView(selectedMatch, this.structure);
	}

}


