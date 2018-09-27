package comp3350.skipthecart.persistence;

import java.util.List;

import comp3350.skipthecart.objects.Product;

public interface ProductPersistence
{
	List< Product > getProductSequential();

	Product getProduct( String sku );

	Product insertProduct( Product currentProduct );

	Product updateProduct( Product currentProduct );

	void deleteProduct( Product currentProduct );

	List< Product > searchProduct( String query );
}
