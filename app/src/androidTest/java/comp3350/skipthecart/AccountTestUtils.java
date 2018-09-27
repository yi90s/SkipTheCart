package comp3350.skipthecart;

import java.io.File;

import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.Employee;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class AccountTestUtils
{
	private static final File DB_SRC = new File( "src/main/assets/db/local.script" );
	private final static String firstName = "FirstName";
	private final static String lastName = "LastName";
	private final static String userId = "UserId";
	private final static String password = "password";
	private final static String address = "1234 queen road";
	private final static String emailAddress = "email@gmail.com";
	private final static String phoneNumber = "123456789";
	private final static String employeeUserId = "Employee";
	private final static String employeeNumber = "123456789";
	private final static String employeePosition = "Manager";
	private static Customer testCustomer = new Customer( firstName, lastName, address, emailAddress,
	                                                     phoneNumber, userId, password );
	private static Employee testEmployee = new Employee( firstName, lastName, address, emailAddress,
	                                                     phoneNumber, employeeUserId, password,
	                                                     employeeNumber, employeePosition );
	private static Customer testCustomerUpdated = new Customer( firstName + "1", lastName + "1",
	                                                            address + "1",
	                                                            emailAddress + "1",
	                                                            phoneNumber + "1", userId,
	                                                            password + "1" );
	private static Employee testEmployeeUpdated = new Employee( firstName + "2", lastName + "2",
	                                                            address + "2", emailAddress + "2",
	                                                            phoneNumber + "2",
	                                                            employeeUserId, password + "2",
	                                                            employeeNumber + "2",
	                                                            employeePosition + "2" );

	public static Customer getTestCustomer()
	{
		return testCustomer;
	}

	public static Employee getTestEmployee()
	{
		return testEmployee;
	}

	public static Customer getTestCustomerUpdated()
	{
		return testCustomerUpdated;
	}

	public static Employee getTestEmployeeUpdated()
	{
		return testEmployeeUpdated;
	}

	public static void loginCustomer( Customer intendedAccount )
	{

		onView( withId( R.id.login_user_id ) )
			.perform( typeText( intendedAccount.getUserID() ) );
		onView( withId( R.id.login_password ) )
			.perform( typeText( intendedAccount.getPassword() ) );
		onView( withId( R.id.login_button ) )
			.perform( click() );
	}

	public static void loginEmployee( Employee intendedAccount )
	{

		onView( withId( R.id.login_user_id ) )
			.perform( typeText( intendedAccount.getUserID() ) );
		onView( withId( R.id.login_password ) )
			.perform( typeText( intendedAccount.getPassword() ) );
		onView( withId( R.id.login_button ) )
			.perform( click() );
	}

	public static void loginByEmployee()
	{
		onView( withId( R.id.login_user_id ) )
			.perform( typeText( "test" ) );
		onView( withId( R.id.login_password ) )
			.perform( typeText( "password" ) );
		onView( withId( R.id.login_button ) )
			.perform( click() );
	}

	public static void loginByCustomer()
	{
		onView( withId( R.id.login_user_id ) )
			.perform( typeText( "noodles" ) );
		onView( withId( R.id.login_password ) )
			.perform( typeText( "password" ) );
		onView( withId( R.id.login_button ) )
			.perform( click() );
	}
}
