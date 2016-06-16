package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stadium.user.User;
import stadium.user.User.ClientKindEnum;

public class UserTest {
	
	private User admin;
	private User client;

	@Before
	public void setUp() throws Exception {
		admin = new User("Admin", "admin");
		client = new User("Client", "client", ClientKindEnum.STUDENT);
	}
	
	// Tests
	
	@Test
	public void testGeneratedObjectShouldBeNotNull(){
		assertNotNull(admin);
		assertNotNull(client);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentsException() {
		User wrongClient = new User("", "pass", ClientKindEnum.STUDENT);
		User wrongAdmin = new User("", "pass");
		User falseAdmin = new User("Liar", "client", ClientKindEnum.ADMIN);
	}
	
	@Test
	public void testUserConstructorWithoutKindShouldMakeAAdminUser() {
		assertTrue(admin.isAdmin());
	}

	@Test
	public void testUserConstructorWithKindShouldMakeAClientUser() {
		assertFalse(client.isAdmin());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentsExceptionInKindSetter() {
		client.setKind(ClientKindEnum.ADMIN);
	}

	@Test
	public void testToString() {
		assertNotEquals(client.toString(), "");
		assertNotEquals(admin.toString(), "");
	}

	@Test
	public void testClonedObjectShouldBeEqualsToTheOriginalObject() {
		User adminClone = (User) admin.clone();
		User adminCopy = new User("Admin", "admin");
		assertTrue(admin.equals(adminClone));
		assertTrue(admin.equals(adminCopy));
	}

}
