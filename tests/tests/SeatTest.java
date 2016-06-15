package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stadium.seat.Seat;

public class SeatTest {
	
	private Seat seat;
	
	@Before
	public void setUp() {
		seat = new Seat();
	}

	@Test
	public void testSeat() {
		assertNotNull(seat);
	}

	@Test
	public void testToString() {
		assertNotEquals(seat.toString(), "");
	}

	@Test
	public void testClonedObjectShouldBeEqualsToTheOriginalObject() {
		Seat seatClone = (Seat) seat.clone();
		assertTrue(seat.equals(seatClone));
	}

}
