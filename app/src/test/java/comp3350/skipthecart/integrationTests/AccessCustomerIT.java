package comp3350.skipthecart.integrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.CustomerException;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.utils.TestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AccessCustomerIT
{
	private File tempDB;
	private AccessCustomer accessCustomer;
	private Customer dummyCustomer;
	private Customer dummyUpdated;
	private String dummyUserID;
	private String dummyPassword;
	private String dummyAddress;
	private String dummyEmail;
	private String dummyFirstName;
	private String dummyLastName;
	private String dummyPhoneNum;

	@Before
	public void setUp() throws IOException
	{
		tempDB = TestUtils.copyDB();
		accessCustomer = new AccessCustomer();
		dummyFirstName = "John";
		dummyLastName = "Smith";
		dummyAddress = "123 Home St.";
		dummyEmail = "home@home.com";
		dummyPhoneNum = "2045555555";
		dummyUserID = "john_smith2018";
		dummyPassword = "password";
		dummyCustomer = new Customer( dummyFirstName, dummyLastName, dummyAddress, dummyEmail,
		                              dummyPhoneNum, dummyUserID, dummyPassword );
		dummyUpdated = new Customer( "Test", "This", dummyAddress, dummyEmail, dummyPhoneNum,
		                             dummyUserID, dummyPassword );
	}

	@Test( expected = CustomerException.class )
	public void testAccessCustomerPersistence()
	{
		Customer resultCustomer;

		assertNull( accessCustomer.getCustomer( dummyUserID, dummyPassword ) );
		assertTrue( accessCustomer.addCustomer( dummyCustomer ) );

		resultCustomer = accessCustomer.getCustomer( dummyUserID, dummyPassword );

		assertNotNull( resultCustomer );

		assertTrue( resultCustomer.getFirstName().equals( dummyFirstName ) );
		assertTrue( resultCustomer.getLastName().equals( dummyLastName ) );
		assertTrue( resultCustomer.getAddress().equals( dummyAddress ) );
		assertTrue( resultCustomer.getEmail().equals( dummyEmail ) );
		assertTrue( resultCustomer.getPhoneNum().equals( dummyPhoneNum ) );
		assertTrue( resultCustomer.getUserID().equals( dummyUserID ) );
		assertTrue( resultCustomer.getPassword().equals( dummyPassword ) );

		assertTrue( accessCustomer.updateCustomerInfo( dummyCustomer, dummyUpdated ) );

		resultCustomer = accessCustomer.getCustomer( dummyUserID, dummyPassword );

		assertNotNull( resultCustomer );

		assertTrue( resultCustomer.getFirstName().equals( "Test" ) );
		assertTrue( resultCustomer.getLastName().equals( "This" ) );
		assertTrue( resultCustomer.getAddress().equals( dummyAddress ) );
		assertTrue( resultCustomer.getEmail().equals( dummyEmail ) );
		assertTrue( resultCustomer.getPhoneNum().equals( dummyPhoneNum ) );
		assertTrue( resultCustomer.getUserID().equals( dummyUserID ) );
		assertTrue( resultCustomer.getPassword().equals( dummyPassword ) );

		accessCustomer.addCustomer( dummyCustomer );
	}

	@After
	public void tearDown()
	{
		tempDB.delete();
	}
}
