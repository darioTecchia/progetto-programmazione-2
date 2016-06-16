package stadium.ticket;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import stadium.match.ClientPurchaseTicketView;
import stadium.structure.Structure;
import stadium.user.User;

/**
 * The Class ClientPurchasesPanel.
 */
@SuppressWarnings("serial")
public class ClientPurchasesPanel extends JPanel {

	private Structure structure;
	private User user;

	private JPanel ticketsPanel;

	private JScrollPane ticketsScrollPane;

	/**
	 * Instantiates a new client purchases panel.
	 *
	 * @param structure the main structure
	 */
	public ClientPurchasesPanel(Structure structure) {
		this.structure 	= structure;
		this.user 			= structure.getCurrentUser();
		initialize();
	}

	/**
	 * The Class TicketComponent.
	 */
	private class TicketComponent extends JPanel {

		private Structure structure;
		private Ticket ticket;

		private JPanel matchInfoPanel;
		
		private JLabel teamsLabel;
		
		private JLabel dateLabel;
		
		private JLabel stadiumLabel;

		private JButton goToMatchButton;

		/**
		 * Instantiates a new ticket component.
		 *
		 * @param ticket the ticket
		 * @param structure the main structure
		 */
		public TicketComponent(Ticket ticket, Structure structure) {
			this.structure 	= structure;
			this.ticket 		= ticket;
			initialize();
		}

		/**
		 * Initialize GUI.
		 */
		private void initialize() {
			this.setLayout(new BorderLayout());
			this.setBorder(new TitledBorder(this.ticket.getTeams()));

			this.matchInfoPanel = new JPanel(new GridLayout(3, 1));
			this.teamsLabel = new JLabel(this.ticket.getMatch().getTeam_1().getName() + " vs " + this.ticket.getMatch().getTeam_2().getName());
			this.matchInfoPanel.add(this.teamsLabel);
			this.dateLabel = new JLabel(this.ticket.getMatch().getDate().toString("EEEE dd MMMM yyyy HH:mm"));
			this.matchInfoPanel.add(this.dateLabel);
			this.stadiumLabel = new JLabel(this.ticket.getMatch().getStadium().getName());
			this.matchInfoPanel.add(this.stadiumLabel);
			this.add(this.matchInfoPanel, BorderLayout.CENTER);

			this.goToMatchButton = new JButton("Vai Al Match");
			this.goToMatchButton.addActionListener(l -> new ClientPurchaseTicketView(this.ticket.getMatch(), this.structure));
			this.add(this.goToMatchButton, BorderLayout.EAST);
		}
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {
		this.setLayout(new GridLayout(1, 1));
		this.ticketsPanel = new JPanel(new GridLayout(this.user.getPurchasedTickets().size()*2, 1));

		ArrayList<Ticket> ticketToDelete = new ArrayList<Ticket>();
		
		for(Ticket ticket: this.user.getPurchasedTickets()) {
			if(ticket.getMatch().isFinished() || !ticket.getStadium().getMatches().contains(ticket.getMatch())) {
				ticketToDelete.add(ticket);
				continue;
			}
			else {
				this.ticketsPanel.add(new TicketComponent(ticket, this.structure));
			}
		}
		this.user.getPurchasedTickets().removeAll(ticketToDelete);
		this.ticketsScrollPane = new JScrollPane();
		this.ticketsScrollPane.getViewport().add(this.ticketsPanel);
		this.add(this.ticketsScrollPane);
	}

}
