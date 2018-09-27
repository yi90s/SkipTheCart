package comp3350.skipthecart.objectTests;

import org.junit.Test;

import comp3350.skipthecart.objects.Product;

import static junit.framework.Assert.assertEquals;

public class ProductTest
{
	private String dummyImage = createDummyImage();
	private Product dummyProduct = createDummyProduct();

	@Test
	public void testProductName()
	{
		assertEquals( "Dummy Product", dummyProduct.getProductName() );
		dummyProduct.setProductName( "Test" );
		assertEquals( "Test", dummyProduct.getProductName() );
	}

	@Test
	public void testSKU()
	{
		assertEquals( "1234", dummyProduct.getSku() );
		dummyProduct.setSku( "9876" );
		assertEquals( "9876", dummyProduct.getSku() );
	}

	@Test
	public void testDescription()
	{
		assertEquals( "Its a good dummy.", dummyProduct.getDescription() );
		dummyProduct.setDescription( "Some test?" );
		assertEquals( "Some test?", dummyProduct.getDescription() );
	}

	@Test
	public void testPrice()
	{
		assertEquals( 999.99, dummyProduct.getPrice() );
		dummyProduct.setPrice( 0.0 );
		assertEquals( 0.0, dummyProduct.getPrice() );
	}

	@Test
	public void testQuantity()
	{
		assertEquals( 99, dummyProduct.getQuantity() );
		dummyProduct.setQuantity( 0 );
		assertEquals( 0, dummyProduct.getQuantity() );
	}

	@Test
	public void testImage()
	{
		assertEquals( dummyImage, dummyProduct.getImage() );
		String newDummyImage = "TEST2";
		dummyProduct.setImage( newDummyImage );
		assertEquals( newDummyImage, dummyProduct.getImage() );
	}

	private String createDummyImage()
	{
		return "TEST";
	}

	private Product createDummyProduct()
	{
		return new Product( "Dummy Product", "1234", "Its a good dummy.", 999.99, 99, "TEST" );
	}
}
