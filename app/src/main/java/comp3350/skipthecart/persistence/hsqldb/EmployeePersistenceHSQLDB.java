package comp3350.skipthecart.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.persistence.EmployeePersistence;

public class EmployeePersistenceHSQLDB implements EmployeePersistence
{
	private final Connection c;

	public EmployeePersistenceHSQLDB( final String dbPath )
	{
		try
		{
			c = DriverManager.getConnection( "jdbc:hsqldb:file:" + dbPath, "SA", "" );
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}
	}

	@Override
	public boolean addEmployee( Employee employee )
	{
		PreparedStatement st;
		boolean result = false;

		try
		{
			if ( employee != null )
			{
				st = c.prepareStatement(
					"INSERT INTO EMPLOYEES VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)" );

				st.setString( 1, employee.getFirstName() );
				st.setString( 2, employee.getLastName() );
				st.setString( 3, employee.getAddress() );
				st.setString( 4, employee.getEmail() );
				st.setString( 5, employee.getPhoneNum() );
				st.setString( 6, employee.getUserID() );
				st.setString( 7, employee.getPassword() );
				st.setString( 8, employee.getEmployeeNum() );
				st.setString( 9, employee.getPosition() );

				result = st.executeUpdate() > 0;
				st.close();
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return result;
	}

	@Override
	public boolean deleteEmployee( Employee employee )
	{
		PreparedStatement st;
		boolean result = false;

		try
		{
			if ( employee != null )
			{
				st = c.prepareStatement( "DELETE FROM EMPLOYEES WHERE USERID = ?" );
				st.setString( 1, employee.getUserID() );
				result = st.executeUpdate() > 0;
				st.close();
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return result;
	}

	@Override
	public Employee getEmployeeFromID( String userID )
	{
		PreparedStatement st;
		ResultSet rs;
		Employee result = null;

		try
		{
			if ( userID != null && !userID.isEmpty() )
			{
				st = c.prepareStatement( "SELECT * FROM EMPLOYEES WHERE USERID = ?" );
				st.setString( 1, userID );
				rs = st.executeQuery();

				if ( rs.next() )
				{
					result = new Employee( rs.getString( "FIRSTNAME" ), rs.getString( "LASTNAME" ),
					                       rs.getString( "ADDRESS" ), rs.getString( "EMAIL" ),
					                       rs.getString( "PHONENUM" ), rs.getString( "USERID" ),
					                       rs.getString( "PASSWORD" ),
					                       rs.getString( "EMPLOYEENUM" ),
					                       rs.getString( "POSITION" ) );
				}

				st.close();
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return result;
	}
}
