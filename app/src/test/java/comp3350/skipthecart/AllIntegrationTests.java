package comp3350.skipthecart;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.skipthecart.integrationTests.AccessCustomerIT;
import comp3350.skipthecart.integrationTests.AccessEmployeeIT;
import comp3350.skipthecart.integrationTests.AccessOrdersIT;
import comp3350.skipthecart.integrationTests.AccessPaymentIT;
import comp3350.skipthecart.integrationTests.AccessProductsIT;

@RunWith( Suite.class )
@Suite.SuiteClasses( {
	                     AccessOrdersIT.class,
	                     AccessCustomerIT.class,
	                     AccessEmployeeIT.class,
	                     AccessPaymentIT.class,
	                     AccessProductsIT.class
                     } )
public class AllIntegrationTests
{
}
