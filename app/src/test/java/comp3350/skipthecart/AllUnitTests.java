package comp3350.skipthecart;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.skipthecart.logicTests.AccessCustomerTest;
import comp3350.skipthecart.logicTests.AccessEmployeeTest;
import comp3350.skipthecart.logicTests.AccessOrdersTest;
import comp3350.skipthecart.logicTests.AccessPaymentTest;
import comp3350.skipthecart.logicTests.AccessProductsTest;
import comp3350.skipthecart.logicTests.CustomerValidatorTest;
import comp3350.skipthecart.logicTests.EmployeeValidatorTest;
import comp3350.skipthecart.logicTests.PaymentValidatorTest;
import comp3350.skipthecart.logicTests.ProductValidatorTest;
import comp3350.skipthecart.objectTests.CreditCardTest;
import comp3350.skipthecart.objectTests.CustomerAccountTest;
import comp3350.skipthecart.objectTests.EmployeeAccountTest;
import comp3350.skipthecart.objectTests.ProductTest;

@RunWith( Suite.class )
@Suite.SuiteClasses( {
	                     AccessEmployeeTest.class,
	                     AccessCustomerTest.class,
	                     AccessProductsTest.class,
	                     AccessOrdersTest.class,
	                     AccessPaymentTest.class,
	                     CreditCardTest.class,
	                     CustomerAccountTest.class,
	                     EmployeeAccountTest.class,
	                     ProductTest.class,
	                     CustomerValidatorTest.class,
	                     EmployeeValidatorTest.class,
	                     PaymentValidatorTest.class,
	                     ProductValidatorTest.class
                     } )

public class AllUnitTests
{
}
