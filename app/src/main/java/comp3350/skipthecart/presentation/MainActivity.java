package comp3350.skipthecart.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.skipthecart.R;
import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.presentation.customer.CustomerLoginActivity;
import comp3350.skipthecart.presentation.employee.EmployeeLoginActivity;

public class MainActivity extends AppCompatActivity
{
	private static boolean ranOnce = false;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		if ( !ranOnce )
		{
			copyDatabaseToDevice();
			ranOnce = true;
		}
	}

	public void onCustomerPressed( View v )
	{
		startActivity( new Intent( this, CustomerLoginActivity.class ) );
	}

	public void onEmployeePressed( View v )
	{
		startActivity( new Intent( this, EmployeeLoginActivity.class ) );
	}

	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent( Intent.ACTION_MAIN );
		intent.addCategory( Intent.CATEGORY_HOME );
		intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
		startActivity( intent );
	}

	public void copyDatabaseToDevice()
	{
		final String DB_PATH = "db";

		String[] assetNames;
		Context context = getApplicationContext();
		File dataDirectory = context.getDir( DB_PATH, Context.MODE_PRIVATE );
		AssetManager assetManager = getAssets();

		try
		{
			assetNames = assetManager.list( DB_PATH );

			for ( int i = 0; i < assetNames.length; i++ )
			{
				assetNames[i] = DB_PATH + "/" + assetNames[i];
			}

			copyAssetsToDirectory( assetNames, dataDirectory );
			Services.setDBPathName( dataDirectory.toString() + "/" + Services.getDBPathName() );
		}
		catch ( final IOException ioe )
		{
			Messages.showDialogBox( this,
			                        "Unable to access application data: " + ioe.getMessage() );
		}
	}

	public void copyAssetsToDirectory( String[] assets,
	                                   File directory ) throws IOException
	{
		AssetManager assetManager = getAssets();

		for ( String asset : assets )
		{
			String[] components = asset.split( "/" );
			String copyPath = directory.toString() + "/" + components[components.length - 1];

			char[] buffer = new char[1024];
			int count;

			File outFile = new File( copyPath );

			if ( !outFile.exists() )
			{
				InputStreamReader in = new InputStreamReader( assetManager.open( asset ) );
				FileWriter out = new FileWriter( outFile );

				count = in.read( buffer );

				while ( count != -1 )
				{
					out.write( buffer, 0, count );
					count = in.read( buffer );
				}

				out.close();
				in.close();
			}
		}
	}
}
