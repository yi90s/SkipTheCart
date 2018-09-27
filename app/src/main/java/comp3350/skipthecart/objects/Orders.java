package comp3350.skipthecart.objects;

import java.util.ArrayList;
import java.util.List;

public class Orders
{
	private List< OrderItem > items;
	private String userId;
	private String status;
	private int orderId;

	public Orders()
	{
		this.items = new ArrayList<>();
	}

	public Orders( List< OrderItem > items,
	               String userId,
	               String status )
	{
		this.items = items;
		this.userId = userId;
		this.status = status;
	}

	public void setStatus( String status )
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}

	public List< OrderItem > getItems()
	{
		return items;
	}

	public void setItems( List< OrderItem > items )
	{
		this.items = items;
	}

	public void setOrderId( int orderId )
	{
		this.orderId = orderId;
	}

	public void setUserId( String userId )
	{
		this.userId = userId;
	}

	public int getOrderId()
	{
		return orderId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void addItems( OrderItem item )
	{
		this.items.add( item );
	}
}
