package comp3350.skipthecart.logic;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.persistence.EmployeePersistence;

public class AccessEmployee
{
	private static Employee currentEmployee = null;
	private EmployeePersistence employeePersistence;
	private EmployeeValidator employeeValidator;

	public AccessEmployee()
	{
		employeePersistence = Services.getEmployeePersistence();
		employeeValidator = new EmployeeValidator();
	}

	public AccessEmployee( final EmployeePersistence employeePersistence,
	                       final EmployeeValidator employeeValidator )
	{
		this();
		this.employeePersistence = employeePersistence;
		this.employeeValidator = employeeValidator;
	}

	public static Employee getCurrentEmployee()
	{
		return currentEmployee;
	}

	public static void setCurrentEmployee( Employee employee )
	{
		currentEmployee = employee;
	}

	public Employee getEmployee( final String userID,
	                             final String password )
	{
		Employee found;
		Employee result = null;

		if ( employeePersistence != null && userID != null && !userID.isEmpty() &&
		     password != null && !password.isEmpty() )
		{
			found = employeePersistence.getEmployeeFromID( userID );

			if ( found != null && found.getPassword() != null &&
			     password.equals( found.getPassword() ) )
			{
				result = found;
			}
		}

		return result;
	}

	public boolean addEmployee( Employee employee ) throws EmployeeException
	{
		boolean result = false;

		if ( employeePersistence != null && employee != null )
		{
			if ( employeePersistence.getEmployeeFromID( employee.getUserID() ) == null )
			{
				employeeValidator.validate( employee );
				result = employeePersistence.addEmployee( employee );
			}
			else
			{
				throw new EmployeeException( "User ID already exists" );
			}
		}

		return result;
	}

	public boolean updateEmployeeInfo( Employee old,
	                                   Employee updated ) throws EmployeeException
	{
		employeeValidator.validate( updated );

		return deleteEmployee( old ) && addEmployee( updated );
	}

	private boolean deleteEmployee( Employee employee )
	{
		boolean result = false;

		if ( employeePersistence != null && employee != null )
		{
			result = employeePersistence.deleteEmployee( employee );
		}

		return result;
	}
}
