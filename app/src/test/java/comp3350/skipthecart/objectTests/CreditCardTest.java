package comp3350.skipthecart.objectTests;

import org.junit.Before;
import org.junit.Test;

import comp3350.skipthecart.objects.CreditCard;

import static junit.framework.Assert.assertEquals;

public class CreditCardTest
{
	private CreditCard dummyCard;

	@Before
	public void setup()
	{
		dummyCard = createDummyCard();
	}

	@Test
	public void testOwnerName()
	{
		assertEquals( "John Smith", dummyCard.getOwnerName() );
		dummyCard.setOwnerName( "Best Name" );
		assertEquals( "Best Name", dummyCard.getOwnerName() );
	}

	@Test
	public void testCardNumber()
	{
		assertEquals( "1234567812345678", dummyCard.getCardNum() );
		dummyCard.setCardNum( "0000000000000000" );
		assertEquals( "0000000000000000", dummyCard.getCardNum() );
	}

	@Test
	public void testVerificationCode()
	{
		assertEquals( "111", dummyCard.getVerificationCode() );
		dummyCard.setVerificationCode( "999" );
		assertEquals( "999", dummyCard.getVerificationCode() );
	}

	@Test
	public void testExpMonth()
	{
		assertEquals( 1, dummyCard.getExpMonth() );
		dummyCard.setExpMonth( 0 );
		assertEquals( 0, dummyCard.getExpMonth() );
	}

	@Test
	public void testExpYear()
	{
		assertEquals( 2001, dummyCard.getExpYear() );
		dummyCard.setExpYear( 2000 );
		assertEquals( 2000, dummyCard.getExpYear() );
	}

	private CreditCard createDummyCard()
	{
		return new CreditCard( "John Smith", "1234567812345678", "111", 1, 2001 );
	}
}
