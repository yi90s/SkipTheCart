package comp3350.skipthecart.logicTests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.CustomerException;
import comp3350.skipthecart.logic.CustomerValidator;
import comp3350.skipthecart.objects.Customer;

public class CustomerValidatorTest
{
	private static CustomerValidator customerValidator;
	private Customer dummyCustomer;

	@BeforeClass
	public static void setupForAll()
	{
		customerValidator = new CustomerValidator();
		Services.setStubs( true );
	}

	@Before
	public void setupForEach()
	{
		dummyCustomer = new Customer( "test", "test", "test", "test", "test", "test", "test" );
	}

	@Test( expected = CustomerException.class )
	public void testEmptyFirstName()
	{
		dummyCustomer.setFirstName( "" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testLongFirstName()
	{
		dummyCustomer.setFirstName( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testEmptyLastName()
	{
		dummyCustomer.setLastName( "" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testLongLastName()
	{
		dummyCustomer.setLastName( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testEmptyAddress()
	{
		dummyCustomer.setAddress( "" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testLongAddress()
	{
		dummyCustomer.setAddress( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testEmptyEmail()
	{
		dummyCustomer.setEmail( "" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testLongEmail()
	{
		dummyCustomer.setEmail( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testEmptyPhoneNum()
	{
		dummyCustomer.setPhoneNum( "" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testLongPhoneNum()
	{
		dummyCustomer.setPhoneNum( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testEmptyUserID()
	{
		dummyCustomer.setUserID( "" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testLongUserID()
	{
		dummyCustomer.setUserID( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testEmptyPassword()
	{
		dummyCustomer.setPassword( "" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testLongPassword()
	{
		dummyCustomer.setPassword( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		customerValidator.validate( dummyCustomer );
	}

	@Test( expected = CustomerException.class )
	public void testNullCustomer()
	{
		customerValidator.validate( null );
	}
}
