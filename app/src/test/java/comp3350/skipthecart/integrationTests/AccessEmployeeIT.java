package comp3350.skipthecart.integrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.skipthecart.logic.AccessEmployee;
import comp3350.skipthecart.logic.EmployeeException;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.utils.TestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AccessEmployeeIT
{
	private File tempDB;
	private AccessEmployee accessEmployee;
	private Employee dummyEmployee;
	private Employee dummyUpdated;
	private String dummyUserID;
	private String dummyPassword;
	private String dummyAddress;
	private String dummyEmail;
	private String dummyFirstName;
	private String dummyLastName;
	private String dummyPhoneNum;
	private String dummyEmployeeNum;
	private String dummyPosition;

	@Before
	public void setUp() throws IOException
	{
		tempDB = TestUtils.copyDB();
		accessEmployee = new AccessEmployee();
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
		dummyUpdated = new Employee( "Test", "This", dummyAddress, dummyEmail, dummyPhoneNum,
		                             dummyUserID, dummyPassword, dummyEmployeeNum, dummyPosition );
	}

	@Test( expected = EmployeeException.class )
	public void testAccessEmployeePersistence()
	{
		Employee resultEmployee;

		assertNull( accessEmployee.getEmployee( dummyUserID, dummyPassword ) );
		assertTrue( accessEmployee.addEmployee( dummyEmployee ) );

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

		assertTrue( accessEmployee.updateEmployeeInfo( dummyEmployee, dummyUpdated ) );

		resultEmployee = accessEmployee.getEmployee( dummyUserID, dummyPassword );

		assertNotNull( resultEmployee );

		assertTrue( resultEmployee.getFirstName().equals( "Test" ) );
		assertTrue( resultEmployee.getLastName().equals( "This" ) );
		assertTrue( resultEmployee.getAddress().equals( dummyAddress ) );
		assertTrue( resultEmployee.getEmail().equals( dummyEmail ) );
		assertTrue( resultEmployee.getPhoneNum().equals( dummyPhoneNum ) );
		assertTrue( resultEmployee.getUserID().equals( dummyUserID ) );
		assertTrue( resultEmployee.getPassword().equals( dummyPassword ) );
		assertTrue( resultEmployee.getEmployeeNum().equals( dummyEmployeeNum ) );
		assertTrue( resultEmployee.getPosition().equals( dummyPosition ) );

		accessEmployee.addEmployee( dummyEmployee );
	}

	@After
	public void tearDown()
	{
		tempDB.delete();
	}
}
