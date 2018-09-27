package comp3350.skipthecart.logicTests;

import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessPayment;
import comp3350.skipthecart.logic.PaymentException;
import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.objects.Customer;

import static junit.framework.Assert.assertEquals;

public class AccessPaymentTest
{
	private AccessPayment dummyAccessPayment = createDummyAccessPayment();
	private static Customer dummyAccount = createDummyAccount();
	private CreditCard dummyCard = createDummyCard();
	private CreditCard badDummyCard = createBadCard();

	@BeforeClass
	public static void setup()
	{
		AccessCustomer.setCurrentCustomer( dummyAccount );
		Services.setStubs( true );
	}

	@Test
	public void testConstructor()
	{
		assertEquals( Services.getPaymentPersistence(),
		              dummyAccessPayment.getPaymentPersistence() );
	}

	@Test
	public void testGetCreditCard()
	{
		assertEquals( null, dummyAccessPayment.getCreditCard( null ) );
		dummyAccessPayment.updateCreditCard( AccessCustomer.getCurrentCustomer().getUserID(),
		                                     dummyCard );
		assertEquals( dummyCard,
		              dummyAccessPayment.getCreditCard(
			              AccessCustomer.getCurrentCustomer().getUserID() ) );
	}

	@Test( expected = PaymentException.class )
	public void testExceptionThrown()
	{
		dummyAccessPayment.updateCreditCard( AccessCustomer.getCurrentCustomer().getUserID(),
		                                     badDummyCard );
	}

	@Test
	public void testDeleteCreditCard()
	{
		assertEquals( false, dummyAccessPayment.deleteCreditCard( null ) );
		dummyAccessPayment.deleteCreditCard( AccessCustomer.getCurrentCustomer().getUserID() );
		assertEquals( null,
		              dummyAccessPayment.getCreditCard(
			              AccessCustomer.getCurrentCustomer().getUserID() ) );
		dummyAccessPayment.updateCreditCard( AccessCustomer.getCurrentCustomer().getUserID(),
		                                     dummyCard );
		assertEquals( true, dummyAccessPayment.deleteCreditCard(
			AccessCustomer.getCurrentCustomer().getUserID() ) );
	}

	private AccessPayment createDummyAccessPayment()
	{
		return new AccessPayment();
	}

	private static Customer createDummyAccount()
	{
		return new Customer( "John", "Smith", "123 Main st.", "jsmith@email.com", "1234567", "1234",
		                     "password" );
	}

	private CreditCard createDummyCard()
	{
		return new CreditCard( "John Smith", "1234567812345678", "111", 1, 2020 );
	}

	private CreditCard createBadCard()
	{
		return new CreditCard( "", "1234567812345678", "111", 1, 2020 );
	}
}
