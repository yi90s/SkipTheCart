package comp3350.skipthecart.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.skipthecart.objects.Account;
import comp3350.skipthecart.objects.Customer;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.persistence.AccountPersistence;

public class AccountPersistenceHSQLDB implements AccountPersistence
{
	private final Connection c;

	public AccountPersistenceHSQLDB( final String dbPath )
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
	public boolean addAccount( Account currentAccount )
	{
		PreparedStatement st = null;
		boolean result = false;

		try
		{
			if ( currentAccount instanceof Customer )
			{
				st = c.prepareStatement( "INSERT INTO CUSTOMERS VALUES(?, ?, ?, ?, ?, ?, ?)" );
			}
			else if ( currentAccount instanceof Employee )
			{
				st = c.prepareStatement(
					"INSERT INTO EMPLOYEES VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)" );

				st.setString( 8, ( ( Employee )currentAccount ).getEmployeeNum() );
				st.setString( 9, ( ( Employee )currentAccount ).getPosition() );
			}

			if ( st != null )
			{
				st.setString( 1, currentAccount.getFirstName() );
				st.setString( 2, currentAccount.getLastName() );
				st.setString( 3, currentAccount.getAddress() );
				st.setString( 4, currentAccount.getEmail() );
				st.setString( 5, currentAccount.getPhoneNum() );
				st.setString( 6, currentAccount.getUserID() );
				st.setString( 7, currentAccount.getPassword() );

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
	public void deleteAccount( Account currentAccount )
	{
		PreparedStatement st;
		String delSt = null;

		try
		{
			if ( currentAccount instanceof Customer )
			{
				delSt = "DELETE FROM CUSTOMERS WHERE USERID = ?";
			}
			else if ( currentAccount instanceof Employee )
			{
				delSt = "DELETE FROM EMPLOYEES WHERE USERID = ?";
			}

			if ( delSt != null )
			{
				st = c.prepareStatement( delSt );
				st.setString( 1, currentAccount.getUserID() );
				st.executeUpdate();
				st.close();
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}
	}

	@Override
	public Account getAccountFromID( String userID )
	{
		PreparedStatement st;
		ResultSet rs;
		Account result = null;

		try
		{
			if ( userID != null )
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

					st.close();
				}
				else
				{
					st.close();

					st = c.prepareStatement( "SELECT * FROM CUSTOMERS WHERE USERID = ?" );
					st.setString( 1, userID );
					rs = st.executeQuery();

					if ( rs.next() )
					{
						result = new Customer( rs.getString( "FIRSTNAME" ),
						                       rs.getString( "LASTNAME" ),
						                       rs.getString( "ADDRESS" ), rs.getString( "EMAIL" ),
						                       rs.getString( "PHONENUM" ), rs.getString( "USERID" ),
						                       rs.getString( "PASSWORD" ) );
					}

					st.close();
				}
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return result;
	}
}
