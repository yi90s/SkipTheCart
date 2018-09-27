package comp3350.skipthecart.logic;

import java.util.Calendar;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.persistence.PaymentPersistence;

public class PaymentValidator
{
	private static final int MIN_CARD_NUM_LENGTH = 16;
	private static final int MIN_VCODE_LENGTH = 3;

	public void validate( String userID,
	                      CreditCard cc ) throws PaymentException
	{
		PaymentPersistence paymentPersistence;
		String checkUserID;
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get( Calendar.YEAR );
		int month = calendar.get( Calendar.MONTH ) + 1;

		if ( userID != null && cc != null )
		{
			if ( cc.getOwnerName() == null || cc.getOwnerName().isEmpty() )
			{
				throw new PaymentException( "No Card Owner entered" );
			}
			else if ( cc.getCardNum() == null ||
			          cc.getCardNum().length() < MIN_CARD_NUM_LENGTH )
			{
				throw new PaymentException( "Card Number is too short" );
			}
			else if ( cc.getVerificationCode() == null ||
			          cc.getVerificationCode().length() < MIN_VCODE_LENGTH )
			{
				throw new PaymentException( "Verification Code is too short" );
			}
			else if ( cc.getExpMonth() < month && cc.getExpYear() <= year )
			{
				throw new PaymentException( "Credit Card is expired" );
			}
			else
			{
				paymentPersistence = Services.getPaymentPersistence();
				checkUserID = paymentPersistence.getUserID( cc.getCardNum() );

				if ( checkUserID != null && !checkUserID.equals( userID ) )
				{
					throw new PaymentException( "Credit Card is used by another account" );
				}
			}
		}
		else
		{
			throw new PaymentException( "No User ID or Credit Card was entered" );
		}
	}
}
