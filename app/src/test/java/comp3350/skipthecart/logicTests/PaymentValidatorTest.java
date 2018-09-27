package comp3350.skipthecart.logicTests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.PaymentException;
import comp3350.skipthecart.logic.PaymentValidator;
import comp3350.skipthecart.objects.CreditCard;

public class PaymentValidatorTest
{
	private static final PaymentValidator paymentValidator = new PaymentValidator();
	private static final String dummyUserID = "TEST";
	private CreditCard dummyCreditCard;

	@BeforeClass
	public static void setStubs()
	{
		Services.setStubs( true );
	}

	@Before
	public void setupForEach()
	{
		dummyCreditCard = new CreditCard( "test", "1234567812345678", "1111", 1, 3000 );
	}

	@Test( expected = PaymentException.class )
	public void testEmptyOwnerName()
	{
		dummyCreditCard.setOwnerName( "" );
		paymentValidator.validate( dummyUserID, dummyCreditCard );
	}

	@Test( expected = PaymentException.class )
	public void testShortCardNum()
	{
		dummyCreditCard.setCardNum( "1" );
		paymentValidator.validate( dummyUserID, dummyCreditCard );
	}

	@Test( expected = PaymentException.class )
	public void testShortVerificationCode()
	{
		dummyCreditCard.setVerificationCode( "1" );
		paymentValidator.validate( dummyUserID, dummyCreditCard );
	}

	@Test( expected = PaymentException.class )
	public void testExpired()
	{
		dummyCreditCard.setExpMonth( 1 );
		dummyCreditCard.setExpYear( 1999 );
		paymentValidator.validate( dummyUserID, dummyCreditCard );
	}

	@Test( expected = PaymentException.class )
	public void testNullArgs()
	{
		paymentValidator.validate( null, null );
	}
}
