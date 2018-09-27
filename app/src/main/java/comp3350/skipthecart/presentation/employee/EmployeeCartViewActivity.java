package comp3350.skipthecart.presentation.employee;

import android.content.Intent;
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

import java.util.Locale;

import comp3350.skipthecart.R;
import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Orders;
import comp3350.skipthecart.presentation.Messages;
import comp3350.skipthecart.presentation.customer.CustomerManagementActivity;

public class EmployeeCartViewActivity extends AppCompatActivity
{
	private Orders order;
	private Customer customer;
	private TextView live;
	private TextView old;

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
		button.setText( "SEND For delivery" );
		empty = findViewById( R.id.emptyList );
		toolbar = findViewById( R.id.toolbar );
		int orderid = getIntent().getIntExtra( "ORDERID", 0 );
		toolbar.setTitle( "ORDERID " + orderid );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}
		order = new Orders();
		try
		{
			order = AccessOrders.getOrderById( orderid );
			order.setItems( Services.getOrdersPersistence().getOrderItems( orderid ) );
		}
		catch ( Exception e )
		{
			Messages.showDialogBox( this, "ISSUE with ORDERID" );
		}

		if ( order != null && order.getStatus().equals( "DELIVERY" ) )
		{
			button.setText( "ORDER IN TRANSIT" );
			button.setClickable( false );
		}
		else if ( order != null && order.getStatus().equals( "FINISH" ) )
		{
			button.setText( "ORDER COMPLETED" );
			button.setClickable( false );
		}

		if ( order.getItems().isEmpty() )
		{
			button.setVisibility( View.GONE );
			empty.setVisibility( View.VISIBLE );
		}

		productArrayAdapter = new ArrayAdapter< OrderItem >( this, R.layout.cart_view_items,
		                                                     R.id.title, order.getItems() )
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

				String image = order.getItems().get( position ).getProduct().getImage();
				byte[] decodedString = Base64.decode( image, Base64.DEFAULT );
				Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0,
				                                                    decodedString.length );
				imageView.setImageBitmap( decodedByte );
				name.setText( order.getItems().get( position ).getProduct().getProductName() );
				quantity.setText( String.valueOf( order.getItems().get( position ).getQuantity() ) +
				                  " * " + String.format( Locale.US, "%.2f", order.getItems().get(
					position ).getProduct().getPrice() ) + " = " );
				price.setText(
					"$ " + String.format( Locale.US, "%.2f",
					                      order.getItems().get( position ).getProduct().getPrice() *
					                      order.getItems().get( position ).getQuantity() ) );

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
		AccessOrders accessOrders = new AccessOrders();
		order.setStatus( "DELIVERY" );
		accessOrders.updateOrder( order );
		finish();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}
}
