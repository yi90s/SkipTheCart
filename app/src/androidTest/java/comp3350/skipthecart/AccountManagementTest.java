package comp3350.skipthecart;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.Account;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.presentation.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith( AndroidJUnit4.class )
@LargeTest
public class AccountManagementTest
{
	private Employee intendedEmployee;
	private Customer intendedCustomer;
	@Rule
	public ActivityTestRule< MainActivity > activityRule = new ActivityTestRule<>(
		MainActivity.class );

	@Before
	public void setUp()
	{
		Services.setStubs( false );
		intendedCustomer = AccountTestUtils.getTestCustomer();
		intendedEmployee = AccountTestUtils.getTestEmployee();
	}

	@After
	public void tearDown()
	{
		Services.setStubs( false );
	}

	@Test
	public void customerAccountTest()
	{

		//create a new customer account
		onView( withId( R.id.customer_button ) )
			.perform( click() );
		onView( withId( R.id.create_account_button ) )
			.perform( closeSoftKeyboard(), click() );
		fillCustomerInfo( intendedCustomer );
		onView( withId( R.id.button_account_management ) )
			.perform( click() );

		//logout
		onView( withId( R.id.account ) )
			.perform( click() );
		onView( withId( R.id.logout ) )
			.perform( click() );

		//login account
		AccountTestUtils.loginCustomer( intendedCustomer );

		//view customer account information
		onView( withId( R.id.account ) )
			.perform( click() );
		validateCustomerField( intendedCustomer );

		//update customer account information
		onView( withId( R.id.button_account_management ) )
			.perform( click() );
		updateAccountInfo( AccountTestUtils.getTestCustomerUpdated() );
		onView( withId( R.id.button_account_management ) )
			.perform( click() );
		validateCustomerField( AccountTestUtils.getTestCustomerUpdated() );

		//reset the account information
		onView( withId( R.id.button_account_management ) )
			.perform( click() );
		updateAccountInfo( intendedCustomer );
		onView( withId( R.id.button_account_management ) )
			.perform( click() );
		validateCustomerField( intendedCustomer );
	}

	@Test
	public void employeeAccountTest()
	{

		//create a new employee account
		onView( withId( R.id.employee_button ) )
			.perform( click() );
		onView( withId( R.id.login_user_id ) )
			.perform( typeText( "test" ) );
		onView( withId( R.id.login_password ) )
			.perform( typeText( "password" ) );
		onView( withId( R.id.login_button ) )
			.perform( click() );
		onView( withId( R.id.account ) )
			.perform( click() );
		onView( withId( R.id.CenterButton ) )
			.perform( click() );
		fillEmployeeInfo( intendedEmployee );
		onView( withId( R.id.button_account_management ) )
			.perform( click() );

		//logout
		onView( withId( R.id.logout ) )
			.perform( click() );

		//login by prepared employee account
		AccountTestUtils.loginEmployee( intendedEmployee );

		//view employee account information
		onView( withId( R.id.account ) )
			.perform( click() );
		validateEmployeeField( intendedEmployee );

		//update employee account information
		onView( withId( R.id.button_account_management ) )
			.perform( click() );
		updateAccountInfo( AccountTestUtils.getTestEmployeeUpdated() );
		onView( withId( R.id.button_account_management ) )
			.perform( click() );
		validateEmployeeField( AccountTestUtils.getTestEmployeeUpdated() );

		//reset employee account information
		onView( withId( R.id.button_account_management ) )
			.perform( click() );
		updateAccountInfo( intendedEmployee );
		onView( withId( R.id.button_account_management ) )
			.perform( click() );
		validateEmployeeField( intendedEmployee );
	}

	public void updateAccountInfo( Account updatedAccount )
	{

		if ( updatedAccount instanceof Customer || updatedAccount instanceof Employee )
		{
			onView( withId( R.id.first_name ) )
				.perform( clearText(), typeText( updatedAccount.getFirstName() ),
				          closeSoftKeyboard() );
			onView( withId( R.id.last_name ) )
				.perform( clearText(), typeText( updatedAccount.getLastName() ),
				          closeSoftKeyboard() );
			onView( withId( R.id.password ) )
				.perform( clearText(), typeText( updatedAccount.getPassword() ),
				          closeSoftKeyboard() );
			onView( withId( R.id.address ) )
				.perform( clearText(), typeText( updatedAccount.getAddress() ),
				          closeSoftKeyboard() );
			onView( withId( R.id.email_address ) )
				.perform( clearText(), typeText( updatedAccount.getEmail() ), closeSoftKeyboard() );
			onView( withId( R.id.phone_number ) )
				.perform( clearText(), typeText( updatedAccount.getPhoneNum() ),
				          closeSoftKeyboard() );
		}

		if ( updatedAccount instanceof Employee )
		{
			onView( withId( R.id.employee_number ) )
				.perform( clearText(), typeText( ( ( Employee )updatedAccount ).getEmployeeNum() ),
				          closeSoftKeyboard() );
			onView( withId( R.id.employee_position ) )
				.perform( clearText(), typeText( ( ( Employee )updatedAccount ).getPosition() ),
				          closeSoftKeyboard() );
		}
	}

