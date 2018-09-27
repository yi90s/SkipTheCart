package comp3350.skipthecart.presentation.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import comp3350.skipthecart.R;
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.logic.AccessPayment;
import comp3350.skipthecart.logic.OrdersException;
import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.presentation.Messages;

public class CartViewActivity extends AppCompatActivity
{
	private List< OrderItem > orderList;
	private Customer customer;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		Toolbar toolbar;
		ActionBar supportActionBar;
		Button button;
		TextView empty;
		ArrayAdapter< OrderItem > productArrayAdapter;
		ListView listView;

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_cart_view );
		button = findViewById( R.id.checkout );
		empty = findViewById( R.id.emptyList );
		toolbar = findViewById( R.id.toolbar );
		toolbar.setTitle( "My cart" );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}

		orderList = AccessOrders.getOrders().getItems();

		if ( orderList.isEmpty() )
		{
			button.setVisibility( View.GONE );
			empty.setVisibility( View.VISIBLE );
		}
		else
		{
			refreshCustomer();
		}

		productArrayAdapter = new ArrayAdapter< OrderItem >( this, R.layout.cart_view_items,
		                                                     R.id.title, orderList )
		{
			@NonNull
			@Override
			public View getView( int position,
			                     View convertView,
			                     @NonNull ViewGroup parent )
			{
				View view = super.getView( position, convertView, parent );

				TextView name = view.findViewById( R.id.title );
				TextView quantity = view.findViewById( R.id.quantity );
				TextView price = view.findViewById( R.id.price );
				ImageView imageView = view.findViewById( R.id.image );

				String image = orderList.get( position ).getProduct().getImage();
				byte[] decodedString = Base64.decode( image, Base64.DEFAULT );
				Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0,
				                                                    decodedString.length );
				imageView.setImageBitmap( decodedByte );
				name.setText( orderList.get( position ).getProduct().getProductName() );
				quantity.setText( String.valueOf( orderList.get( position ).getQuantity() ) +
				                  " * " + String.format( Locale.US, "%.2f", orderList.get(
					position ).getProduct().getPrice() ) + " = " );
				price.setText(
					"$ " + String.format( Locale.US, "%.2f",
					                      orderList.get( position ).getProduct().getPrice() *
					                      orderList.get( position ).getQuantity() ) );

				return view;
			}
		};

		listView = findViewById( R.id.cartList );
		listView.setAdapter( productArrayAdapter );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.account, menu );

		return super.onCreateOptionsMenu( menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch ( item.getItemId() )
		{
			case R.id.account:
				Intent intent = new Intent( this, CustomerManagementActivity.class );
				intent.putExtra( "ACTION", "view" );
				startActivity( intent );

			default:
				return super.onOptionsItemSelected( item );
		}
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();

		return true;
	}

	public void proceedCheckout( View view )
	{
		Intent intent;
		AccessOrders accessOrders;

		if ( customer != null )
		{
			if ( customer.getPaymentMethod() != null )
			{
				try
				{
					accessOrders = new AccessOrders();
					accessOrders.commitOrders();
					SharedPreferences mPrefs = getSharedPreferences( "SkipTheCart", MODE_PRIVATE );
					SharedPreferences.Editor prefsEditor = mPrefs.edit();
					prefsEditor.remove( "Orders" );
					prefsEditor.commit();
					Toast.makeText( this, "Order placed successfully!", Toast.LENGTH_SHORT ).show();
					finish();
				}
				catch ( OrdersException oe )
				{
					Messages.showDialogBox( this, oe.getMessage() );
					AccessOrders.clearOrders();
				}
			}
			else
			{
				intent = new Intent( this, PaymentEditActivity.class );
				startActivity( intent );
			}
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		refreshCustomer();

		if ( customer != null && customer.getPaymentMethod() == null )
		{
			Toast.makeText( this, "Credit Card information required", Toast.LENGTH_SHORT ).show();
		}
	}

	private void refreshCustomer()
	{
		AccessPayment accessPayment;
		CreditCard cc;

		customer = AccessCustomer.getCurrentCustomer();

		if ( customer != null && customer.getPaymentMethod() == null )
		{
			accessPayment = new AccessPayment();
			cc = accessPayment.getCreditCard( customer.getUserID() );

			if ( cc != null )
			{
				customer.setPaymentMethod( cc );
			}
		}
	}
}
