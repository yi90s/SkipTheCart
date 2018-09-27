package comp3350.skipthecart.persistence;

import comp3350.skipthecart.objects.Customer;

public interface CustomerPersistence
{
	boolean addCustomer( Customer customer );

	boolean deleteCustomer( Customer customer );

	Customer getCustomerFromID( String userID );
}
