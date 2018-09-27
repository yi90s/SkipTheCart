package comp3350.skipthecart.presentation.customer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import comp3350.skipthecart.R;
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.logic.AccessProducts;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Product;

public class CustomerProductDetailsActivity extends AppCompatActivity
{
	private Toolbar toolbar;
	private TextView productName;
	private TextView productPrice;
	private TextView productQuantity;
	private TextView productDescription;
	private ImageView productImage;
	private Product product;
	private AccessProducts accessProducts;
	private String sku;
	private Customer customer;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		ActionBar supportActionBar;
		Button button;

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_product_details );

		toolbar = findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}

		productName = findViewById( R.id.product_name );
		productPrice = findViewById( R.id.product_price );
		productQuantity = findViewById( R.id.product_quantity );
		productDescription = findViewById( R.id.product_description );
		productImage = findViewById( R.id.product_image );

		button = findViewById( R.id.details_button );
		button.setText( R.string.add_to_cart );
		button.setOnClickListener( new View.OnClickListener()
		{
			public void onClick( View v )
			{
				onProductAddToCart( v );
			}
		} );

		customer = AccessCustomer.getCurrentCustomer();
		sku = getIntent().getStringExtra( "SKU" );
		accessProducts = new AccessProducts();
		product = accessProducts.getProduct( sku );

		if ( product != null )
		{
			toolbar.setTitle( product.getProductName() );
			displayDetails();
		}
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

	private void displayDetails()
	{
		product = accessProducts.getProduct( sku );

		productName.setText( product.getProductName() );
		productPrice.setText( String.format( Locale.US, "%.2f", product.getPrice() ) );
		productDescription.setText( product.getDescription() );
		productQuantity.setText( String.valueOf( product.getQuantity() ) );

		String image = product.getImage();
		byte[] decodedString = Base64.decode( image, Base64.DEFAULT );
		Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0,
		                                                    decodedString.length );
		productImage.setImageBitmap( decodedByte );
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();

		return true;
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		customer = AccessCustomer.getCurrentCustomer();
		accessProducts = new AccessProducts();
		product = accessProducts.getProduct( sku );

		if ( product != null )
		{
			toolbar.setTitle( product.getProductName() );
			displayDetails();
		}
		else
		{
			finish();
		}
	}

	public void onProductAddToCart( View view )
	{
		OrderItem addedItems;

		if ( customer != null && product != null )
		{
			addedItems = new OrderItem( product, 1, product.getPrice() );
			AccessOrders.addOrders( customer, addedItems );
			Toast.makeText( this, "Added to Cart", Toast.LENGTH_SHORT ).show();
		}
	}
}
