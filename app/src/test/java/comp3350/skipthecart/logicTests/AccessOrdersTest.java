package comp3350.skipthecart.logicTests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessOrders;
import comp3350.skipthecart.logic.AccessProducts;
import comp3350.skipthecart.logic.OrdersException;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Orders;
import comp3350.skipthecart.objects.Product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessOrdersTest
{
	private AccessOrders dummyAccessOrders;
	private OrderItem dummyOrderItem;
	private Product dummyProduct;

	@BeforeClass
	public static void setupForAll()
	{
		AccessCustomer.setCurrentCustomer( createDummyAccount() );
		Services.setStubs( true );
	}

	@AfterClass
	public static void tearDownForAll()
	{
		AccessCustomer.setCurrentCustomer( null );
	}

	@Before
	public void setupForEach()
	{
		dummyProduct = createDummyProduct();
		dummyAccessOrders = createDummyAccessOrders();
		dummyOrderItem = createDummyOrderItem();
	}

	@After
	public void tearDownForEach()
	{
		AccessOrders.clearOrders();
	}

	@Test
	public void testConstructor()
	{
		assertEquals( Services.getOrdersPersistence(), dummyAccessOrders.getOrdersPersistence() );
	}

	@Test
	public void testSetAndGet()
	{
		Orders resultOrders;
		Orders dummyOrders = new Orders();

		AccessOrders.setOrders( dummyOrders );
		resultOrders = AccessOrders.getOrders();
		assertEquals( dummyOrders, resultOrders );
	}

	@Test
	public void testAddOrders()
	{
		AccessOrders.addOrders( AccessCustomer.getCurrentCustomer(), null );
		assertTrue( AccessOrders.getOrders().getItems().isEmpty() );
		AccessOrders.addOrders( AccessCustomer.getCurrentCustomer(), dummyOrderItem );
		assertFalse( AccessOrders.getOrders().getItems().isEmpty() );
	}

	@Test
	public void testCommitOrders()
	{
		AccessOrders.addOrders( AccessCustomer.getCurrentCustomer(), dummyOrderItem );
		dummyAccessOrders.commitOrders();
		assertTrue( AccessOrders.getOrders().getItems().isEmpty() );
	}

	@Test
	public void testGetAllOrder()
	{
		AccessOrders.addOrders( AccessCustomer.getCurrentCustomer(), dummyOrderItem );
		assertTrue( dummyAccessOrders.getAllOrders().isEmpty() );
		dummyAccessOrders.commitOrders();
		assertFalse( dummyAccessOrders.getAllOrders().isEmpty() );
		assertTrue( dummyAccessOrders.getAllOrders().get( 0 ).getUserId().equals(
			AccessCustomer.getCurrentCustomer().getUserID() ) );
	}

	@Test( expected = OrdersException.class )
	public void testProductQtyExceeded()
	{
		AccessProducts accessProducts = new AccessProducts();
		Product testProduct = new Product( "Test quantity", "Test quantity", "Test quantity", 1,
		                                   1 );
		OrderItem testOrderItem = new OrderItem( testProduct, 2, 2 );

		assertTrue(
			accessProducts.insertProduct( "Test quantity", "Test quantity", "Test quantity", "1",
			                              "1" ) );
		AccessOrders.addOrders( AccessCustomer.getCurrentCustomer(), testOrderItem );
		dummyAccessOrders.commitOrders();
	}

	private AccessOrders createDummyAccessOrders()
	{
		return new AccessOrders();
	}

	private Product createDummyProduct()
	{
		return new Product( "Dummy Product", "1234", "Its a good dummy.", 999.99, 99, "" );
	}

	private static Customer createDummyAccount()
	{
		return new Customer( "John", "Smith", "123 Main st.", "jsmith@email.com", "1234567", "1234",
		                     "password" );
	}

	private OrderItem createDummyOrderItem()
	{
		return new OrderItem( dummyProduct, 10, 19.99 );
	}
}
