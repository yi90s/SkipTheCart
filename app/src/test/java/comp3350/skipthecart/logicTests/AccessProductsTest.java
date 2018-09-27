package comp3350.skipthecart.logicTests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.AccessProducts;
import comp3350.skipthecart.logic.ProductException;
import comp3350.skipthecart.objects.Product;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessProductsTest
{
	private AccessProducts dummyAccessProducts;
	private Product dummyProduct;

	@BeforeClass
	public static void setStubs()
	{
		Services.setStubs( true );
	}

	@Before
	public void setup()
	{
		dummyAccessProducts = new AccessProducts();
		dummyProduct = createDummyProduct();
		dummyAccessProducts.deleteProduct( dummyProduct );
	}

	@Test
	public void testServiceSet()
	{
		assertEquals( dummyAccessProducts.getProductPersistence(),
		              Services.getProductPersistence() );
	}

	@Test( expected = ProductException.class )
	public void testInsertProduct()
	{
		assertTrue( dummyAccessProducts.insertProduct( "Dummy Product", "1234", "Its a good dummy.",
		                                               "999.99", "99" ) );
		assertEquals( dummyProduct.getSku(),
		              ( ( dummyAccessProducts.getProductPersistence() ).getProduct(
			              "1234" ).getSku() ) );
		dummyAccessProducts.insertProduct( "Dummy Product", "1234", "Its a good dummy.", "999.99",
		                                   "99" );
	}

	@Test
	public void testUpdateProduct()
	{
		assertTrue( dummyAccessProducts.insertProduct( "Dummy Product", "1234", "Its a good dummy.",
		                                               "999.99", "99" ) );
		assertTrue(
			dummyAccessProducts.updateProduct( "Dummy Product", "1234", "Its a good dummy.", "5",
			                                   "5" ) );
		assertEquals( 5.0, ( dummyAccessProducts.getProduct( "1234" ) ).getPrice() );
		assertEquals( 5, ( dummyAccessProducts.getProduct( "1234" ) ).getQuantity() );
	}

	@Test
	public void testDeleteProduct()
	{
		dummyAccessProducts.insertProduct( "Dummy Product", "1234", "Its a good dummy.", "999.99",
		                                   "99" );
		dummyAccessProducts.deleteProduct( dummyProduct );
		assertFalse( dummyAccessProducts.getProducts().contains( dummyProduct ) );
	}

	@Test
	public void testSearchProduct()
	{
		List< Product > results;

		results = dummyAccessProducts.searchProduct( dummyProduct.getProductName() );
		assertTrue( results.isEmpty() );
		dummyAccessProducts.insertProduct( "Dummy Product", "1234", "Its a good dummy.", "999.99",
		                                   "99" );
		results = dummyAccessProducts.searchProduct( dummyProduct.getProductName() );
		assertFalse( results.isEmpty() );
		assertTrue( results.get( 0 ).getSku().equals( dummyProduct.getSku() ) );
		results = dummyAccessProducts.searchProduct( "No Name" );
		assertTrue( results.isEmpty() );
		dummyAccessProducts.deleteProduct( dummyProduct );
		results = dummyAccessProducts.searchProduct( dummyProduct.getProductName() );
		assertTrue( results.isEmpty() );
		results = dummyAccessProducts.searchProduct( "No Name" );
		assertTrue( results.isEmpty() );
	}

	private Product createDummyProduct()
	{
		return new Product( "Dummy Product", "1234", "Its a good dummy.", 999.99, 99, "" );
	}
}
