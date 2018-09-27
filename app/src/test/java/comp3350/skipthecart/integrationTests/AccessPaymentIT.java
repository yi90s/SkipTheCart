package comp3350.skipthecart.integrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.skipthecart.logic.AccessCustomer;
import comp3350.skipthecart.logic.AccessPayment;
import comp3350.skipthecart.logic.PaymentException;
import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.utils.TestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AccessPaymentIT
{
	private File tempDB;
	private AccessPayment dummyAccessPayment;
	AccessCustomer accessCustomer;
	private Customer dummyAccount;
	private CreditCard dummyCard;
	private CreditCard badCard;

	@Before
	public void setUp() throws IOException
	{
		tempDB = TestUtils.copyDB();
		accessCustomer = new AccessCustomer();
		dummyAccount = createDummyAccount();
		accessCustomer.addCustomer( dummyAccount );
		AccessCustomer.setCurrentCustomer( dummyAccount );
		dummyAccessPayment = createDummyAccessPayment();
		dummyCard = createDummyCard();
		badCard = createBadCard();
	}

	@Test( expected = PaymentException.class )
	public void testAccessPaymentPersistence()
	{
		CreditCard cc;

		dummyAccessPayment.updateCreditCard( AccessCustomer.getCurrentCustomer().getUserID(),
		                                     dummyCard );
		cc = dummyAccessPayment.getCreditCard( AccessCustomer.getCurrentCustomer().getUserID() );

		assertNotNull( cc );

		assertTrue( cc.getOwnerName().equals( dummyCard.getOwnerName() ) );
		assertTrue( cc.getCardNum().equals( dummyCard.getCardNum() ) );
		assertTrue( cc.getExpMonth() == dummyCard.getExpMonth() );
		assertTrue( cc.getExpYear() == dummyCard.getExpYear() );
		assertTrue( cc.getOwnerName().equals( dummyCard.getOwnerName() ) );

		dummyAccessPayment.deleteCreditCard( AccessCustomer.getCurrentCustomer().getUserID() );

		assertNull(
			dummyAccessPayment.getCreditCard( AccessCustomer.getCurrentCustomer().getUserID() ) );

		dummyAccessPayment.updateCreditCard( AccessCustomer.getCurrentCustomer().getUserID(),
		                                     badCard );
	}

	@After
	public void tearDown()
	{
		tempDB.delete();
	}

	private AccessPayment createDummyAccessPayment()
	{
		return new AccessPayment();
	}

	private Customer createDummyAccount()
	{
		return new Customer( "John", "Smith", "123 Main st.", "jsmith@email.com", "1234567",
		                     "johns", "password" );
	}

	private CreditCard createDummyCard()
	{
		return new CreditCard( "John Smith", "8888888888888888", "333", 1, 2020 );
	}

	private CreditCard createBadCard()
	{
		return new CreditCard( "", "1234567812345678", "111", 1, 2020 );
	}
}
