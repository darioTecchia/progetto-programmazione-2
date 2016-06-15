package stadium.match;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Comparator;

import javax.swing.JButton;
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
 * The Class AdminMatchesViewPanel.
 */
@SuppressWarnings("serial")
public class AdminMatchesViewPanel extends JPanel {

	private Structure structure;

	private JTable matchesTable;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;

	private JButton addNewMatchButton;
	private JButton deleteMatch;
	private JPanel buttonPanel;

	/**
	 * Instantiates a new admin's matches view panel.
	 *
	 * @param structure the main structure
	 */
	public AdminMatchesViewPanel(Structure structure) {
		this.structure = structure;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setLayout(new BorderLayout());

		this.tableModel = new DefaultTableModel(
				new String[][]{}, 
				new String[]{ "Data", "Team", "Stadio", "Capacità", "ID" }
				);
		for(Stadium stadium: this.structure.getStadiums()) {
			System.out.println("Reading matches from " + stadium.getName());

			for(Match match: stadium.getMatches()) {
				if(match.isFinished()) {
					stadium.seatsEntryCleaner();
					continue;
				}
				else {
					System.out.println("Loaded match " + match.getTeam_1().getName() + " vs " + match.getTeam_2().getName());
					tableModel.addRow(
							new String[] {
									match.getDate().toString("EEEE dd MMMM yyyy HH:mm"), 
									match.getTeam_1().getName() + " vs " + match.getTeam_2().getName(), 
									stadium.getName(), String.valueOf(stadium.getCapiency()),
									String.valueOf(match.getID())
							});					
				}
			}

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

		this.scrollPane = new JScrollPane(matchesTable);
		this.add(this.scrollPane, BorderLayout.CENTER);

		this.buttonPanel = new JPanel(new GridLayout(1, 2));
		this.add(this.buttonPanel, BorderLayout.SOUTH);

		this.addNewMatchButton = new JButton("Aggiungi nuova partita");
		this.addNewMatchButton.addActionListener(l -> new AdminMatchCreateView(this.structure, this.tableModel));
		this.buttonPanel.add(this.addNewMatchButton);

		this.deleteMatch = new JButton("Elimina Partita");
		this.deleteMatch.addActionListener(l -> deleteMatch(Integer.parseInt((String) this.matchesTable.getValueAt(this.matchesTable.getSelectedRow(), 4))));
		this.buttonPanel.add(this.deleteMatch);
	}

	/**
	 * Delete the match with this ID from the stadium.
	 *
	 * @param ID the match's ID
	 */
	private void deleteMatch(int ID) {
		Stadium tempStadium = this.structure.getStadiumByName((String)this.matchesTable.getValueAt(this.matchesTable.getSelectedRow(), 2));
		tempStadium.getMatches().remove(tempStadium.getMatchesByID(ID));
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.tableModel.removeRow(this.matchesTable.getSelectedRow());
	}


}
