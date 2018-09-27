package comp3350.skipthecart.logic;

import java.util.List;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Orders;
import comp3350.skipthecart.persistence.OrdersPersistence;

public class AccessOrders
{
	private static Orders currentOrders = null;
	private OrdersPersistence ordersPersistence;
	private OrdersValidator ordersValidator;

	public AccessOrders()
	{
		ordersPersistence = Services.getOrdersPersistence();
		ordersValidator = new OrdersValidator();
	}

	public static Orders getOrders()
	{
		if ( currentOrders == null )
		{
			currentOrders = new Orders();
		}

		return currentOrders;
	}

	public static Orders getOrderById( int orderId )
	{
		Orders items = new Orders();

		items = Services.getOrdersPersistence().getOrdersFromOrderId( orderId );

		return items;
	}

	public static void setOrders( Orders item )
	{
		currentOrders = item;
	}

	public static void addOrders( Customer customer,
	                              OrderItem item )
	{
		boolean found = false;

		if ( customer != null && item != null )
		{
			if ( currentOrders == null )
			{
				currentOrders = new Orders();
			}

			currentOrders.setUserId( customer.getUserID() );

			for ( int i = 0; i < currentOrders.getItems().size() && !found; i++ )
			{
				if ( currentOrders.getItems().get( i ).getProduct().getSku().equals(
					item.getProduct().getSku() ) )
				{
					currentOrders.getItems().get( i ).setQuantity(
						currentOrders.getItems().get( i ).getQuantity() + item.getQuantity() );
					found = true;
				}
			}

			if ( !found )
			{
				currentOrders.addItems( item );
			}
		}
	}

	public static void clearOrders()
	{
		currentOrders = null;
	}

	public List< Orders > getAllOrders()
	{
		List< Orders > result = null;

		if ( ordersPersistence != null )
		{
			result = ordersPersistence.getAllOrders();
		}

		return result;
	}

	public void commitOrders() throws OrdersException
	{
		if ( ordersPersistence != null && currentOrders != null )
		{
			currentOrders.setStatus( "LIVE" );
			ordersValidator.validate( currentOrders );
			ordersPersistence.addOrders( currentOrders );
			clearOrders();
		}
	}

	public boolean updateOrder( Orders order )
	{
		boolean result = ordersPersistence.updateOrder( order );
		return result;
	}

	public OrdersPersistence getOrdersPersistence()
	{
		return ordersPersistence;
	}
}
