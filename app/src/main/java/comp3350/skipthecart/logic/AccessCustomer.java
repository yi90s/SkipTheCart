package comp3350.skipthecart.logic;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.persistence.CustomerPersistence;
import comp3350.skipthecart.persistence.PaymentPersistence;

public class AccessCustomer
{
	private static Customer currentCustomer = null;
	private CustomerValidator customerValidator;
	private CustomerPersistence customerPersistence;
	private PaymentPersistence paymentPersistence;

	public AccessCustomer()
	{
		customerPersistence = Services.getCustomerPersistence();
		paymentPersistence = Services.getPaymentPersistence();
		customerValidator = new CustomerValidator();
	}

	public AccessCustomer( final CustomerPersistence customerPersistence,
	                       final PaymentPersistence paymentPersistence,
	                       final CustomerValidator customerValidator )
	{
		this();
		this.customerPersistence = customerPersistence;
		this.paymentPersistence = paymentPersistence;
		this.customerValidator = customerValidator;
	}

	public static Customer getCurrentCustomer()
	{
		return currentCustomer;
	}

	public static void setCurrentCustomer( Customer customer )
	{
		currentCustomer = customer;
	}

	public Customer getCustomer( final String userID,
	                             final String password )
	{
		Customer found;
		Customer result = null;

		if ( customerPersistence != null && userID != null && !userID.isEmpty() &&
		     password != null && !password.isEmpty() )
		{
			found = customerPersistence.getCustomerFromID( userID );

			if ( found != null && found.getPassword() != null &&
			     password.equals( found.getPassword() ) )
			{
				result = found;
			}
		}

		return result;
	}

	public Customer getCustomerById( final String userID )
	{
		Customer result = null;

		if ( customerPersistence != null && userID != null && !userID.isEmpty() )
		{
			result = customerPersistence.getCustomerFromID( userID );
		}

		return result;
	}

	public boolean addCustomer( Customer customer ) throws CustomerException
	{
		boolean result = false;

		if ( customerPersistence != null && customer != null )
		{
			if ( customerPersistence.getCustomerFromID( customer.getUserID() ) == null )
			{
				customerValidator.validate( customer );
				result = customerPersistence.addCustomer( customer );
			}
			else
			{
				throw new CustomerException( "User ID already exists" );
			}
		}

		return result;
	}

	public boolean updateCustomerInfo( Customer old,
	                                   Customer updated ) throws CustomerException
	{
		boolean successful;
		CreditCard cc;

		customerValidator.validate( updated );

		if ( old.getPaymentMethod() != null )
		{
			cc = old.getPaymentMethod();
		}
		else
		{
			cc = paymentPersistence.getCreditCard( old.getUserID() );
		}

		successful = deleteCustomer( old ) && addCustomer( updated );

		if ( successful )
		{
			if ( cc != null )
			{
				paymentPersistence.updateCreditCard( updated.getUserID(), cc );
				updated.setPaymentMethod( cc );
			}
		}

		return successful;
	}

	private boolean deleteCustomer( Customer customer )
	{
		boolean result = false;

		if ( customerPersistence != null && customer != null )
		{
			result = customerPersistence.deleteCustomer( customer );
		}

		return result;
	}
}
