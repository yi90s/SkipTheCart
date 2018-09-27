package comp3350.skipthecart.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Orders;
import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.persistence.OrdersPersistence;

public class OrdersPersistenceStub implements OrdersPersistence
{
	private List< Orders > orders;

	public OrdersPersistenceStub()
	{
		orders = new ArrayList<>();
	}

	@Override
	public List< Orders > getOrdersFromUserId( String userId )
	{
		List< Orders > result = new ArrayList<>();

		if ( userId != null )
		{
			for ( int i = 0; i < orders.size(); i++ )
			{
				Orders currOrder = orders.get( i );

				if ( currOrder.getUserId().equals( userId ) )
				{
					result.add( currOrder );
				}
			}
		}

		return result;
	}

	@Override
	public Orders getOrdersFromOrderId( int orderid )
	{
		return null;
	}

	@Override
	public List< Orders > getAllOrders()
	{
		return orders;
	}

	public List< OrderItem > getOrderItems( int orderId )
	{
		List< OrderItem > result = new ArrayList<>();

		if ( orderId > 0 )
		{
			for ( int i = 0; i < orders.size(); i++ )
			{
				Orders currOrder = orders.get( i );

				if ( currOrder.getOrderId() == orderId )
				{
					result = currOrder.getItems();
				}
			}
		}

		return result;
	}

	public boolean addOrders( Orders order )
	{
		boolean result = false;

		if ( order != null )
		{
			order.setOrderId( orders.size() + 1 );
			orders.add( order );
			addOrderItems( order );
			result = true;
		}

		return result;
	}

	@Override
	public boolean updateOrder( Orders order )
	{
		return false;
	}

	public boolean addOrderItems( Orders order )
	{
		List< OrderItem > items;
		OrderItem currItem;
		int orderID;
		Product currProd;
		boolean result = false;

		if ( order != null )
		{
			items = order.getItems();
			orderID = order.getOrderId();

			if ( items != null )
			{
				for ( int i = 0; i < items.size(); i++ )
				{
					currItem = items.get( i );

					if ( currItem != null && currItem.getProduct() != null )
					{
						currItem.setOrderId( orderID );
						currProd = currItem.getProduct();
						currProd.setQuantity( currProd.getQuantity() -
						                      currItem.getQuantity() );
						result = true;
					}
				}
			}
		}

		return result;
	}
}
