package comp3350.skipthecart.presentation.employee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comp3350.skipthecart.R;
import comp3350.skipthecart.logic.AccessEmployee;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.logic.EmployeeException;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.presentation.Messages;

public class EmployeeManagementActivity extends AppCompatActivity
{
	private AccessEmployee accessEmployee;
	private Employee employee;
	private String action;
	private Button leftButton;
	private Button centerButton;
	private Button logout;
	private EditText firstName;
	private EditText lastName;
	private EditText userID;
	private EditText password;
	private EditText address;
	private EditText email;
	private EditText phoneNum;
	private EditText employeeNum;
	private EditText position;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		Toolbar toolbar;
		ActionBar supportActionBar;
		boolean noAction = false;

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_account_management );

		toolbar = findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}

		setupEmployeeButtons();
		accessEmployee = new AccessEmployee();
		employee = AccessEmployee.getCurrentEmployee();
		action = getIntent().getStringExtra( "ACTION" );

		if ( action != null && !action.isEmpty() )
		{
			setupEmployeeLayout();

			switch ( action )
			{
				case "create":
					toolbar.setTitle( R.string.button_create_account );
					leftButton.setText( R.string.button_create_account );
					employee = null;
					clearAccountInfo();

					break;

				case "edit":
					toolbar.setTitle( R.string.edit_account );
					leftButton.setText( R.string.update_account );
					fillAccountInfo();
					userID.setFocusable( false );
					userID.setBackground( null );

					break;

				case "view":
					toolbar.setTitle( R.string.account_details );
					logout.setVisibility( View.VISIBLE );
					centerButton.setVisibility( View.VISIBLE );
					centerButton.setText( R.string.add_employee );
					leftButton.setText( R.string.edit_account );
					disableEditText();

					break;

				default:
					noAction = true;

					break;
			}
		}
		else
		{
			noAction = true;
		}

		if ( noAction )
		{
			Toast.makeText( getApplicationContext(), "No ACTION specified",
			                Toast.LENGTH_SHORT ).show();
			finish();
		}
	}

	private void setupEmployeeButtons()
	{
		leftButton = findViewById( R.id.button_account_management );
		centerButton = findViewById( R.id.CenterButton );
		logout = findViewById( R.id.logout );

		leftButton.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				accountManageButtonOnClick( v );
			}
		} );

		centerButton.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				onCenterButtonClick( v );
			}
		} );

		logout.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				onLogout( v );
			}
		} );
	}

	private void setupEmployeeLayout()
	{
		firstName = findViewById( R.id.first_name );
		lastName = findViewById( R.id.last_name );
		userID = findViewById( R.id.user_id );
		password = findViewById( R.id.password );
		address = findViewById( R.id.address );
		email = findViewById( R.id.email_address );
		phoneNum = findViewById( R.id.phone_number );
		employeeNum = findViewById( R.id.employee_number );
		position = findViewById( R.id.employee_position );

		logout.setVisibility( View.GONE );
		centerButton.setVisibility( View.GONE );
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		employee = AccessEmployee.getCurrentEmployee();
		fillAccountInfo();
	}

	private boolean updateEmployeeInfo()
	{
		boolean successful;

		Employee updatedEmployee = new Employee( firstName.getText().toString(),
		                                         lastName.getText().toString(),
		                                         address.getText().toString(),
		                                         email.getText().toString(),
		                                         phoneNum.getText().toString(),
		                                         userID.getText().toString(),
		                                         password.getText().toString(),
		                                         employeeNum.getText().toString(),
		                                         position.getText().toString() );

		try
		{
			successful = accessEmployee.updateEmployeeInfo( employee, updatedEmployee );

			if ( successful )
			{
				employee = updatedEmployee;
				AccessEmployee.setCurrentEmployee( updatedEmployee );
			}
		}
		catch ( EmployeeException ee )
		{
			Messages.showDialogBox( this, ee.getMessage() );
			successful = false;
		}

		return successful;
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();

		return true;
	}

	private void disableEditText()
	{
		firstName.setFocusable( false );
		firstName.setBackground( null );
		lastName.setFocusable( false );
		lastName.setBackground( null );
		userID.setFocusable( false );
		userID.setBackground( null );
		password.setFocusable( false );
		password.setBackground( null );
		address.setFocusable( false );
		address.setBackground( null );
		email.setFocusable( false );
		email.setBackground( null );
		phoneNum.setFocusable( false );
		phoneNum.setBackground( null );
		employeeNum.setFocusable( false );
		employeeNum.setBackground( null );
		position.setFocusable( false );
		position.setBackground( null );
	}

	private void fillAccountInfo()
	{
		if ( employee != null && ( action.equals( "view" ) || action.equals( "edit" ) ) )
		{
			firstName.setText( employee.getFirstName() );
			lastName.setText( employee.getLastName() );
			userID.setText( employee.getUserID() );
			password.setText( employee.getPassword() );
			address.setText( employee.getAddress() );
			email.setText( employee.getEmail() );
			phoneNum.setText( employee.getPhoneNum() );
			employeeNum.setText( employee.getEmployeeNum() );
			position.setText( employee.getPosition() );
		}
	}

	private void clearAccountInfo()
	{
		firstName.setText( "" );
		lastName.setText( "" );
		userID.setText( "" );
		password.setText( "" );
		address.setText( "" );
		email.setText( "" );
		phoneNum.setText( "" );
		employeeNum.setText( "" );
		position.setText( "" );
	}

	private Employee createNewEmployee()
	{
		Employee result = null;
		Employee newEmployee = new Employee( firstName.getText().toString(),
		                                     lastName.getText().toString(),
		                                     address.getText().toString(),
		                                     email.getText().toString(),
		                                     phoneNum.getText().toString(),
		                                     userID.getText().toString(),
		                                     password.getText().toString(),
		                                     employeeNum.getText().toString(),
		                                     position.getText().toString() );

		try
		{
			if ( accessEmployee.addEmployee( newEmployee ) )
			{
				result = newEmployee;
			}
		}
		catch ( EmployeeException ee )
		{
			Messages.showDialogBox( this, ee.getMessage() );
		}

		return result;
	}

	public void accountManageButtonOnClick( View v )
	{
		Employee newEmployee;
		Intent intent;

		if ( action.equals( "create" ) )
		{
			newEmployee = createNewEmployee();

			if ( newEmployee != null )
			{
				Toast.makeText( getApplicationContext(), "New employee added",
				                Toast.LENGTH_SHORT ).show();
				finish();
			}
		}
		else if ( action.equals( "view" ) && employee != null )
		{
			intent = new Intent( getApplicationContext(), EmployeeManagementActivity.class );
			intent.putExtra( "ACTION", "edit" );
			startActivity( intent );
		}
		else if ( action.equals( "edit" ) && updateEmployeeInfo() )
		{
			Toast.makeText( getApplicationContext(), "Update successful",
			                Toast.LENGTH_SHORT ).show();
			finish();
		}
	}

	public void onLogout( View view )
	{
		SharedPreferences mPrefs = getSharedPreferences( "SkipTheCart", MODE_PRIVATE );
		SharedPreferences.Editor prefsEditor = mPrefs.edit();
		prefsEditor.remove( "Orders" );
		prefsEditor.commit();
		prefsEditor.remove( "Employee" );
		prefsEditor.commit();
		AccessOrders.clearOrders();
		AccessEmployee.setCurrentEmployee( null );
		startActivity( new Intent( this, EmployeeLoginActivity.class ) );
		finish();
	}

	public void onCenterButtonClick( View view )
	{
		Intent intent = new Intent( this, EmployeeManagementActivity.class );
		intent.putExtra( "ACTION", "create" );
		startActivity( intent );
	}
}
