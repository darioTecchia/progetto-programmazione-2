package tests;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import stadium.match.Match;
import stadium.seat.Seat;
import stadium.stadium.Stadium;
import stadium.team.Team;
import stadium.ticket.Ticket;

public class TicketTest {

	private Match match;

	private DateTime matchDate;
	private Team team1;
	private Team team2;
	private Stadium stadium;
	private	Ticket ticket;
	private Seat seat;

	@Before
	public void setUp() throws Exception {
		matchDate = new DateTime(DateTime.now().getMillis() + 1000*60*60*10);
		team1 		= new Team("Napoli");
		team2 		= new Team("Juventus");
		stadium 	= new Stadium("San Paolo", 20, 5.60);
		match 		= new Match(matchDate, team1, team2, stadium);
		seat 			= new Seat();
		ticket 		= new Ticket(match, seat);
	}

	@Test
	public void testTicket() {
		assertNotNull(ticket);
	}

	@Test
	public void testIsExpired() {
		assertTrue(ticket.isExpired());
	}

	@Test
	public void testToString() {
		assertNotEquals(ticket.toString(), "");
	}

	@Test
	public void testClonedObjectShouldBeEqualsToTheOriginalObject() {
		Ticket ticketClone = (Ticket) ticket.clone();
		assertTrue(ticket.equals(ticketClone));
	}

}
