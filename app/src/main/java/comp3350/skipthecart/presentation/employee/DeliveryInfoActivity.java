package comp3350.skipthecart.presentation.employee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import comp3350.skipthecart.R;
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Orders;

public class DeliveryInfoActivity extends AppCompatActivity
{
	TextView order_Id;
	TextView name;
	TextView address;
	TextView phone;
	TextView total;
	private android.support.v7.widget.Toolbar toolbar;
	private Orders orders;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_delivery_info );
		toolbar = findViewById( R.id.toolbar );
		toolbar.setTitle( "Delivery Info" );
		setSupportActionBar( toolbar );
		if ( getSupportActionBar() != null )
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled( true );
			getSupportActionBar().setDisplayShowHomeEnabled( true );
		}
		order_Id = ( TextView )findViewById( R.id.order_id );
		name = ( TextView )findViewById( R.id.customer_name );
		address = ( TextView )findViewById( R.id.customer_address );
		phone = ( TextView )findViewById( R.id.customer_phone );
		total = ( TextView )findViewById( R.id.order_total );

		int orderId = getIntent().getIntExtra( "ORDERID", 0 );

		orders = AccessOrders.getOrderById( orderId );
		AccessCustomer c = new AccessCustomer();
		Customer customer = c.getCustomerById( orders.getUserId() );
		displayCustomerInfo( orders, customer );
	}

	private void displayCustomerInfo( Orders orders,
	                                  Customer customer )
	{
		order_Id.setText( String.valueOf( "Order Id " + orders.getOrderId() ) );
		name.setText(
			String.valueOf( "Name :" + customer.getFirstName() + " " + customer.getLastName() ) );
		address.setText( String.valueOf( "Address: " + customer.getAddress() ) );
		phone.setText( String.valueOf( "Phone :" + customer.getPhoneNum() ) );
		total.setText( String.valueOf( "Total : " + String.format( "%.2f", getTotal( orders ) ) ) );
	}

	private double getTotal( Orders orders )
	{
		double total = 0;
		for ( OrderItem item : orders.getItems() )
		{
			total += ( item.getQuantity() * item.getPrice() );
		}
		return total;
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();
		return true;
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
				Intent intent = new Intent( this, EmployeeManagementActivity.class );
				intent.putExtra( "ACTION", "view" );
				startActivity( intent );

			default:
				return super.onOptionsItemSelected( item );
		}
	}

	public void onClickDeliver( View view )
	{
		orders.setStatus( "FINISH" );
		AccessOrders ac = new AccessOrders();
		ac.updateOrder( orders );
		Toast.makeText( this, "Delivered Successfully", Toast.LENGTH_LONG ).show();
		finish();
	}
}
