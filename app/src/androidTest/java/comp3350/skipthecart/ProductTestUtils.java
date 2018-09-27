package comp3350.skipthecart;

import comp3350.skipthecart.objects.Product;

public class ProductTestUtils
{
	private final static String productName = "shampoo";
	private final static String productSKU = "4321-001-F12-02";
	private final static String description = "make your hair feel good~";
	private final static String image = null;
	private final static double price = 10.99;
	private final static int quantity = 100;
	private static Product addedProduct = new Product( productName, productSKU, description, price,
	                                                   quantity );

	public static Product getAddedProduct()
	{
		return addedProduct;
	}
}
