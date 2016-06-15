package stadium.match;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import stadium.salepolicy.SalePolicy;
import stadium.seat.Seat;
import stadium.seat.Seat.SeatEnum;
import stadium.structure.Structure;
import stadium.ticket.Ticket;

/**
 * The Class ClientPurchaseTicketView.
 */
@SuppressWarnings("serial")
public class ClientPurchaseTicketView extends JFrame {

	private Structure structure;
	private Match match;
	private Seat selectedSeat;

	private JButton selectedSeatButton;

	private JPanel stadiumSeatsPanel;

	private JPanel matchInfoPanel;
	private JLabel teamsLabel;
	private JLabel stadiumLabel;
	private JLabel dateLabel;

	private JButton purchaseButton;
	private JButton preorderButton;
	private JButton deletePrenotation;
	private JLabel totalPrice;

	private	ArrayList<Double> possiblePrices;


	/**
	 * Instantiates a new client purchase ticket view.
	 *
	 * @param match the match
	 * @param structure the main structure
	 */
	public ClientPurchaseTicketView(Match match, Structure structure) {
		this.structure 	= structure;
		this.match 			= match;
		initialize();
	}

	/**
	 * Initialize GUI.
	 */
	private void initialize() {

		this.setTitle("Acquista o Prenota un biglietto per il match " + match.getTeam_1().getName() + " vs " + match.getTeam_2().getName());
		this.getContentPane().setLayout(new BorderLayout());
		this.setVisible(true);
		this.add(new JLabel("Clicca su una poltrona disponibile e poi clicca su 'Prenota/Compra il posto'"), BorderLayout.NORTH);

		int seatsRow = this.match.getStadium().getCapiency()%10 == 0 ? this.match.getStadium().getCapiency()/10 : this.match.getStadium().getCapiency()/10 + 1;
		this.stadiumSeatsPanel = new JPanel(new GridLayout(seatsRow, 10));

		Icon chairIcon = new ImageIcon("icon/chair.png");
		for(Seat seat: this.match.getStadium().getSeats()) {
			JButton chairButton = new JButton(chairIcon);
			switch (seat.getStatuses().get(this.match.getDate())) {
			case AVAIABLE:
				chairButton.setBackground(new Color(38, 166, 91));
				break;
			case NOT_AVAIABLE:
				chairButton.setEnabled(false);
				chairButton.setBackground(new Color(207, 0, 15));
				break;
			case PRENOATED:
				if(seat.getUsers().get(this.match.getDate()).equals(this.structure.getCurrentUser())) {
					chairButton.setBackground(new Color(230, 126, 34));
					chairButton.setEnabled(true);
				}
				else {
					chairButton.setEnabled(false);
					chairButton.setBackground(new Color(244, 208, 63));
				}
				break;
			}
			chairButton.addActionListener(l -> selectSeat(seat, chairButton));
			this.stadiumSeatsPanel.add(chairButton);

		}
		this.add(this.stadiumSeatsPanel, BorderLayout.CENTER);

		this.matchInfoPanel = new JPanel(new GridLayout(7, 1));
		this.add(this.matchInfoPanel, BorderLayout.EAST);
		this.teamsLabel = new JLabel(this.match.getTeam_1().getName() + " vs " + this.match.getTeam_2().getName());
		this.matchInfoPanel.add(this.teamsLabel);
		this.stadiumLabel = new JLabel(this.match.getStadium().getName());
		this.matchInfoPanel.add(this.stadiumLabel);
		this.dateLabel = new JLabel(this.match.getDate().toString(DateTimeFormat.forPattern("dd MMMM yyyy HH:mm")));
		this.matchInfoPanel.add(this.dateLabel);
		this.purchaseButton = new JButton("Compra biglietto");
		this.purchaseButton.setEnabled(false);
		this.purchaseButton.addActionListener(l -> purchaseTicket());
		this.matchInfoPanel.add(this.purchaseButton);
		this.preorderButton = new JButton("Prenota il posto");
		this.preorderButton.setEnabled(false);
		this.preorderButton.addActionListener(l -> prenoteTicket());
		if(DateTime.now().getMillis() >= this.match.getDate().getMillis() - 1000*60*60*12){
			this.preorderButton.setEnabled(false);
		}
		this.matchInfoPanel.add(this.preorderButton);
		this.deletePrenotation = new JButton("Elimina prenotazione");
		this.deletePrenotation.setEnabled(false);
		this.deletePrenotation.addActionListener(l -> deletePrenotation());
		this.matchInfoPanel.add(this.deletePrenotation);


		this.possiblePrices = new ArrayList<Double>();
		if(this.structure.isSaleDayActivated() && this.structure.getSaleDay() == DateTime.now().getDayOfWeek()) {
			possiblePrices.add(SalePolicy.calcPerc(this.match.getStadium().getPricePerMatch(), this.structure.getDiscountPercentage()));
		}
		if(this.match.getStadium().isActiveSale()) {
			possiblePrices.add(SalePolicy.calcPerc(this.match.getStadium().getPricePerMatch(), this.match.getStadium().getDiscountPercentage()));
		}
		if(this.structure.getDiscountPolicyByKind(this.structure.getCurrentUser().getKind()).isActived()) {
			possiblePrices.add(SalePolicy.calcPerc(this.match.getStadium().getPricePerMatch(), this.structure.getCurrentUser().getKind().getValue()));	
		}
		this.possiblePrices.add(this.match.getStadium().getPricePerMatch());
		Collections.sort(possiblePrices);

		this.totalPrice = new JLabel("Prezzo totale: " + possiblePrices.get(0));
		this.matchInfoPanel.add(this.totalPrice);

		this.repaint();
		this.revalidate();
		this.pack();		
	}

