package comp3350.skipthecart.logicTests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.EmployeeException;
import comp3350.skipthecart.logic.EmployeeValidator;
import comp3350.skipthecart.objects.Employee;

public class EmployeeValidatorTest
{
	private static EmployeeValidator employeeValidator;
	private Employee dummyEmployee;

	@BeforeClass
	public static void setupForAll()
	{
		employeeValidator = new EmployeeValidator();
		Services.setStubs( true );
	}

	@Before
	public void setupForEach()
	{
		dummyEmployee = new Employee( "test", "test", "test", "test", "test", "test", "test",
		                              "test", "test" );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyFirstName()
	{
		dummyEmployee.setFirstName( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongFirstName()
	{
		dummyEmployee.setFirstName( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyLastName()
	{
		dummyEmployee.setLastName( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongLastName()
	{
		dummyEmployee.setLastName( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyAddress()
	{
		dummyEmployee.setAddress( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongAddress()
	{
		dummyEmployee.setAddress( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyEmail()
	{
		dummyEmployee.setEmail( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongEmail()
	{
		dummyEmployee.setEmail( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyPhoneNum()
	{
		dummyEmployee.setPhoneNum( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongPhoneNum()
	{
		dummyEmployee.setPhoneNum( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyUserID()
	{
		dummyEmployee.setUserID( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongUserID()
	{
		dummyEmployee.setUserID( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyPassword()
	{
		dummyEmployee.setPassword( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongPassword()
	{
		dummyEmployee.setPassword( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyEmployeeNum()
	{
		dummyEmployee.setEmployeeNum( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongEmployeeNum()
	{
		dummyEmployee.setEmployeeNum( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testEmptyPosition()
	{
		dummyEmployee.setPosition( "" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testLongPosition()
	{
		dummyEmployee.setPosition( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
		employeeValidator.validate( dummyEmployee );
	}

	@Test( expected = EmployeeException.class )
	public void testNullEmployee()
	{
		employeeValidator.validate( null );
	}
}
