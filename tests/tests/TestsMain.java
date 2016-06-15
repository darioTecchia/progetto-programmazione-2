package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.junit.Test;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	MatchTest.class,
	SalePolicyTest.class,
	UserTest.class,
	SeatTest.class,
	StadiumTest.class,
	TeamTest.class,
	TicketTest.class,
	UserTest.class
})

public class TestsMain {

	@Test
	public void test() {
	}

}