	/**
	 * This function allow to hold what seat the client select.
	 *
	 * @param seat the selected seat seat
	 * @param chairButton the selected chair button
	 */
	private void selectSeat(Seat seat, JButton chairButton) {
		this.selectedSeat = seat;
		this.selectedSeatButton = chairButton;
		this.purchaseButton.setEnabled(true);
		if(DateTime.now().getMillis() < this.match.getDate().getMillis() - 1000*60*60*12){
			this.preorderButton.setEnabled(true);
		}
		if(!seat.getStatuses().get(this.match.getDate()).equals(SeatEnum.PRENOATED)) {
			this.deletePrenotation.setEnabled(false);
		}
		if(seat.getStatuses().get(this.match.getDate()).equals(SeatEnum.PRENOATED) && this.structure.getCurrentUser().equals(seat.getUsers().get(this.match.getDate()))) {
			this.preorderButton.setEnabled(false);
			this.deletePrenotation.setEnabled(true);
		}
		else if(seat.getStatuses().get(this.match.getDate()).equals(SeatEnum.PRENOATED) && !this.structure.getCurrentUser().equals(seat.getUsers().get(this.match.getDate()))) {
			this.preorderButton.setEnabled(true);
		}
	}

	/**
	 * Delete a user's prenotation.
	 */
	private void deletePrenotation() {

		this.selectedSeat.getStatuses().put(this.match.getDate(), SeatEnum.AVAIABLE);
		this.selectedSeat.getUsers().put(this.match.getDate(), null);
		this.selectedSeatButton.setBackground(new Color(38, 166, 91));
		this.match.setAvaiableSeat(this.match.getAvaiableSeat() + 1);
		Ticket tempTicket = new Ticket(this.match, this.selectedSeat);
		for(Ticket preorderTicket: this.structure.getCurrentUser().getPreOrderedTickets()) {
			if(preorderTicket.getMatch().equals(tempTicket.getMatch()) && preorderTicket.getSeat().equals(tempTicket.getSeat())) {
				this.structure.getCurrentUser().getPreOrderedTickets().remove(preorderTicket);
				break;
			}
		}
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Purchase a ticket.
	 */
	private void purchaseTicket() {

		if(this.selectedSeat.getStatuses().get(this.match.getDate()).equals(SeatEnum.PRENOATED)) {
			Ticket tempTicket = new Ticket(this.match, this.selectedSeat);
			for(Ticket preorderTicket: this.structure.getCurrentUser().getPreOrderedTickets()) {
				if(preorderTicket.getMatch().equals(tempTicket.getMatch()) && preorderTicket.getSeat().equals(tempTicket.getSeat())) {
					this.structure.getCurrentUser().getPreOrderedTickets().remove(preorderTicket);
					break;
				}
			}
		}
		else if(!this.selectedSeat.getStatuses().get(this.match.getDate()).equals(SeatEnum.PRENOATED)) {
			this.match.setAvaiableSeat(this.match.getAvaiableSeat() - 1);
			this.selectedSeat.getUsers().put(this.match.getDate(), this.structure.getCurrentUser());
		}
		this.selectedSeat.getStatuses().put(this.match.getDate(), SeatEnum.NOT_AVAIABLE);
		this.structure.getCurrentUser().getPurchasedTickets().add(new Ticket(this.match, this.selectedSeat));
		this.selectedSeatButton.setBackground(new Color(207, 0, 15));
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.selectedSeatButton.setEnabled(false);
		this.purchaseButton.setEnabled(false);
		this.match.getStadium().setTaking(this.match.getStadium().getTaking() + this.possiblePrices.get(0));

	}

	/**
	 * Prenote a ticket.
	 */
	private void prenoteTicket() {

		this.selectedSeat.getStatuses().put(this.match.getDate(), SeatEnum.PRENOATED);
		this.match.setAvaiableSeat(this.match.getAvaiableSeat() - 1);
		this.selectedSeat.getUsers().put(this.match.getDate(), this.structure.getCurrentUser());
		this.structure.getCurrentUser().getPreOrderedTickets().add(new Ticket(this.match, this.selectedSeat));
		this.selectedSeatButton.setBackground(new Color(230, 126, 34));
		try {
			this.structure.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.preorderButton.setEnabled(false);
	}

}
