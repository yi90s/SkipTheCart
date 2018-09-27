package comp3350.skipthecart.integrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.skipthecart.logic.AccessProducts;
import comp3350.skipthecart.logic.ProductException;
import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.utils.TestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AccessProductsIT
{
	private File tempDB;
	private AccessProducts accessProducts;
	private Product dummyProduct;
	private String productName;
	private String sku;
	private String description;
	private String price;
	private String quantity;

	@Before
	public void setUp() throws IOException
	{
		tempDB = TestUtils.copyDB();
		accessProducts = new AccessProducts();
		productName = "test";
		sku = "123ABC";
		description = "this is a test";
		price = "9.99";
		quantity = "10";
		dummyProduct = new Product( productName, sku, description, Double.parseDouble( price ),
		                            Integer.parseInt( quantity ) );
	}

	@Test( expected = ProductException.class )
	public void testInsertProduct()
	{
		Product result;

		assertTrue( accessProducts.insertProduct( productName, sku, description,
		                                          price, quantity ) );

		assertNotNull( accessProducts.getProduct( sku ) );

		result = accessProducts.getProduct( sku );

		assertTrue( result.getProductName().equals( productName ) );
		assertTrue( result.getSku().equals( sku ) );
		assertTrue( result.getDescription().equals( description ) );
		assertTrue( price.equals( "" + result.getPrice() ) );
		assertTrue( quantity.equals( "" + result.getQuantity() ) );

		accessProducts.deleteProduct( dummyProduct );
		assertNull( accessProducts.getProduct( sku ) );
		assertTrue( accessProducts.insertProduct( productName, sku, description,
		                                          price, quantity ) );
		accessProducts.insertProduct( productName, sku, description, price, quantity );
	}

	@After
	public void tearDown()
	{
		tempDB.delete();
	}
}
