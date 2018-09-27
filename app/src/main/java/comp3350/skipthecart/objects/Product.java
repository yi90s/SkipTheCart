package comp3350.skipthecart.objects;

public class Product
{
	private String productName;
	private String sku;
	private String description;
	private double price;
	private long quantity;
	private String image;

	public Product( final String productName,
	                final String sku,
	                final String description,
	                final double price,
	                final int quantity,
	                final String image )
	{
		this.productName = productName;
		this.sku = sku;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}

	public Product( final String productName,
	                final String sku,
	                final String description,
	                final double price,
	                final int quantity )
	{
		this.productName = productName;
		this.sku = sku;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.image = null;
	}

	public String getProductName()
	{
		return productName;
	}

	public String getSku()
	{
		return sku;
	}

	public String getDescription()
	{
		return description;
	}

	public double getPrice()
	{
		return price;
	}

	public long getQuantity()
	{
		return quantity;
	}

	public String getImage()
	{
		return image;
	}

	public void setProductName( final String productName )
	{
		this.productName = productName;
	}

	public void setSku( final String sku )
	{
		this.sku = sku;
	}

	public void setDescription( final String description )
	{
		this.description = description;
	}

	public void setPrice( final double price )
	{
		this.price = price;
	}

	public void setQuantity( final long quantity )
	{
		this.quantity = quantity;
	}

	public void setImage( final String image )
	{
		this.image = image;
	}
}
