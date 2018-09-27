package comp3350.skipthecart.objects;

public class OrderItem
{
	private Product product;
	private int quantity;
	private double price;
	private int orderId;

	public OrderItem()
	{
		this.product = null;
	}

	public OrderItem( Product product,
	                  int quantity,
	                  double price )
	{
		this.price = price;
		this.product = product;
		this.quantity = quantity;
	}

	public void setOrderId( int orderId )
	{
		this.orderId = orderId;
	}

	public int getOrderId()
	{
		return orderId;
	}

	public double getPrice()
	{
		return price;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setPrice( double price )
	{
		this.price = price;
	}

	public void setProduct( Product product )
	{
		this.product = product;
	}

	public void setQuantity( int quantity )
	{
		this.quantity = quantity;
	}
}
