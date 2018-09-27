package comp3350.skipthecart.logic;

import java.util.Collections;
import java.util.List;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.persistence.ProductPersistence;

public class AccessProducts
{
	private ProductPersistence productPersistence;
	private ProductValidator productValidator;
	private List< Product > products;

	public AccessProducts()
	{
		productPersistence = Services.getProductPersistence();

		if ( productPersistence != null )
		{
			products = productPersistence.getProductSequential();
		}

		productValidator = new ProductValidator();
	}

	public List< Product > getProducts()
	{
		if ( productPersistence != null )
		{
			products = productPersistence.getProductSequential();

			if ( products != null )
			{
				return Collections.unmodifiableList( products );
			}
		}

		return null;
	}

	public Product getProduct( String productSku )
	{
		boolean found = false;
		Product product = null;

		if ( productSku != null && !productSku.isEmpty() )
		{
			if ( products != null && !products.isEmpty() )
			{
				for ( int i = 0; !found && i < products.size(); i++ )
				{
					product = products.get( i );

					if ( product != null && product.getSku() != null &&
					     product.getSku().equals( productSku ) )
					{
						found = true;
					}
					else
					{
						product = null;
					}
				}
			}

			if ( !found && productPersistence != null )
			{
				product = productPersistence.getProduct( productSku );
			}
		}

		return product;
	}

	public boolean insertProduct( String name,
	                              String sku,
	                              String description,
	                              String price,
	                              String qty ) throws ProductException
	{
		Product product;
		boolean result = true;

		if ( productPersistence != null )
		{
			if ( productPersistence.getProduct( sku ) == null )
			{
				product = productValidator.getValidProduct( name, sku, description, price, qty );
				productPersistence.insertProduct( product );
			}
			else
			{
				throw new ProductException( "SKU is already being used" );
			}
		}
		else
		{
			result = false;
		}

		return result;
	}

	public boolean updateProduct( String name,
	                              String sku,
	                              String description,
	                              String price,
	                              String qty ) throws ProductException
	{
		Product product;
		boolean result = true;

		if ( productPersistence != null )
		{
			product = productValidator.getValidProduct( name, sku, description, price, qty );
			productPersistence.updateProduct( product );
		}
		else
		{
			result = false;
		}

		return result;
	}

	public void deleteProduct( Product currentProduct )
	{
		if ( productPersistence != null && currentProduct != null )
		{
			productPersistence.deleteProduct( currentProduct );
		}
	}

	public List< Product > searchProduct( String query )
	{
		List< Product > results = null;

		if ( productPersistence != null && query != null && !query.isEmpty() )
		{
			results = productPersistence.searchProduct( query );
		}

		return results;
	}

	public ProductPersistence getProductPersistence()
	{
		return productPersistence;
	}
}
