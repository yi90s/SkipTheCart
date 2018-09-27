package comp3350.skipthecart.logic;

import java.util.List;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Orders;
import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.persistence.ProductPersistence;

public class OrdersValidator
{
	public void validate( Orders orders ) throws OrdersException
	{
		List< OrderItem > items;
		OrderItem currItem;
		Product currProduct;
		Product compareProduct;
		String currSku;
		ProductPersistence productPersistence;

		if ( orders != null && orders.getItems() != null &&
		     !orders.getItems().isEmpty() )
		{
			items = orders.getItems();
			productPersistence = Services.getProductPersistence();

			for ( int i = 0; i < items.size(); i++ )
			{
				currItem = items.get( i );

				if ( currItem != null && currItem.getProduct() != null )
				{
					currProduct = currItem.getProduct();
					currSku = currProduct.getSku();

					if ( currSku != null )
					{
						compareProduct = productPersistence.getProduct( currSku );

						if ( compareProduct != null &&
						     compareProduct.getQuantity() < currItem.getQuantity() )
						{
							throw new OrdersException( compareProduct.getProductName() +
							                           " (QTY " + currItem.getQuantity() +
							                           ") exceeds our current stock. Your" +
							                           " order will not be processed." );
						}
					}
				}
			}
		}
		else
		{
			throw new OrdersException( "No orders to process" );
		}
	}
}
