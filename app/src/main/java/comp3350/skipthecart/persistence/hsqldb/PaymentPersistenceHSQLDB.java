package comp3350.skipthecart.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.skipthecart.objects.CreditCard;
import comp3350.skipthecart.persistence.PaymentPersistence;

public class PaymentPersistenceHSQLDB implements PaymentPersistence
{
	private final Connection c;

	public PaymentPersistenceHSQLDB( final String dbPath )
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
	public String getUserID( String cardNum )
	{
		PreparedStatement st;
		ResultSet rs;
		String result = null;

		try
		{
			if ( cardNum != null )
			{
				st = c.prepareStatement( "SELECT * FROM PAYMENT WHERE CARDNUM = ?" );
				st.setString( 1, cardNum );
				rs = st.executeQuery();

				if ( rs.next() )
				{
					result = rs.getString( "USERID" );
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

	@Override
	public CreditCard getCreditCard( String userID )
	{
		PreparedStatement st;
		ResultSet rs;
		CreditCard result = null;

		try
		{
			if ( userID != null )
			{
				st = c.prepareStatement( "SELECT * FROM PAYMENT WHERE USERID = ?" );
				st.setString( 1, userID );
				rs = st.executeQuery();

				if ( rs.next() )
				{
					result = new CreditCard( rs.getString( "OWNERNAME" ),
					                         rs.getString( "CARDNUM" ),
					                         rs.getString( "VCODE" ),
					                         rs.getInt( "EXPMONTH" ),
					                         rs.getInt( "EXPYEAR" ) );
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

	@Override
	public boolean updateCreditCard( String userID,
	                                 CreditCard cc )
	{
		PreparedStatement st;
		boolean result = false;

		try
		{
			if ( userID != null && cc != null )
			{
				deleteCreditCard( userID );

				st = c.prepareStatement( "INSERT INTO PAYMENT VALUES(?,?,?,?,?,?)" );
				st.setString( 1, userID );
				st.setString( 2, cc.getOwnerName() );
				st.setString( 3, cc.getCardNum() );
				st.setString( 4, cc.getVerificationCode() );
				st.setInt( 5, cc.getExpMonth() );
				st.setInt( 6, cc.getExpYear() );

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
	public boolean deleteCreditCard( String userID )
	{
		PreparedStatement st;
		boolean result = false;

		try
		{
			if ( userID != null )
			{
				st = c.prepareStatement( "DELETE FROM PAYMENT WHERE USERID = ?" );
				st.setString( 1, userID );

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
}
