package comp3350.skipthecart.logic;

import comp3350.skipthecart.objects.Customer;

public class CustomerValidator
{
	private static final int MAX_NAME = 20;
	private static final int MAX_ADDRESS = 40;
	private static final int MAX_EMAIL = 40;
	private static final int MAX_PHONE_NUM = 12;
	private static final int MAX_USER_ID = 20;
	private static final int MAX_PASSWORD = 20;

	public void validate( Customer customer ) throws CustomerException
	{
		if ( customer != null )
		{
			if ( customer.getFirstName() == null || customer.getFirstName().isEmpty() ||
			     customer.getFirstName().length() > MAX_NAME )
			{
				throw new CustomerException( "First name is empty or too long" );
			}
			else if ( customer.getLastName() == null || customer.getLastName().isEmpty() ||
			          customer.getLastName().length() > MAX_NAME )
			{
				throw new CustomerException( "Last name is empty or too long" );
			}
			else if ( customer.getAddress() == null || customer.getAddress().isEmpty() ||
			          customer.getAddress().length() > MAX_ADDRESS )
			{
				throw new CustomerException( "Address is empty or too long" );
			}
			else if ( customer.getEmail() == null || customer.getEmail().isEmpty() ||
			          customer.getEmail().length() > MAX_EMAIL )
			{
				throw new CustomerException( "Email is empty or too long" );
			}
			else if ( customer.getPhoneNum() == null || customer.getPhoneNum().isEmpty() ||
			          customer.getPhoneNum().length() > MAX_PHONE_NUM )
			{
				throw new CustomerException( "Phone number is empty or too long" );
			}
			else if ( customer.getUserID() == null || customer.getUserID().isEmpty() ||
			          customer.getUserID().length() > MAX_USER_ID )
			{
				throw new CustomerException( "User ID  is empty or too long" );
			}
			else if ( customer.getPassword() == null || customer.getPassword().isEmpty() ||
			          customer.getPassword().length() > MAX_PASSWORD )
			{
				throw new CustomerException( "Password is empty or too long" );
			}
		}
		else
		{
			throw new CustomerException( "No customer to process" );
		}
	}
}
