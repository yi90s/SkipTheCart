package comp3350.skipthecart.presentation;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Messages
{
	public static void showDialogBox( final Activity source,
	                                  final String message )
	{
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder( source );

		dlgAlert.setMessage( message );
		dlgAlert.setTitle( "Unable to proceed..." );
		dlgAlert.setCancelable( false );
		dlgAlert.setPositiveButton( "DISMISS",
		                            new DialogInterface.OnClickListener()
		                            {
			                            @Override
			                            public void onClick( DialogInterface dialog,
			                                                 int which )
			                            {
			                            }
		                            } );
		dlgAlert.create().show();
	}
}
