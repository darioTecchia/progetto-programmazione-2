package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stadium.team.Team;

public class TeamTest {
	
	private Team team;

	@Before
	public void setUp() throws Exception {
		team = new Team("Salerno");
	}

	@Test
	public void testTeam() {
		assertNotNull(team);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentsException() {
		Team wrongTeam = new Team("");
		Team wrongTeam2 = new Team("    What's wrong with me?");
	}

	@Test
	public void testToString() {
		assertNotEquals(team.toString(), "");
	}

	@Test
	public void testClonedObjectShouldBeEqualsToTheOriginalObject() {
		Team teamClone = (Team) team.clone();
		assertTrue(team.equals(teamClone));
	}

}
