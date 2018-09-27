package comp3350.skipthecart.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.persistence.PaymentPersistence;

public class PaymentPersistenceStub implements PaymentPersistence
{
	private List< Payment > paymentInfo;

	public PaymentPersistenceStub()
	{
		paymentInfo = new ArrayList<>();

		updateCreditCard( "suzybaby93",
		                  new CreditCard( "Suzy Shopper", "4500030589456645", "412", 12, 2019 ) );

		updateCreditCard( "noodles",
		                  new CreditCard( "Ramen K Munsch", "6512238945611234", "585", 8, 2020 ) );
	}

	@Override
	public String getUserID( String cardNum )
	{
		String userID = null;
		Payment custPaymentInfo = null;
		CreditCard currCard = null;
		boolean found = false;

		if ( paymentInfo != null && !paymentInfo.isEmpty() && cardNum != null )
		{
			for ( int i = 0; i < paymentInfo.size() && !found; i++ )
			{
				custPaymentInfo = paymentInfo.get( i );
				currCard = custPaymentInfo.getPaymentMethod();

				if ( cardNum.equals( currCard.getCardNum() ) )
				{
					userID = custPaymentInfo.getUserID();
					found = true;
				}
			}
		}

		return userID;
	}

	@Override
	public CreditCard getCreditCard( String userID )
	{
		Payment custPaymentInfo = null;
		CreditCard currCard = null;
		boolean found = false;

		if ( paymentInfo != null && !paymentInfo.isEmpty() && userID != null )
		{
			for ( int i = 0; i < paymentInfo.size() && !found; i++ )
			{
				custPaymentInfo = paymentInfo.get( i );

				if ( userID.equals( custPaymentInfo.getUserID() ) )
				{
					currCard = custPaymentInfo.getPaymentMethod();
					found = true;
				}
			}
		}

		return currCard;
	}

	@Override
	public boolean updateCreditCard( String userID,
	                                 CreditCard cc )
	{
		boolean updated = false;
		Payment custPaymentInfo = null;

		if ( paymentInfo != null && userID != null && cc != null )
		{
			for ( int i = 0; i < paymentInfo.size() && !updated; i++ )
			{
				custPaymentInfo = paymentInfo.get( i );

				if ( userID.equals( custPaymentInfo.getUserID() ) )
				{
					custPaymentInfo.setPaymentMethod( cc );
					updated = true;
				}
			}

			if ( !updated )
			{
				custPaymentInfo = new Payment( userID, cc );
				updated = paymentInfo.add( custPaymentInfo );
			}
		}

		return updated;
	}

	@Override
	public boolean deleteCreditCard( String userID )
	{
		Payment custPaymentInfo = null;
		boolean deleted = false;

		if ( paymentInfo != null && !paymentInfo.isEmpty() && userID != null )
		{
			for ( int i = 0; i < paymentInfo.size() && !deleted; i++ )
			{
				custPaymentInfo = paymentInfo.get( i );

				if ( userID.equals( custPaymentInfo.getUserID() ) )
				{
					paymentInfo.remove( i );
					deleted = true;
				}
			}
		}

		return deleted;
	}

	private class Payment
	{
		private String userID;
		private CreditCard paymentMethod;

		Payment( String userID,
		         CreditCard paymentMethod )
		{
			this.userID = userID;
			this.paymentMethod = paymentMethod;
		}

		String getUserID()
		{
			return userID;
		}

		CreditCard getPaymentMethod()
		{
			return paymentMethod;
		}

		void setPaymentMethod( CreditCard paymentMethod )
		{
			this.paymentMethod = paymentMethod;
		}
	}
}
