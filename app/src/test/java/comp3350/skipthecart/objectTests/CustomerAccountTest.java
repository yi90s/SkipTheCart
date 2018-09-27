package comp3350.skipthecart.objectTests;

import org.junit.Test;

import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.objects.Customer;

import static org.junit.Assert.assertEquals;

public class CustomerAccountTest
{
	private CreditCard dummyPaymentMethod = createDummyPaymentMethod();
	private Customer dummyAcc = createDummyAccount();

	@Test
	public void testFirstName()
	{
		assertEquals( "John", dummyAcc.getFirstName() );
		dummyAcc.setFirstName( "Bob" );
		assertEquals( "Bob", dummyAcc.getFirstName() );
	}

	@Test
	public void testLastName()
	{
		assertEquals( "Smith", dummyAcc.getLastName() );
		dummyAcc.setLastName( "Meep" );
		assertEquals( "Meep", dummyAcc.getLastName() );
	}

	@Test
	public void testAddress()
	{
		assertEquals( "123 Main st.", dummyAcc.getAddress() );
		dummyAcc.setAddress( "1 Test ave." );
		assertEquals( "1 Test ave.", dummyAcc.getAddress() );
	}

	@Test
	public void testEmail()
	{
		assertEquals( "jsmith@email.com", dummyAcc.getEmail() );
		dummyAcc.setEmail( "bMeep@email.com" );
		assertEquals( "bMeep@email.com", dummyAcc.getEmail() );
	}

	@Test
	public void testPhoneNumber()
	{
		assertEquals( "1234567", dummyAcc.getPhoneNum() );
		dummyAcc.setPhoneNum( "9999999" );
		assertEquals( "9999999", dummyAcc.getPhoneNum() );
	}

	@Test
	public void testUserID()
	{
		assertEquals( "1234", dummyAcc.getUserID() );
		dummyAcc.setUserID( "9999" );
		assertEquals( "9999", dummyAcc.getUserID() );
	}

	@Test
	public void testPassword()
	{
		assertEquals( "password", dummyAcc.getPassword() );
		dummyAcc.setPassword( "boo-yah!" );
		assertEquals( "boo-yah!", dummyAcc.getPassword() );
	}

	@Test
	public void testPaymentMethod()
	{
		dummyAcc.setPaymentMethod( dummyPaymentMethod );
		assertEquals( dummyPaymentMethod, dummyAcc.getPaymentMethod() );
	}

	private CreditCard createDummyPaymentMethod()
	{
		return new CreditCard( "John Smith", "1234-5678-1234-5678", "111", 1, 2001 );
	}

	private Customer createDummyAccount()
	{
		return new Customer( "John", "Smith", "123 Main st.", "jsmith@email.com", "1234567", "1234",
		                     "password" );
	}
}
