package comp3350.skipthecart.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.persistence.CustomerPersistence;

public class CustomerPersistenceHSQLDB implements CustomerPersistence
{
	private final Connection c;

	public CustomerPersistenceHSQLDB( final String dbPath )
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
	public boolean addCustomer( Customer customer )
	{
		PreparedStatement st;
		boolean result = false;

		try
		{
			if ( customer != null )
			{
				st = c.prepareStatement( "INSERT INTO CUSTOMERS VALUES(?, ?, ?, ?, ?, ?, ?)" );

				st.setString( 1, customer.getFirstName() );
				st.setString( 2, customer.getLastName() );
				st.setString( 3, customer.getAddress() );
				st.setString( 4, customer.getEmail() );
				st.setString( 5, customer.getPhoneNum() );
				st.setString( 6, customer.getUserID() );
				st.setString( 7, customer.getPassword() );

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
	public boolean deleteCustomer( Customer customer )
	{
		PreparedStatement st;
		boolean result = false;

		try
		{
			if ( customer != null )
			{
				st = c.prepareStatement( "DELETE FROM CUSTOMERS WHERE USERID = ?" );
				st.setString( 1, customer.getUserID() );
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
	public Customer getCustomerFromID( String userID )
	{
		PreparedStatement st;
		ResultSet rs;
		Customer result = null;

		try
		{
			if ( userID != null && !userID.isEmpty() )
			{
				st = c.prepareStatement( "SELECT * FROM CUSTOMERS WHERE USERID = ?" );
				st.setString( 1, userID );
				rs = st.executeQuery();

				if ( rs.next() )
				{
					result = new Customer( rs.getString( "FIRSTNAME" ), rs.getString( "LASTNAME" ),
					                       rs.getString( "ADDRESS" ), rs.getString( "EMAIL" ),
					                       rs.getString( "PHONENUM" ), rs.getString( "USERID" ),
					                       rs.getString( "PASSWORD" ) );
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
