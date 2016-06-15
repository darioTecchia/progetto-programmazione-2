package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stadium.salepolicy.SalePolicy;
import stadium.user.User.ClientKindEnum;

public class SalePolicyTest {
	
	private SalePolicy salePolicy;

	@Before
	public void setUp() throws Exception {
		salePolicy = new SalePolicy(ClientKindEnum.STUDENT, true);
	}

	@Test
	public void testSalePolicy() {
		assertNotNull(salePolicy);
	}

	@Test
	public void testCalculateFianlPriveGivenAPercentage() {
		assertEquals(50.0, SalePolicy.calcPerc(100.0, 50), 0);
		assertNotEquals(20.0, SalePolicy.calcPerc(100.0, 50));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCalcPercArgumentsShouldNotLessThanZero() {
		SalePolicy.calcPerc(-2.0, 50);
		SalePolicy.calcPerc(20, -4);
	}

	@Test
	public void testToString() {
		assertNotEquals(salePolicy.toString(), "");
	}

	@Test
	public void testClonedObjectShouldBeEqualsToTheOriginalObject() {
		SalePolicy saleClone = (SalePolicy) salePolicy.clone();
		assertTrue(saleClone.equals(saleClone));
	}

}
