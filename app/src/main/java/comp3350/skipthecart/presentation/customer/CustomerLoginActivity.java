package comp3350.skipthecart.presentation.customer;

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
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.presentation.MainActivity;

public class CustomerLoginActivity extends AppCompatActivity
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
		json = sp.getString( "Customer", "" );
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
		toolbar.setTitle( "Customer Login" );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}

		login = findViewById( R.id.login_button );
		login.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				onLoginPressed( v );
			}
		} );

		createAccount = findViewById( R.id.create_account_button );
		createAccount.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				onCreateAccountPressed( v );
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

	public void onCreateAccountPressed( View v )
	{
		Intent intent = new Intent( getApplicationContext(), CustomerManagementActivity.class );
		intent.putExtra( "ACTION", "create" );
		startActivity( intent );
	}

	public void onLoginPressed( View v )
	{
		Intent intent;
		SharedPreferences mPrefs;
		SharedPreferences.Editor prefsEditor;
		Customer customer;
		Gson gson;
		String json;
		Toast incorrectInfo;
		AccessCustomer accessCustomer = new AccessCustomer();
		EditText userID = findViewById( R.id.login_user_id );
		EditText password = findViewById( R.id.login_password );

		customer = accessCustomer.getCustomer( userID.getText().toString(),
		                                       password.getText().toString() );

		if ( customer != null )
		{
			AccessCustomer.setCurrentCustomer( customer );
			mPrefs = getSharedPreferences( "SkipTheCart", MODE_PRIVATE );
			prefsEditor = mPrefs.edit();
			gson = new Gson();
			json = gson.toJson( customer );
			prefsEditor.putString( "Customer", json );
			prefsEditor.apply();
			intent = new Intent( getApplicationContext(), CustomerProductsListingActivity.class );
			startActivity( intent );
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
