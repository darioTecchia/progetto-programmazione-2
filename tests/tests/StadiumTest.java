package tests;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import stadium.exception.ItemNotFoundException;
import stadium.match.Match;
import stadium.seat.Seat;
import stadium.seat.Seat.SeatEnum;
import stadium.stadium.Stadium;
import stadium.team.Team;

public class StadiumTest {
	
	private Stadium stadium;
	
	private Match match;
	
	private Team team1;
	private Team team2;
	
	private DateTime matchDate;
	
	@Before
	public void setUp() {
		matchDate = new DateTime(2016, 01, 01, 10, 00);
		
		stadium = new Stadium("San Siro",	30, 10);
		
		team1 = new Team("Napoli");
		team2 = new Team("Juve");
		
		match = new Match(matchDate, team1, team2, stadium);
		
	}

	@Test
	public void testStadium() {
		assertNotNull(stadium);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void testGetMatchesByIdWithoutTheMatch() {
		stadium.getMatchesByID(match.getID());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void testGetMatchesByIdWithWrongID() {
		stadium.getMatchesByID(123456);
	}

	@Test
	public void testGetMatchesByID() {
		stadium.getMatches().add(match);
		assertEquals(stadium.getMatchesByID(match.getID()), match);
	}

	@Test
	public void testSeatsInitializer() {
		for(Match match: stadium.getMatches()) {
			for(Seat seat: stadium.getSeats()) {
				assertEquals(SeatEnum.AVAIABLE, seat.getStatuses().get(match.getDate()));
				assertNull(seat.getStatuses().get(match.getDate()));
			}
		}
	}

	@Test
	public void testToString() {
		assertNotEquals(stadium.toString(), "");
	}

	@Test
	public void testClonedObjectShouldBeEqualsToTheOriginalObject() {
		Stadium stadiumClone = (Stadium) stadium.clone();
		assertTrue(stadium.equals(stadiumClone));
	}

}
