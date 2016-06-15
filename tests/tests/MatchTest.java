package tests;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import stadium.match.Match;
import stadium.stadium.Stadium;
import stadium.team.Team;

public class MatchTest {
	
	private Match match;
	
	private DateTime matchDate;
	
	private Team team1;
	private Team team2;
	
	private Stadium stadium;
	
	@Before
	public void setUp() throws Exception {
		matchDate = new DateTime(2016, 01, 01, 10, 00);
		
		team1 = new Team("Napoli");
		team2 = new Team("Juventus");
		
		stadium = new Stadium("San Paolo", 20, 5.60);
		
		match = new Match(matchDate, team1, team2, stadium);
	}
	
	//Tests

	@Test
	public void testMatch() {
		assertNotNull(match);
	}
	
	@Test
	public void testToString() {
		assertNotEquals(stadium.toString(), "");
	}

	@Test
	public void testClonedObjectShouldBeEqualsToTheOriginalObject() {
		Stadium adminClone = (Stadium) stadium.clone();
		assertTrue(stadium.equals(adminClone));
	}

}
