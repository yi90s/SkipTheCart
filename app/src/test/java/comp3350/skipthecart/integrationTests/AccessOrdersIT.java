package comp3350.skipthecart.integrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.logic.AccessProducts;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.utils.TestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessOrdersIT
{
	private File tempDB;
	private AccessOrders dummyAccessOrders;
	private OrderItem dummyOrderItem;
	private Product dummyProduct;
	private Customer dummyCustomer;
	private AccessCustomer accessCustomer;
	private AccessProducts accessProducts;

	@Before
	public void setUp() throws IOException
	{
		tempDB = TestUtils.copyDB();
		accessCustomer = new AccessCustomer();
		dummyCustomer = createDummyAccount();
		accessCustomer.addCustomer( dummyCustomer );
		dummyProduct = createDummyProduct();
		dummyAccessOrders = createDummyAccessOrders();
		accessProducts = new AccessProducts();
		accessProducts.insertProduct( "Dummy Product", "7895-DX", "Its a good dummy.",
		                              "999.99", "99" );
		dummyOrderItem = createDummyOrderItem();
	}

	@Test
	public void testAccessOrdersPersistence()
	{
		assertTrue( dummyAccessOrders.getAllOrders().isEmpty() );
		AccessOrders.addOrders( dummyCustomer, dummyOrderItem );
		assertFalse( AccessOrders.getOrders().getItems().isEmpty() );
		dummyAccessOrders.commitOrders();
		assertTrue( AccessOrders.getOrders().getItems().isEmpty() );
		assertFalse( dummyAccessOrders.getAllOrders().isEmpty() );
		assertTrue( dummyAccessOrders.getAllOrders().get( 0 ).getUserId().equals(
			dummyCustomer.getUserID() ) );
	}

	@After
	public void tearDown()
	{
		tempDB.delete();
	}

	private AccessOrders createDummyAccessOrders()
	{
		return new AccessOrders();
	}

	private Product createDummyProduct()
	{
		return new Product( "Dummy Product", "7895-DX", "Its a good dummy.", 999.99, 99, "" );
	}

	private Customer createDummyAccount()
	{
		return new Customer( "John", "Smith", "123 Main st.", "jsmith@email.com", "1234567",
		                     "john1234",
		                     "password" );
	}

	private OrderItem createDummyOrderItem()
	{
		return new OrderItem( dummyProduct, 10, 19.99 );
	}
}
