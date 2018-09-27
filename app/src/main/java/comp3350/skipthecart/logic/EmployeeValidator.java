package comp3350.skipthecart.logic;

import comp3350.skipthecart.objects.Employee;

public class EmployeeValidator
{
	private static final int MAX_NAME = 20;
	private static final int MAX_ADDRESS = 40;
	private static final int MAX_EMAIL = 40;
	private static final int MAX_PHONE_NUM = 12;
	private static final int MAX_USER_ID = 20;
	private static final int MAX_PASSWORD = 20;
	private static final int MAX_EMPLOYEE_NUM = 20;
	private static final int MAX_POSITION = 40;

	public void validate( Employee employee ) throws EmployeeException
	{
		if ( employee != null )
		{
			if ( employee.getFirstName() == null || employee.getFirstName().isEmpty() ||
			     employee.getFirstName().length() > MAX_NAME )
			{
				throw new EmployeeException( "First name is empty or too long" );
			}
			else if ( employee.getLastName() == null || employee.getLastName().isEmpty() ||
			          employee.getLastName().length() > MAX_NAME )
			{
				throw new EmployeeException( "Last name is empty or too long" );
			}
			else if ( employee.getAddress() == null || employee.getAddress().isEmpty() ||
			          employee.getAddress().length() > MAX_ADDRESS )
			{
				throw new EmployeeException( "Address is empty or too long" );
			}
			else if ( employee.getEmail() == null || employee.getEmail().isEmpty() ||
			          employee.getEmail().length() > MAX_EMAIL )
			{
				throw new EmployeeException( "Email is empty or too long" );
			}
			else if ( employee.getPhoneNum() == null || employee.getPhoneNum().isEmpty() ||
			          employee.getPhoneNum().length() > MAX_PHONE_NUM )
			{
				throw new EmployeeException( "Phone number is empty or too long" );
			}
			else if ( employee.getUserID() == null || employee.getUserID().isEmpty() ||
			          employee.getUserID().length() > MAX_USER_ID )
			{
				throw new EmployeeException( "User ID  is empty or too long" );
			}
			else if ( employee.getPassword() == null || employee.getPassword().isEmpty() ||
			          employee.getPassword().length() > MAX_PASSWORD )
			{
				throw new EmployeeException( "Password is empty or too long" );
			}
			else if ( employee.getEmployeeNum() == null || employee.getEmployeeNum().isEmpty() ||
			          employee.getEmployeeNum().length() > MAX_EMPLOYEE_NUM )
			{
				throw new EmployeeException( "Employee number is empty or too long" );
			}
			else if ( employee.getPosition() == null || employee.getPosition().isEmpty() ||
			          employee.getPosition().length() > MAX_POSITION )
			{
				throw new EmployeeException( "Position is empty or too long" );
			}
		}
		else
		{
			throw new EmployeeException( "No employee to process" );
		}
	}
}
