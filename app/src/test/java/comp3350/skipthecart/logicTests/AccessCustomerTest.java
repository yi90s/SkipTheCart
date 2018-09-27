package comp3350.skipthecart.logicTests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.CustomerException;
import comp3350.skipthecart.logic.CustomerValidator;
import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.persistence.CustomerPersistence;
import comp3350.skipthecart.persistence.PaymentPersistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessCustomerTest
{
	private AccessCustomer accessCustomer;
	private CustomerPersistence customerPersistence;
	private PaymentPersistence paymentPersistence;
	private CustomerValidator customerValidator;
	private Customer dummyCustomer;
	private Customer resultCustomer;
	private String dummyUserID;
	private String dummyPassword;
	private String dummyAddress;
	private String dummyEmail;
	private String dummyFirstName;
	private String dummyLastName;
	private String dummyPhoneNum;
	private CreditCard dummyCreditCard;

	@BeforeClass
	public static void setStubs()
	{
		Services.setStubs( true );
	}

	@Before
	public void setUp()
	{
		customerPersistence = mock( CustomerPersistence.class );
		paymentPersistence = mock( PaymentPersistence.class );
		customerValidator = mock( CustomerValidator.class );
		accessCustomer = new AccessCustomer( customerPersistence, paymentPersistence,
		                                     customerValidator );
		dummyFirstName = "John";
		dummyLastName = "Smith";
		dummyAddress = "123 Home St.";
		dummyEmail = "home@home.com";
		dummyPhoneNum = "2045555555";
		dummyUserID = "john_smith2018";
		dummyPassword = "password";
		dummyCustomer = new Customer( dummyFirstName, dummyLastName, dummyAddress, dummyEmail,
		                              dummyPhoneNum, dummyUserID, dummyPassword );
		dummyCreditCard = new CreditCard( "John Smith", "7777777777777777", "1234", 1, 3000 );
		dummyCustomer.setPaymentMethod( dummyCreditCard );
		resultCustomer = null;
	}

	@Test
	public void testGetCustomer()
	{
		when( customerPersistence.getCustomerFromID( dummyUserID ) ).thenReturn( dummyCustomer );

		resultCustomer = accessCustomer.getCustomer( dummyUserID, dummyPassword );

		assertNotNull( resultCustomer );

		assertTrue( resultCustomer.getFirstName().equals( dummyFirstName ) );
		assertTrue( resultCustomer.getLastName().equals( dummyLastName ) );
		assertTrue( resultCustomer.getAddress().equals( dummyAddress ) );
		assertTrue( resultCustomer.getEmail().equals( dummyEmail ) );
		assertTrue( resultCustomer.getPhoneNum().equals( dummyPhoneNum ) );
		assertTrue( resultCustomer.getUserID().equals( dummyUserID ) );
		assertTrue( resultCustomer.getPassword().equals( dummyPassword ) );

		verify( customerPersistence ).getCustomerFromID( dummyUserID );
	}

	@Test
	public void testGetCustomerById()
	{
		when( customerPersistence.getCustomerFromID( dummyUserID ) ).thenReturn( dummyCustomer );

		resultCustomer = accessCustomer.getCustomerById( dummyUserID );

		assertNotNull( resultCustomer );
		assertTrue( resultCustomer.getUserID().equals( dummyUserID ) );

		verify( customerPersistence ).getCustomerFromID( dummyUserID );
	}

	@Test
	public void testAddNewCustomer()
	{
		when( customerPersistence.getCustomerFromID( dummyUserID ) ).thenReturn( null );
		when( customerPersistence.addCustomer( dummyCustomer ) ).thenReturn( true );

		assertTrue( accessCustomer.addCustomer( dummyCustomer ) );

		verify( customerPersistence ).getCustomerFromID( dummyUserID );
		verify( customerPersistence ).addCustomer( dummyCustomer );
		verify( customerValidator ).validate( dummyCustomer );
	}

	@Test
	public void testAddExistingCustomer()
	{
		when( customerPersistence.getCustomerFromID( dummyUserID ) ).thenReturn( dummyCustomer );

		try
		{
			accessCustomer.addCustomer( dummyCustomer );
			fail();
		}
		catch ( CustomerException ce )
		{
			verify( customerPersistence ).getCustomerFromID( dummyUserID );
		}
	}

	@Test
	public void testUpdateCustomer()
	{
		Customer updated = new Customer( "Test", "This", dummyAddress, dummyEmail, dummyPhoneNum,
		                                 dummyUserID, dummyPassword );

		when( customerPersistence.deleteCustomer( dummyCustomer ) ).thenReturn( true );
		when( customerPersistence.getCustomerFromID( dummyUserID ) ).thenReturn( null );
		when( customerPersistence.addCustomer( updated ) ).thenReturn( true );
		when( paymentPersistence.updateCreditCard( dummyUserID, dummyCreditCard ) ).thenReturn(
			true );

		assertTrue( accessCustomer.updateCustomerInfo( dummyCustomer, updated ) );

		verify( customerPersistence ).deleteCustomer( dummyCustomer );
		verify( customerPersistence ).getCustomerFromID( dummyUserID );
		verify( customerPersistence ).addCustomer( updated );
		verify( customerValidator, times( 2 ) ).validate( updated );
		verify( paymentPersistence ).updateCreditCard( dummyUserID, dummyCreditCard );
	}
}
