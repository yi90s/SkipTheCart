package comp3350.skipthecart.persistence;

import comp3350.skipthecart.objects.CreditCard;

public interface PaymentPersistence
{
	String getUserID( String cardNum );

	CreditCard getCreditCard( String userID );

	boolean updateCreditCard( String userID,
	                          CreditCard cc );

	boolean deleteCreditCard( String userID );
}
