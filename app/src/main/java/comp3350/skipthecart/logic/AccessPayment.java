package comp3350.skipthecart.logic;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.persistence.PaymentPersistence;

public class AccessPayment
{
	private PaymentPersistence paymentPersistence;
	private PaymentValidator paymentValidator;

	public AccessPayment()
	{
		paymentPersistence = Services.getPaymentPersistence();
		paymentValidator = new PaymentValidator();
	}

	public CreditCard getCreditCard( String userID )
	{
		CreditCard cc = null;

		if ( userID != null )
		{
			cc = paymentPersistence.getCreditCard( userID );
		}

		return cc;
	}

	public void updateCreditCard( String userID,
	                              CreditCard cc ) throws PaymentException
	{
		paymentValidator.validate( userID, cc );

		if ( !paymentPersistence.updateCreditCard( userID, cc ) )
		{
			throw new PaymentException( "Could not update Credit Card information" );
		}
	}

	public boolean deleteCreditCard( String userID )
	{
		boolean result = false;

		if ( userID != null )
		{
			result = paymentPersistence.deleteCreditCard( userID );
		}

		return result;
	}

	public PaymentPersistence getPaymentPersistence()
	{
		return paymentPersistence;
	}
}
