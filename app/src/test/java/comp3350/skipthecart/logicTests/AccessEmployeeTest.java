package comp3350.skipthecart.logicTests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.AccessEmployee;
import comp3350.skipthecart.logic.EmployeeException;
import comp3350.skipthecart.logic.EmployeeValidator;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.persistence.EmployeePersistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessEmployeeTest
{
	private AccessEmployee accessEmployee;
	private EmployeePersistence employeePersistence;
	private EmployeeValidator employeeValidator;
	private Employee dummyEmployee;
	private Employee resultEmployee;
	private String dummyUserID;
	private String dummyPassword;
	private String dummyAddress;
	private String dummyEmail;
	private String dummyFirstName;
	private String dummyLastName;
	private String dummyPhoneNum;
	private String dummyEmployeeNum;
	private String dummyPosition;

	@BeforeClass
	public static void setStubs()
	{
		Services.setStubs( true );
	}

	@Before
	public void setUp()
	{
		employeePersistence = mock( EmployeePersistence.class );
		employeeValidator = mock( EmployeeValidator.class );
		accessEmployee = new AccessEmployee( employeePersistence, employeeValidator );
		dummyFirstName = "John";
		dummyLastName = "Smith";
		dummyAddress = "123 Home St.";
		dummyEmail = "home@home.com";
		dummyPhoneNum = "2045555555";
		dummyUserID = "john_smith2018";
		dummyPassword = "password";
		dummyEmployeeNum = "77777";
		dummyPosition = "Mr. Manager";
		dummyEmployee = new Employee( dummyFirstName, dummyLastName, dummyAddress, dummyEmail,
		                              dummyPhoneNum, dummyUserID, dummyPassword, dummyEmployeeNum,
		                              dummyPosition );
		resultEmployee = null;
	}

	@Test
	public void testSetAndGet()
	{
		AccessEmployee.setCurrentEmployee( dummyEmployee );
		resultEmployee = AccessEmployee.getCurrentEmployee();
		assertEquals( dummyEmployee, resultEmployee );
		AccessEmployee.setCurrentEmployee( null );
	}

	@Test
	public void testGetEmployee()
	{
		when( employeePersistence.getEmployeeFromID( dummyUserID ) ).thenReturn( dummyEmployee );

		resultEmployee = accessEmployee.getEmployee( dummyUserID, dummyPassword );

		assertNotNull( resultEmployee );

		assertTrue( resultEmployee.getFirstName().equals( dummyFirstName ) );
		assertTrue( resultEmployee.getLastName().equals( dummyLastName ) );
		assertTrue( resultEmployee.getAddress().equals( dummyAddress ) );
		assertTrue( resultEmployee.getEmail().equals( dummyEmail ) );
		assertTrue( resultEmployee.getPhoneNum().equals( dummyPhoneNum ) );
		assertTrue( resultEmployee.getUserID().equals( dummyUserID ) );
		assertTrue( resultEmployee.getPassword().equals( dummyPassword ) );
		assertTrue( resultEmployee.getEmployeeNum().equals( dummyEmployeeNum ) );
		assertTrue( resultEmployee.getPosition().equals( dummyPosition ) );

		verify( employeePersistence ).getEmployeeFromID( dummyUserID );
	}

	@Test
	public void testAddNewEmployee()
	{
		when( employeePersistence.getEmployeeFromID( dummyUserID ) ).thenReturn( null );
		when( employeePersistence.addEmployee( dummyEmployee ) ).thenReturn( true );

		assertTrue( accessEmployee.addEmployee( dummyEmployee ) );

		verify( employeePersistence ).getEmployeeFromID( dummyUserID );
		verify( employeePersistence ).addEmployee( dummyEmployee );
		verify( employeeValidator ).validate( dummyEmployee );
	}

	@Test
	public void testAddExistingEmployee()
	{
		when( employeePersistence.getEmployeeFromID( dummyUserID ) ).thenReturn( dummyEmployee );

		try
		{
			accessEmployee.addEmployee( dummyEmployee );
			fail();
		}
		catch ( EmployeeException ce )
		{
			verify( employeePersistence ).getEmployeeFromID( dummyUserID );
		}
	}

	@Test
	public void testUpdateEmployee()
	{
		Employee updated = new Employee( "Test", "This", dummyAddress, dummyEmail, dummyPhoneNum,
		                                 dummyUserID, dummyPassword, dummyEmployeeNum,
		                                 dummyPosition );

		when( employeePersistence.deleteEmployee( dummyEmployee ) ).thenReturn( true );
		when( employeePersistence.getEmployeeFromID( dummyUserID ) ).thenReturn( null );
		when( employeePersistence.addEmployee( updated ) ).thenReturn( true );

		assertTrue( accessEmployee.updateEmployeeInfo( dummyEmployee, updated ) );

		verify( employeePersistence ).deleteEmployee( dummyEmployee );
		verify( employeePersistence ).getEmployeeFromID( dummyUserID );
		verify( employeePersistence ).addEmployee( updated );
		verify( employeeValidator, times( 2 ) ).validate( updated );
	}
}
