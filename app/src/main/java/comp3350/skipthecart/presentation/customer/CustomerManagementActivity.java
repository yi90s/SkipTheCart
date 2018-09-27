package comp3350.skipthecart.presentation.customer;

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
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.logic.CustomerException;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.presentation.Messages;

public class CustomerManagementActivity extends AppCompatActivity
{
	private AccessCustomer accessCustomer;
	private Customer customer;
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

		setupCustomerButtons();
		accessCustomer = new AccessCustomer();
		customer = AccessCustomer.getCurrentCustomer();
		action = getIntent().getStringExtra( "ACTION" );

		if ( action != null && !action.isEmpty() )
		{
			setupCustomerLayout();

			switch ( action )
			{
				case "create":
					toolbar.setTitle( R.string.button_create_account );
					leftButton.setText( R.string.button_create_account );
					customer = null;
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

	private void setupCustomerButtons()
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

	private void setupCustomerLayout()
	{
		firstName = findViewById( R.id.first_name );
		lastName = findViewById( R.id.last_name );
		userID = findViewById( R.id.user_id );
		password = findViewById( R.id.password );
		address = findViewById( R.id.address );
		email = findViewById( R.id.email_address );
		phoneNum = findViewById( R.id.phone_number );

		logout.setVisibility( View.GONE );
		centerButton.setVisibility( View.GONE );

		findViewById( R.id.textview_employee_number ).setVisibility( View.GONE );
		findViewById( R.id.employee_number ).setVisibility( View.GONE );
		findViewById( R.id.textview_employee_position ).setVisibility( View.GONE );
		findViewById( R.id.employee_position ).setVisibility( View.GONE );
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		customer = AccessCustomer.getCurrentCustomer();
		fillAccountInfo();
	}

	private boolean updateCustomerInfo()
	{
		boolean successful;

		Customer updatedCustomer = new Customer( firstName.getText().toString(),
		                                         lastName.getText().toString(),
		                                         address.getText().toString(),
		                                         email.getText().toString(),
		                                         phoneNum.getText().toString(),
		                                         userID.getText().toString(),
		                                         password.getText().toString() );

		try
		{
			successful = accessCustomer.updateCustomerInfo( customer, updatedCustomer );

			if ( successful )
			{
				customer = updatedCustomer;
				AccessCustomer.setCurrentCustomer( updatedCustomer );
			}
		}
		catch ( CustomerException ce )
		{
			Messages.showDialogBox( this, ce.getMessage() );
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
	}

	private void fillAccountInfo()
	{
		if ( customer != null && ( action.equals( "view" ) || action.equals( "edit" ) ) )
		{
			firstName.setText( customer.getFirstName() );
			lastName.setText( customer.getLastName() );
			userID.setText( customer.getUserID() );
			password.setText( customer.getPassword() );
			address.setText( customer.getAddress() );
			email.setText( customer.getEmail() );
			phoneNum.setText( customer.getPhoneNum() );
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
	}

	private Customer createNewCustomer()
	{
		Customer result = null;
		Customer newCustomer = new Customer( firstName.getText().toString(),
		                                     lastName.getText().toString(),
		                                     address.getText().toString(),
		                                     email.getText().toString(),
		                                     phoneNum.getText().toString(),
		                                     userID.getText().toString(),
		                                     password.getText().toString() );

		try
		{
			if ( accessCustomer.addCustomer( newCustomer ) )
			{
				result = newCustomer;
			}
		}
		catch ( CustomerException ce )
		{
			Messages.showDialogBox( this, ce.getMessage() );
		}

		return result;
	}

	public void accountManageButtonOnClick( View v )
	{
		Customer newCustomer;
		Intent intent;

		if ( action.equals( "create" ) )
		{
			newCustomer = createNewCustomer();

			if ( newCustomer != null )
			{
				Toast.makeText( getApplicationContext(), "Let's start shopping!",
				                Toast.LENGTH_SHORT ).show();
				AccessCustomer.setCurrentCustomer( newCustomer );
				intent = new Intent( getApplicationContext(),
				                     CustomerProductsListingActivity.class );
				startActivity( intent );
			}
		}
		else if ( action.equals( "view" ) && customer != null )
		{
			intent = new Intent( getApplicationContext(), CustomerManagementActivity.class );
			intent.putExtra( "ACTION", "edit" );
			startActivity( intent );
		}
		else if ( action.equals( "edit" ) && updateCustomerInfo() )
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
		prefsEditor.remove( "Customer" );
		prefsEditor.commit();
		AccessOrders.clearOrders();
		AccessCustomer.setCurrentCustomer( null );
		startActivity( new Intent( this, CustomerLoginActivity.class ) );
	}

	public void onCenterButtonClick( View view )
	{
		if ( customer != null )
		{
			Intent intent = new Intent( this, PaymentEditActivity.class );
			startActivity( intent );
		}
	}
}
