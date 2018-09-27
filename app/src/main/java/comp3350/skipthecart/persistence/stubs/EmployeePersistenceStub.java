package comp3350.skipthecart.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.persistence.EmployeePersistence;

public class EmployeePersistenceStub implements EmployeePersistence
{
	private List< Employee > employees;

	public EmployeePersistenceStub()
	{
		employees = new ArrayList<>();

		employees.add(
			new Employee( "Boss", "Mann", "53 Wilklevoss Carriage", "bmann@skipthecart.ca",
			              "204-555-1212", "test", "password", "123456", "Manager" ) );

		employees.add(
			new Employee( "Lowley", "Peon", "433 Furby Street", "lpeon@skipthecart.ca",
			              "431-555-4898", "lpeon", "password", "654321", "Delivery Driver" ) );
	}

	@Override
	public boolean addEmployee( Employee employee )
	{
		boolean result = false;

		if ( employees != null && employee != null && inEmployees( employee ) < 0 )
		{
			result = employees.add( employee );
		}

		return result;
	}

	@Override
	public boolean deleteEmployee( Employee employee )
	{
		int index;
		boolean result = false;

		if ( employees != null && employee != null )
		{
			index = inEmployees( employee );

			if ( index >= 0 )
			{
				result = employees.remove( index ) != null;
			}
		}

		return result;
	}

	@Override
	public Employee getEmployeeFromID( String userID )
	{
		Employee curr;
		Employee result = null;
		boolean found = false;

		if ( employees != null && userID != null && !userID.isEmpty() )
		{
			for ( int i = 0; i < employees.size() && !found; i++ )
			{
				curr = employees.get( i );

				if ( curr != null && curr.getUserID() != null && userID.equals( curr.getUserID() ) )
				{
					result = curr;
					found = true;
				}
			}
		}

		return result;
	}

	private int inEmployees( Employee compare )
	{
		Employee curr;
		String userID1;
		String userID2;
		int result = -1;
		boolean found = false;

		if ( employees != null && compare != null )
		{
			userID1 = compare.getUserID();

			if ( userID1 != null )
			{
				for ( int i = 0; i < employees.size() && !found; i++ )
				{
					curr = employees.get( i );

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
