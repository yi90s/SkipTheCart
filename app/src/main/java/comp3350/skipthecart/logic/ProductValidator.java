package comp3350.skipthecart.logic;

import java.util.Locale;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.persistence.ProductPersistence;

public class ProductValidator
{
	private static final int MAX_SKU = 20;
	private static final int MAX_NAME = 60;
	private static final int MAX_DESCRIPTION = 255;
	private static final int MAX_PRICE_LENGTH = 13;
	private static final int MAX_QTY_LENGTH = 10;
	private static final String DEFAULT_NO_IMAGE = "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";

	public Product getValidProduct( String name,
	                                String sku,
	                                String description,
	                                String price,
	                                String qty ) throws ProductException
	{
		Product result;
		Product tempProduct;
		Double convertedPrice;
		int convertedQty;
		String roundedPrice;
		String image;
		ProductPersistence productPersistence = Services.getProductPersistence();

		if ( name == null || name.isEmpty() || name.length() > MAX_NAME )
		{
			throw new ProductException( "Name is empty or too long" );
		}
		else if ( sku == null || sku.isEmpty() || sku.length() > MAX_SKU )
		{
			throw new ProductException( "SKU is empty or too long" );
		}
		else if ( description == null || description.isEmpty() ||
		          description.length() > MAX_DESCRIPTION )
		{
			throw new ProductException( "Description is empty or too long" );
		}

		if ( price == null || price.isEmpty() )
		{
			throw new ProductException( "No price entered" );
		}
		else
		{
			try
			{
				convertedPrice = Double.parseDouble( price );
				roundedPrice = String.format( Locale.US, "%.2f", convertedPrice );

				if ( roundedPrice.length() > MAX_PRICE_LENGTH )
				{
					throw new NumberFormatException();
				}

				convertedPrice = Double.parseDouble( roundedPrice );
			}
			catch ( NumberFormatException nfe )
			{
				throw new ProductException( "Price is too large" );
			}
		}

		if ( qty == null || qty.isEmpty() )
		{
			throw new ProductException( "No quantity entered" );
		}
		else
		{
			try
			{
				if ( qty.length() > MAX_QTY_LENGTH )
				{
					throw new NumberFormatException();
				}
				else
				{
					convertedQty = Integer.parseInt( qty );
				}
			}
			catch ( NumberFormatException nfe )
			{
				throw new ProductException( "Quantity is too large" );
			}
		}

		tempProduct = productPersistence.getProduct( sku );

		if ( tempProduct != null && tempProduct.getImage() != null &&
		     !tempProduct.getImage().isEmpty() )
		{
			image = tempProduct.getImage();
		}
		else
		{
			image = DEFAULT_NO_IMAGE;
		}

		result = new Product( name, sku, description, convertedPrice, convertedQty, image );

		return result;
	}
}
