package comp3350.skipthecart.presentation.employee;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import comp3350.skipthecart.R;
import comp3350.skipthecart.logic.AccessProducts;
import comp3350.skipthecart.logic.ProductException;
import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.presentation.Messages;

public class ProductEditActivity extends AppCompatActivity
{
	private EditText productName;
	private EditText productSKU;
	private EditText productPrice;
	private EditText productQty;
	private EditText productDescription;
	private Product passedProduct;
	private AccessProducts accessProducts;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		LinearLayout createLayout;
		LinearLayout updateLayout;
		Toolbar toolbar;
		ActionBar supportActionBar;
		String action;
		String sku;
		boolean noAction = false;

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_product_edit );
		this.getWindow().setSoftInputMode(
			WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );

		toolbar = findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		supportActionBar = getSupportActionBar();

		if ( supportActionBar != null )
		{
			supportActionBar.setDisplayHomeAsUpEnabled( true );
			supportActionBar.setDisplayShowHomeEnabled( true );
		}

		productName = findViewById( R.id.product_name );
		productSKU = findViewById( R.id.product_sku );
		productPrice = findViewById( R.id.product_price );
		productQty = findViewById( R.id.product_quantity );
		productDescription = findViewById( R.id.product_description );
		createLayout = findViewById( R.id.create_layout );
		updateLayout = findViewById( R.id.update_layout );

		action = getIntent().getStringExtra( "ACTION" );
		sku = getIntent().getStringExtra( "SKU" );
		accessProducts = new AccessProducts();

		if ( action != null && !action.isEmpty() )
		{
			switch ( action )
			{
				case "create":
					toolbar.setTitle( "New Product" );
					updateLayout.setVisibility( LinearLayout.GONE );

					break;

				case "edit":
					if ( sku != null && !sku.isEmpty() &&
					     accessProducts.getProduct( sku ) != null )
					{
						passedProduct = accessProducts.getProduct( sku );
						toolbar.setTitle( passedProduct.getProductName() );
						assignValues();
						createLayout.setVisibility( LinearLayout.GONE );
						productSKU.setFocusable( false );
						productSKU.setBackground( null );
					}
					else
					{
						Toast.makeText( getApplicationContext(), "Product not found",
						                Toast.LENGTH_SHORT ).show();
						finish();
					}

					break;

				default:
					noAction = true;

					break;
			}
		}
		else
		{
			noAction = true;
		}

		if ( noAction )
		{
			Toast.makeText( getApplicationContext(), "No ACTION specified",
			                Toast.LENGTH_SHORT ).show();
			finish();
		}
	}

	private void assignValues()
	{
		productName.setText( passedProduct.getProductName() );
		productSKU.setText( passedProduct.getSku() );
		productQty.setText( String.valueOf( passedProduct.getQuantity() ) );
		productPrice.setText( String.valueOf( passedProduct.getPrice() ) );
		productDescription.setText( passedProduct.getDescription() );
	}

	public void onProductCreateClick( View view )
	{
		String name = productName.getText().toString();
		String sku = productSKU.getText().toString();
		String description = productDescription.getText().toString();
		String price = productPrice.getText().toString();
		String qty = productQty.getText().toString();

		try
		{
			if ( accessProducts.insertProduct( name, sku, description, price, qty ) )
			{
				finish();
			}
			else
			{
				Toast.makeText( getApplicationContext(), "Product could not be added",
				                Toast.LENGTH_SHORT ).show();
			}
		}
		catch ( ProductException pe )
		{
			Messages.showDialogBox( this, pe.getMessage() );
		}
	}

	public void onProductUpdateClick( View view )
	{
		String name = productName.getText().toString();
		String sku = productSKU.getText().toString();
		String description = productDescription.getText().toString();
		String price = productPrice.getText().toString();
		String qty = productQty.getText().toString();

		try
		{
			if ( accessProducts.updateProduct( name, sku, description, price, qty ) )
			{
				finish();
			}
			else
			{
				Toast.makeText( getApplicationContext(), "Product could not be updated",
				                Toast.LENGTH_SHORT ).show();
			}
		}
		catch ( ProductException pe )
		{
			Messages.showDialogBox( this, pe.getMessage() );
		}
	}

	public void onProductDeleteClick( View view )
	{
		accessProducts.deleteProduct(
			new Product( "", productSKU.getText().toString(), "", 0, 0 ) );
		finish();
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();

		return true;
	}
}
