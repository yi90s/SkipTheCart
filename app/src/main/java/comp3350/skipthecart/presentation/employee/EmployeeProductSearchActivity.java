package comp3350.skipthecart.presentation.employee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import comp3350.skipthecart.R;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.logic.AccessProducts;
import comp3350.skipthecart.objects.Orders;
import comp3350.skipthecart.objects.Product;

public class EmployeeProductSearchActivity extends AppCompatActivity
{
	private List< Product > productsList;
	private ArrayAdapter< Product > productArrayAdapter;
	private FrameLayout frameLayout;
	private TextView badge;
	private TextView noProducts;
	private String query;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		Toolbar toolbar;
		ActionBar supportActionBar;
		FrameLayout addLayout;

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_products_listing );

		frameLayout = findViewById( R.id.main_layer );
		badge = findViewById( R.id.cart_badge );
		noProducts = findViewById( R.id.noProducts );
		noProducts.setText( R.string.no_products_found );

		addLayout = findViewById( R.id.add_layout );
		addLayout.setVisibility( View.GONE );

		SharedPreferences sp = getSharedPreferences( "SkipTheCart", MODE_PRIVATE );
		Gson gson = new Gson();
		String json = sp.getString( "Orders", "" );
		Log.i( "JSONOBJ", json );

		if ( !json.isEmpty() )
		{
			Orders obj = gson.fromJson( json, Orders.class );
			AccessOrders.setOrders( obj );
		}

		toolbar = findViewById( R.id.toolbar );
		toolbar.setTitle( "Search Results" );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}

		setupEmployeeButtons();

		query = getIntent().getStringExtra( "QUERY" );
		displayData();
	}

	private void setupEmployeeButtons()
	{
		FloatingActionButton addProduct = findViewById( R.id.add_button );
		FloatingActionButton cartOrders = findViewById( R.id.cart_button );

		addProduct.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				onAddProductPressed( v );
			}
		} );

		cartOrders.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				onCartOrdersPressed( v );
			}
		} );
	}

	public void displayData()
	{
		AccessProducts accessProducts;
		GridView gridView;
		AccessOrders accessOrders = new AccessOrders();

		if ( accessOrders.getAllOrders() == null )
		{
			badge.setText( "0" );
		}
		else
		{
			badge.setText(
				String.valueOf( accessOrders.getAllOrders().size() ) );
		}

		try
		{
			accessProducts = new AccessProducts();
			productsList = accessProducts.searchProduct( query );

			if ( productsList == null || productsList.isEmpty() )
			{
				noProducts.setVisibility( View.VISIBLE );
			}
			else
			{
				noProducts.setVisibility( View.GONE );
				frameLayout.setVisibility( View.VISIBLE );
				productArrayAdapter = new ArrayAdapter< Product >( this, R.layout.products_list,
				                                                   R.id.product_name, productsList )
				{
					@NonNull
					@Override
					public View getView( int position,
					                     View convertView,
					                     @NonNull ViewGroup parent )
					{
						View view = super.getView( position, convertView, parent );

						TextView name = view.findViewById( R.id.product_name );
						TextView quantity = view.findViewById( R.id.product_quantity );
						TextView price = view.findViewById( R.id.product_price );
						ImageView imageView = view.findViewById( R.id.image );

						if ( productsList != null && !productsList.isEmpty() )
						{
							String image = productsList.get( position ).getImage();
							byte[] decodedString = Base64.decode( image, Base64.DEFAULT );
							Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0,
							                                                    decodedString.length );
							imageView.setImageBitmap( decodedByte );
							name.setText( productsList.get( position ).getProductName() );
							String qtyStr =
								String.valueOf( productsList.get( position ).getQuantity() ) +
								" units left";
							quantity.setText( qtyStr );
							String priceStr =
								"$ " + String.format( Locale.US, "%.2f", productsList.get(
									position ).getPrice() );
							price.setText( priceStr );
						}
						else
						{
							finish();
						}

						return view;
					}
				};
			}

			gridView = findViewById( R.id.gridView );
			gridView.setAdapter( productArrayAdapter );
			gridView.setOnItemClickListener( new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick( AdapterView< ? > parent,
				                         View view,
				                         int position,
				                         long id )
				{

					Intent displayDetails = new Intent( EmployeeProductSearchActivity.this,
					                                    EmployeeProductDetailsActivity.class );
					displayDetails.putExtra( "SKU", productsList.get( position ).getSku() );
					startActivity( displayDetails );
				}
			} );
		}
		catch ( final Exception e )
		{
			Log.i( "ERR", "err" );
		}
	}

	public void onAddProductPressed( View view )
	{
		Intent intent = new Intent( this, ProductEditActivity.class );
		intent.putExtra( "ACTION", "create" );
		startActivity( intent );
	}

	public void onCartOrdersPressed( View view )
	{
		Intent intent = new Intent( this, OrdersActivity.class );
		startActivity( intent );
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

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();

		return true;
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Orders obj = AccessOrders.getOrders();
		SharedPreferences mPrefs = getSharedPreferences( "SkipTheCart", MODE_PRIVATE );
		SharedPreferences.Editor prefsEditor = mPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson( obj );
		prefsEditor.putString( "Orders", json );
		prefsEditor.apply();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		displayData();
	}
}
