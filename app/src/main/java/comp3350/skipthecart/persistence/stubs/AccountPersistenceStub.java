package comp3350.skipthecart.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.Account;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.persistence.AccountPersistence;
import comp3350.skipthecart.persistence.PaymentPersistence;

public class AccountPersistenceStub implements AccountPersistence
{
	private List< Account > accounts;

	public AccountPersistenceStub()
	{
		accounts = new ArrayList<>();

		accounts.add(
			new Employee( "Boss", "Mann", "53 Wilklevoss Carriage", "bmann@skipthecart.ca",
			              "204-555-1212", "test", "password", "123456", "manager" ) );

		accounts.add(
			new Customer( "Suzy", "Shopper", "55 Bridgwater Bay", "suzyruleztheroost@mymts.net",
			              "204-555-6536", "suzybaby93", "password" ) );

		accounts.add(
			new Customer( "Ramen", "Munsch", "123 University Crescent", "ummunschr@cc.umanitoba.ca",
			              "431-555-5555", "noodles", "password" ) );

		accounts.add(
			new Customer( "Yi", "Peng", "105 Shore Street", "pengy346@myumanitoba.ca",
			              "204-955-4275",
			              "yipeng", "password" ) );
	}

	@Override
	public boolean addAccount( Account currentAccount )
	{
		boolean result = false;

		if ( accounts != null && currentAccount != null && inAccounts( currentAccount ) < 0 )
		{
			accounts.add( currentAccount );
			result = true;
		}

		return result;
	}

	@Override
	public void deleteAccount( Account currentAccount )
	{
		int index;
		PaymentPersistence paymentPersistence;

		if ( accounts != null && currentAccount != null )
		{
			index = inAccounts( currentAccount );

			if ( index >= 0 )
			{
				paymentPersistence = Services.getPaymentPersistence();

				if ( paymentPersistence != null )
				{
					paymentPersistence.deleteCreditCard( accounts.get( index ).getUserID() );
				}

				accounts.remove( index );
			}
		}
	}

	@Override
	public Account getAccountFromID( String userID )
	{
		Account acc = null;
		Account result = null;

		if ( accounts != null && userID != null )
		{
			for ( int i = 0; i < accounts.size(); i++ )
			{
				acc = accounts.get( i );

				if ( acc != null && acc.getUserID() != null && userID.equals( acc.getUserID() ) )
				{
					result = acc;
					break;
				}
			}
		}

		return result;
	}

	private int inAccounts( Account test )
	{
		Account acc;
		String userID1;
		String userID2;
		int result = -1;
		boolean found = false;

		if ( accounts != null && test != null )
		{
			userID1 = test.getUserID();

			if ( userID1 != null )
			{
				for ( int i = 0; i < accounts.size() && !found; i++ )
				{
					acc = accounts.get( i );

					if ( acc != null )
					{
						userID2 = acc.getUserID();

						if ( userID2 != null && userID1.equals( userID2 ) )
						{
							result = i;
							found = true;
						}
					}
				}
			}
		}

		return result;
	}
}
