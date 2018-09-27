package comp3350.skipthecart.presentation.employee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp3350.skipthecart.R;
import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.AccessEmployee;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.objects.Orders;

public class OrdersActivity extends AppCompatActivity
{
	private TextView live;
	private TextView old;
	AccessOrders accessOrders;
	ListView listView;
	ListView historyList;
	TextView noOrdersMsg;
	List< Orders > orders;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		Toolbar toolbar;
		ActionBar supportActionBar;

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_orders );
		toolbar = findViewById( R.id.toolbar );
		toolbar.setTitle( "Orders" );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		live = ( TextView )findViewById( R.id.live_orders );
		old = ( TextView )findViewById( R.id.orders_history );

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}
	}

	private void reloadPage()
	{
		if ( getLiveOrdersCount( Services.getOrdersPersistence().getAllOrders() ) > 0 )
		{
			live.setVisibility( View.GONE );
		}
		else
		{
			live.setVisibility( View.VISIBLE );
		}
		if ( getHistoryCount( Services.getOrdersPersistence().getAllOrders() ) > 0 )
		{
			old.setVisibility( View.GONE );
		}
		else
		{
			old.setVisibility( View.VISIBLE );
		}
		accessOrders = new AccessOrders();
		orders = accessOrders.getAllOrders();

		if ( orders != null && !orders.isEmpty() )
		{
			String type = "LIVE";
			if ( AccessEmployee.getCurrentEmployee().getPosition().equals( "DRIVER" ) )
			{
				type = "DELIVERY";
			}
			final List< Orders > liveOrders = getOrders( orders, type );
			ArrayAdapter< Orders > orderArrayAdapter = getArrayAdapter( liveOrders );
			listView = findViewById( R.id.ordersList );
			listView.setAdapter( orderArrayAdapter );
			listView.setOnItemClickListener( new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick( AdapterView< ? > parent,
				                         View view,
				                         int position,
				                         long id )
				{

					Intent displayDetails;
					if ( AccessEmployee.getCurrentEmployee().getPosition().equals( "DRIVER" ) )
					{
						displayDetails = new Intent( OrdersActivity.this,
						                             DeliveryInfoActivity.class );
					}
					else
					{
						displayDetails = new Intent( OrdersActivity.this,
						                             EmployeeCartViewActivity.class );
					}
					displayDetails.putExtra( "ORDERID", liveOrders.get( position ).getOrderId() );
					startActivity( displayDetails );
				}
			} );

			final List< Orders > historyOrders = getOrders( orders, "FINISH" );
			ArrayAdapter< Orders > historyArrayAdapter = getArrayAdapter( historyOrders );
			historyList = findViewById( R.id.ordersHistoryList );
			historyList.setAdapter( historyArrayAdapter );
			historyList.setOnItemClickListener( new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick( AdapterView< ? > parent,
				                         View view,
				                         int position,
				                         long id )
				{
					Intent displayDetails;
					if ( AccessEmployee.getCurrentEmployee().getPosition().equals( "DRIVER" ) )
					{
						displayDetails = new Intent( OrdersActivity.this,
						                             DeliveryInfoActivity.class );
					}
					else
					{
						displayDetails = new Intent( OrdersActivity.this,
						                             EmployeeCartViewActivity.class );
					}
					displayDetails.putExtra( "ORDERID",
					                         historyOrders.get( position ).getOrderId() );
					startActivity( displayDetails );
				}
			} );
		}
		else
		{
			noOrdersMsg = findViewById( R.id.noOrdersMsg );
			noOrdersMsg.setVisibility( View.VISIBLE );
		}
	}

	private ArrayAdapter< Orders > getArrayAdapter( final List< Orders > orders )
	{
		ArrayAdapter< Orders > orderArrayAdapter = new ArrayAdapter< Orders >( this,
		                                                                       R.layout.order_items,
		                                                                       R.id.userId,
		                                                                       orders )
		{
			@NonNull
			@Override
			public View getView( int position,
			                     View convertView,
			                     @NonNull ViewGroup parent )
			{
				View view = super.getView( position, convertView, parent );

				TextView oid = view.findViewById( R.id.orderId );
				TextView cid = view.findViewById( R.id.userId );

				oid.setText(
					"Order ID: " + String.valueOf( orders.get( position ).getOrderId() ) );
				cid.setText(
					"User ID: " + String.valueOf( orders.get( position ).getUserId() ) );

				return view;
			}
		};
		return orderArrayAdapter;
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
	protected void onResume()
	{
		super.onResume();
		reloadPage();
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

	private int getLiveOrdersCount( List< Orders > orders )
	{
		int result = 0;

		for ( Orders orders1 : orders )
		{

			if ( orders1.getStatus().equals( "LIVE" ) || orders1.getStatus().equals( "DELIVERY" ) )
			{
				result++;
			}
		}
		return result;
	}

	private List< Orders > getOrders( List< Orders > orders,
	                                  String type )
	{
		List< Orders > result = new ArrayList<>();

		for ( Orders orders1 : orders )
		{
			if ( type.equals( "LIVE" ) && ( orders1.getStatus().equals( type ) ||
			                                orders1.getStatus().equals( "DELIVERY" ) ) )
			{
				result.add( orders1 );
			}
			else if ( type.equals( "DELIVERY" ) && orders1.getStatus().equals( "DELIVERY" ) )
			{
				result.add( orders1 );
			}
			else if ( type.equals( "FINISH" ) && orders1.getStatus().equals( type ) )
			{
				result.add( orders1 );
			}
		}
		return result;
	}

	private int getHistoryCount( List< Orders > orders )
	{
		return orders.size() - getLiveOrdersCount( orders );
	}
}
