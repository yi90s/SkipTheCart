package comp3350.skipthecart.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.persistence.CustomerPersistence;
import comp3350.skipthecart.persistence.PaymentPersistence;

public class CustomerPersistenceStub implements CustomerPersistence
{
	private List< Customer > customers;

	public CustomerPersistenceStub()
	{
		customers = new ArrayList<>();

		customers.add(
			new Customer( "Suzy", "Shopper", "55 Bridgewater Bay", "suzyruleztheroost@mymts.net",
			              "204-555-6536", "suzybaby93", "password" ) );

		customers.add(
			new Customer( "Ramen", "Munsch", "123 University Crescent", "ummunschr@cc.umanitoba.ca",
			              "431-555-5555", "noodles", "password" ) );

		customers.add(
			new Customer( "Yi", "Peng", "105 Shore Street", "pengy346@myumanitoba.ca",
			              "204-955-4275", "yipeng", "password" ) );
	}

	@Override
	public boolean addCustomer( Customer customer )
	{
		boolean result = false;

		if ( customers != null && customer != null && inCustomers( customer ) < 0 )
		{
			result = customers.add( customer );
		}

		return result;
	}

	@Override
	public boolean deleteCustomer( Customer customer )
	{
		int index;
		PaymentPersistence paymentPersistence;
		boolean result = false;

		if ( customers != null && customer != null )
		{
			index = inCustomers( customer );

			if ( index >= 0 )
			{
				paymentPersistence = Services.getPaymentPersistence();

				if ( paymentPersistence != null )
				{
					paymentPersistence.deleteCreditCard( customers.get( index ).getUserID() );
				}

				result = customers.remove( index ) != null;
			}
		}

		return result;
	}

	@Override
	public Customer getCustomerFromID( String userID )
	{
		Customer curr;
		Customer result = null;
		boolean found = false;

		if ( customers != null && userID != null && !userID.isEmpty() )
		{
			for ( int i = 0; i < customers.size() && !found; i++ )
			{
				curr = customers.get( i );

				if ( curr != null && curr.getUserID() != null && userID.equals( curr.getUserID() ) )
				{
					result = curr;
					found = true;
				}
			}
		}

		return result;
	}

	private int inCustomers( Customer compare )
	{
		Customer curr;
		String userID1;
		String userID2;
		int result = -1;
		boolean found = false;

		if ( customers != null && compare != null )
		{
			userID1 = compare.getUserID();

			if ( userID1 != null )
			{
				for ( int i = 0; i < customers.size() && !found; i++ )
				{
					curr = customers.get( i );

					if ( curr != null )
					{
						userID2 = curr.getUserID();

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