	public void fillCustomerInfo( Customer intendedCurrentUser )
	{

		//In AccountManagementActivity, creating a customer account
		onView( withId( R.id.first_name ) )
			.perform( typeText( intendedCurrentUser.getFirstName() ), closeSoftKeyboard() );
		onView( withId( R.id.last_name ) )
			.perform( typeText( intendedCurrentUser.getLastName() ), closeSoftKeyboard() );
		onView( withId( R.id.user_id ) )
			.perform( typeText( intendedCurrentUser.getUserID() ), closeSoftKeyboard() );
		onView( withId( R.id.password ) )
			.perform( typeText( intendedCurrentUser.getPassword() ), closeSoftKeyboard() );
		onView( withId( R.id.address ) )
			.perform( typeText( intendedCurrentUser.getAddress() ), closeSoftKeyboard() );
		onView( withId( R.id.email_address ) )
			.perform( typeText( intendedCurrentUser.getEmail() ), closeSoftKeyboard() );
		onView( withId( R.id.phone_number ) )
			.perform( typeText( intendedCurrentUser.getPhoneNum() ), closeSoftKeyboard() );
	}

	public void fillEmployeeInfo( Employee intendedCurrentUser )
	{

		//In AccountManagementActivity, creating a customer account
		onView( withId( R.id.first_name ) )
			.perform( typeText( intendedCurrentUser.getFirstName() ), closeSoftKeyboard() );
		onView( withId( R.id.last_name ) )
			.perform( typeText( intendedCurrentUser.getLastName() ), closeSoftKeyboard() );
		onView( withId( R.id.user_id ) )
			.perform( typeText( intendedCurrentUser.getUserID() ), closeSoftKeyboard() );
		onView( withId( R.id.password ) )
			.perform( typeText( intendedCurrentUser.getPassword() ), closeSoftKeyboard() );
		onView( withId( R.id.address ) )
			.perform( typeText( intendedCurrentUser.getAddress() ), closeSoftKeyboard() );
		onView( withId( R.id.email_address ) )
			.perform( typeText( intendedCurrentUser.getEmail() ), closeSoftKeyboard() );
		onView( withId( R.id.phone_number ) )
			.perform( typeText( intendedCurrentUser.getPhoneNum() ), closeSoftKeyboard() );

		onView( withId( R.id.employee_number ) )
			.perform( typeText( ( intendedCurrentUser ).getEmployeeNum() ), closeSoftKeyboard() );
		onView( withId( R.id.employee_position ) )
			.perform( typeText( ( intendedCurrentUser ).getPosition() ), closeSoftKeyboard() );
	}

	public void validateCustomerField( Customer intendedCurrentUser )
	{

		onView( ViewMatchers.withId( R.id.first_name ) )
			.check( matches( withText( intendedCurrentUser.getFirstName() ) ) );
		onView( withId( R.id.last_name ) )
			.check( matches( withText( intendedCurrentUser.getLastName() ) ) );
		onView( withId( R.id.user_id ) )
			.check( matches( withText( intendedCurrentUser.getUserID() ) ) );
		onView( withId( R.id.password ) )
			.check( matches( withText( intendedCurrentUser.getPassword() ) ) );
		onView( withId( R.id.address ) )
			.check( matches( withText( intendedCurrentUser.getAddress() ) ) );
		onView( withId( R.id.email_address ) )
			.check( matches( withText( intendedCurrentUser.getEmail() ) ) );
		onView( withId( R.id.phone_number ) )
			.check( matches( withText( intendedCurrentUser.getPhoneNum() ) ) );
	}

	public void validateEmployeeField( Employee intendedCurrentUser )
	{

		onView( ViewMatchers.withId( R.id.first_name ) )
			.check( matches( withText( intendedCurrentUser.getFirstName() ) ) );
		onView( withId( R.id.last_name ) )
			.check( matches( withText( intendedCurrentUser.getLastName() ) ) );
		onView( withId( R.id.user_id ) )
			.check( matches( withText( intendedCurrentUser.getUserID() ) ) );
		onView( withId( R.id.password ) )
			.check( matches( withText( intendedCurrentUser.getPassword() ) ) );
		onView( withId( R.id.address ) )
			.check( matches( withText( intendedCurrentUser.getAddress() ) ) );
		onView( withId( R.id.email_address ) )
			.check( matches( withText( intendedCurrentUser.getEmail() ) ) );
		onView( withId( R.id.phone_number ) )
			.check( matches( withText( intendedCurrentUser.getPhoneNum() ) ) );

		onView( withId( R.id.employee_number ) )
			.check( matches( withText( ( ( Employee )intendedCurrentUser ).getEmployeeNum() ) ) );
		onView( withId( R.id.employee_position ) )

			.check( matches( withText( ( ( Employee )intendedCurrentUser ).getPosition() ) ) );
	}
}
