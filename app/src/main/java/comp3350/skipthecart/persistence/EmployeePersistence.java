package comp3350.skipthecart.persistence;

import comp3350.skipthecart.objects.Employee;

public interface EmployeePersistence
{
	boolean addEmployee( Employee employee );

	boolean deleteEmployee( Employee employee );

	Employee getEmployeeFromID( String userID );
}
