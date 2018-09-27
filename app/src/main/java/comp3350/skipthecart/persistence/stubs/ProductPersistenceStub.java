package comp3350.skipthecart.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.persistence.ProductPersistence;

public class ProductPersistenceStub implements ProductPersistence
{
	private List< Product > products;

	public ProductPersistenceStub()
	{
		products = new ArrayList<>();

		products.add( new Product( "Coke 355 mL Can", "1234", "A single can of Coke.", 1.50, 10,
		                           "" ) );
		products.add( new Product( "Bacon", "8543", "Mmmmmm....bacon.", 9.99, 5,
		                           "" ) );
		products.add( new Product( "Bananas", "7318", "Yummy, nutritious bananas.", 2.99, 15,
		                           "" ) );
		products.add( new Product( "Chips Ahoy!", "5584", "nom nom nom", 6.99, 10,
		                           "" ) );
		products.add(
			new Product( "Carton of Eggs", "9436", "Good for eating or throwing at people.", 4.99,
			             15,
			             "" ) );
		products.add( new Product( "Potato Chips", "7256", "Bag of air with some chips.", 2.99, 10,
		                           "" ) );
		products.add( new Product( "Shredded Cheese", "4198", "Cheeeeeeeeese!", 6.99, 5,
		                           "" ) );
	}

	@Override
	public List< Product > getProductSequential()
	{
		if ( products != null )
		{
			return Collections.unmodifiableList( products );
		}

		return null;
	}

	@Override
	public Product getProduct( String argSku )
	{
		boolean found = false;
		Product product = null;
		String currSku;

		if ( products != null && argSku != null )
		{
			for ( int i = 0; !found && i < products.size(); i++ )
			{
				product = products.get( i );

				if ( product != null )
				{
					currSku = product.getSku();

					if ( currSku != null && currSku.equals( argSku ) )
					{
						found = true;
					}
					else
					{
						product = null;
					}
				}
			}
		}

		return product;
	}

	@Override
	public Product insertProduct( Product currentProduct )
	{
		if ( products != null && currentProduct != null )
		{
			if ( getProductIndex( currentProduct ) < 0 )
			{
				products.add( currentProduct );
			}
			else
			{
				updateProduct( currentProduct );
			}
		}

		return currentProduct;
	}

	@Override
	public Product updateProduct( Product currentProduct )
	{
		int index;

		if ( products != null && currentProduct != null )
		{
			index = getProductIndex( currentProduct );

			if ( index >= 0 )
			{
				products.set( index, currentProduct );
			}
			else
			{
				products.add( currentProduct );
			}
		}

		return currentProduct;
	}

	@Override
	public void deleteProduct( Product currentProduct )
	{
		int index;

		if ( products != null && currentProduct != null )
		{
			index = getProductIndex( currentProduct );

			if ( index >= 0 )
			{
				products.remove( index );
			}
		}
	}

	@Override
	public List< Product > searchProduct( String query )
	{
		Product currProd;
		String currName;
		String[] words;
		boolean isMatch;
		List< Product > results = new ArrayList<>();

		if ( products != null && !products.isEmpty() &&
		     query != null && !query.isEmpty() )
		{
			words = query.trim().split( "\\s+" );

			for ( int i = 0; i < products.size(); i++ )
			{
				currProd = products.get( i );

				if ( currProd != null )
				{
					currName = currProd.getProductName();

					if ( currName != null )
					{
						isMatch = true;

						for ( int j = 0; j < words.length && isMatch; j++ )
						{
							isMatch = currName.toUpperCase().contains( words[j].toUpperCase() );
						}

						if ( isMatch )
						{
							results.add( currProd );
						}
					}
				}
			}
		}

		return results;
	}

	private int getProductIndex( Product currentProduct )
	{
		int index = -1;
		Product product;
		String sku;

		if ( products != null && currentProduct != null && currentProduct.getSku() != null )
		{
			for ( int i = 0; i < products.size() && index == -1; i++ )
			{
				product = products.get( i );

				if ( product != null )
				{
					sku = product.getSku();

					if ( sku != null && sku.equals( currentProduct.getSku() ) )
					{
						index = i;
					}
				}
			}
		}

		return index;
	}
}
