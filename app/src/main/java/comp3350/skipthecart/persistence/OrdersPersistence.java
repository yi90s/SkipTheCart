package comp3350.skipthecart.persistence;

import java.util.List;

import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Orders;

public interface OrdersPersistence
{
	List< Orders > getOrdersFromUserId( String userId );

	Orders getOrdersFromOrderId( int orderid );

	List< Orders > getAllOrders();

	List< OrderItem > getOrderItems( int orderId );

	boolean addOrders( Orders order );

	boolean updateOrder( Orders order );

	boolean addOrderItems( Orders order );
}
