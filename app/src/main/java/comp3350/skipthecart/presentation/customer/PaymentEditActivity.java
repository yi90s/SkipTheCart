package comp3350.skipthecart.presentation.customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import comp3350.skipthecart.R;
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessPayment;
import comp3350.skipthecart.logic.PaymentException;
import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.presentation.Messages;

public class PaymentEditActivity extends AppCompatActivity
{
	private Customer customer;
	private AccessPayment accessPayment;
	private EditText ownerName;
	private EditText cardNum;
	private EditText verificationCode;
	private int setMonth;
	private int setYear;
	private int cardYear;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		Toolbar toolbar;
		ActionBar supportActionBar;
		Calendar calendar = Calendar.getInstance();

		setYear = calendar.get( Calendar.YEAR );
		setMonth = calendar.get( Calendar.MONTH ) + 1;
		cardYear = setYear;

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_payment_edit );
		this.getWindow().setSoftInputMode(
			WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );

		toolbar = findViewById( R.id.CreditCardToolbar );
		toolbar.setTitle( "Credit Card" );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}

		accessPayment = new AccessPayment();
		customer = AccessCustomer.getCurrentCustomer();

		ownerName = findViewById( R.id.CardOwnerEdit );
		cardNum = findViewById( R.id.CardNumEdit );
		verificationCode = findViewById( R.id.VerificationCodeEdit );

		fillFieldsFromCreditCard();
		fillExpMonthSpinner();
		fillExpYearSpinner();
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();

		return true;
	}

	public void onUpdateClick( View view )
	{
		CreditCard newCard = null;

		if ( customer != null )
		{
			newCard = createCardFromFields();

			try
			{
				accessPayment.updateCreditCard( customer.getUserID(), newCard );
				customer.setPaymentMethod( newCard );
				finish();
			}
			catch ( PaymentException pe )
			{
				Messages.showDialogBox( this, pe.getMessage() );
			}
		}
	}

	public void onRemoveClick( View view )
	{
		if ( customer != null )
		{
			accessPayment.deleteCreditCard( customer.getUserID() );
			customer.setPaymentMethod( null );
			finish();
		}
	}

	private void fillFieldsFromCreditCard()
	{
		CreditCard cc;
		Button removeButton;

		if ( customer != null )
		{
			if ( customer.getPaymentMethod() != null )
			{
				cc = customer.getPaymentMethod();
			}
			else
			{
				cc = accessPayment.getCreditCard( customer.getUserID() );
				customer.setPaymentMethod( cc );
			}

			if ( cc != null )
			{
				ownerName.setText( cc.getOwnerName() );
				cardNum.setText( cc.getCardNum() );
				verificationCode.setText( cc.getVerificationCode() );
				setMonth = cc.getExpMonth();
				cardYear = cc.getExpYear();
			}
			else
			{
				removeButton = findViewById( R.id.RemoveCard );
				removeButton.setVisibility( View.GONE );
			}
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		fillFieldsFromCreditCard();
	}

	@NonNull
	private CreditCard createCardFromFields()
	{
		String updateName = ownerName.getText().toString();
		String updateNum = cardNum.getText().toString();
		String updateCode = verificationCode.getText().toString();

		return new CreditCard( updateName, updateNum, updateCode, setMonth,
		                       cardYear );
	}

	private void fillExpMonthSpinner()
	{
		final Spinner monthSpinner;
		final String[] months;
		final ArrayAdapter< String > monthAdapter;

		monthSpinner = findViewById( R.id.ExpMonthSelect );
		months = new String[]{
			"Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
		};

		monthAdapter = new ArrayAdapter<>( this, R.layout.spinner_payment, months );
		monthSpinner.setAdapter( monthAdapter );
		monthSpinner.setSelection( setMonth - 1 );

		monthSpinner.setOnItemSelectedListener( new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected( AdapterView< ? > parent,
			                            View view,
			                            int position,
			                            long id )
			{
				setMonth = position + 1;
			}

			@Override
			public void onNothingSelected( AdapterView< ? > parent )
			{
			}
		} );
	}

	private void fillExpYearSpinner()
	{
		final Spinner yearSpinner;
		final String[] years;
		final ArrayAdapter< String > yearAdapter;
		final int maxExpYears = 5;

		yearSpinner = findViewById( R.id.ExpYearSelect );
		years = new String[maxExpYears];

		for ( int i = 0; i < maxExpYears; i++ )
		{
			years[i] = "" + ( setYear + i );
		}

		yearAdapter = new ArrayAdapter<>( this, R.layout.spinner_payment, years );
		yearSpinner.setAdapter( yearAdapter );
		yearSpinner.setSelection( getYearIndex( yearSpinner, "" + cardYear ) );

		yearSpinner.setOnItemSelectedListener( new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected( AdapterView< ? > parent,
			                            View view,
			                            int position,
			                            long id )
			{
				cardYear = Integer.parseInt( yearSpinner.getSelectedItem().toString() );
			}

			@Override
			public void onNothingSelected( AdapterView< ? > parent )
			{
			}
		} );
	}

	private int getYearIndex( Spinner spinner,
	                          String year )
	{
		int index = 0;
		boolean found = false;

		if ( spinner != null && year != null )
		{
			for ( int i = 0; i < spinner.getCount() && !found; i++ )
			{
				found = spinner.getItemAtPosition( i ).toString().equals( year );

				if ( found )
				{
					index = i;
				}
			}
		}

		return index;
	}
}
