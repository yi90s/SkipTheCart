package comp3350.skipthecart.presentation.employee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import comp3350.skipthecart.R;
import comp3350.skipthecart.logic.AccessEmployee;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.presentation.MainActivity;

public class EmployeeLoginActivity extends AppCompatActivity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		Toolbar toolbar;
		ActionBar supportActionBar;
		SharedPreferences sp;
		JSONObject reader;
		String json;
		Button login;
		Button createAccount;
		String userId = "";
		String password = "";

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_login );

		sp = getPreferences( MODE_PRIVATE );
		json = sp.getString( "Employee", "" );
		Log.i( "JSON", json );

		if ( !json.isEmpty() )
		{
			try
			{
				reader = new JSONObject( json );
				userId = reader.getString( "userID" );
				password = reader.getString( "password" );
			}
			catch ( JSONException e )
			{
				e.printStackTrace();
			}
		}

		toolbar = findViewById( R.id.toolbar );
		toolbar.setTitle( "Employee Login" );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}

		createAccount = findViewById( R.id.create_account_button );
		createAccount.setVisibility( View.GONE );

		login = findViewById( R.id.login_button );
		login.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				onLoginPressed( v );
			}
		} );
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();

		return true;
	}

	@Override
	public void onBackPressed()
	{
		startActivity( new Intent( this, MainActivity.class ) );
		finish();
	}

	public void onLoginPressed( View v )
	{
		Intent intent;
		SharedPreferences mPrefs;
		SharedPreferences.Editor prefsEditor;
		Employee employee;
		Gson gson;
		String json;
		Toast incorrectInfo;
		AccessEmployee accessEmployee = new AccessEmployee();
		EditText userID = findViewById( R.id.login_user_id );
		EditText password = findViewById( R.id.login_password );

		employee = accessEmployee.getEmployee( userID.getText().toString(),
		                                       password.getText().toString() );

		if ( employee != null )
		{
			AccessEmployee.setCurrentEmployee( employee );
			mPrefs = getSharedPreferences( "SkipTheCart", MODE_PRIVATE );
			prefsEditor = mPrefs.edit();
			gson = new Gson();
			json = gson.toJson( employee );
			prefsEditor.putString( "Employee", json );
			prefsEditor.apply();
			if ( employee.getPosition().equals( "DRIVER" ) )
			{
				intent = new Intent( getApplicationContext(), OrdersActivity.class );
				startActivity( intent );
				finish();
			}
			else
			{
				intent = new Intent( getApplicationContext(),
				                     EmployeeProductsListingActivity.class );
				startActivity( intent );
			}
			finish();
		}
		else
		{
			incorrectInfo = Toast.makeText( getApplicationContext(),
			                                "Incorrect User ID or Password",
			                                Toast.LENGTH_SHORT );
			incorrectInfo.show();
		}
	}
}
