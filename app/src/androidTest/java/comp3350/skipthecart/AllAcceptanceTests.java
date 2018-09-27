package comp3350.skipthecart;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses( {
	                     PlaceOrderTest.class,
	                     ViewOrderTest.class,
	                     ShipOrderTest.class,
	                     AccountManagementTest.class,
	                     ShoppingCartManagementTest.class,
	                     SearchProductTest.class,
	                     InventoryManagementTest.class
                     } )
public class AllAcceptanceTests
{
}
