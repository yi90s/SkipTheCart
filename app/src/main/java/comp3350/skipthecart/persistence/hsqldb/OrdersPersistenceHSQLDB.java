package comp3350.skipthecart.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.OrderItem;
import comp3350.skipthecart.objects.Orders;
import comp3350.skipthecart.persistence.OrdersPersistence;
import comp3350.skipthecart.persistence.ProductPersistence;

public class OrdersPersistenceHSQLDB implements OrdersPersistence
{
	private final Connection c;

	public OrdersPersistenceHSQLDB( final String dbPath )
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
	public List< Orders > getOrdersFromUserId( String userId )
	{
		PreparedStatement st;
		ResultSet rs;
		List< Orders > result = null;

		try
		{
			if ( userId != null )
			{
				st = c.prepareStatement( "SELECT * FROM ORDERS WHERE USERID = ?" );
				st.setString( 1, userId );
				rs = st.executeQuery();

				while ( rs.next() )
				{
					if ( result == null )
					{
						result = new ArrayList<>();
					}

					Orders orders = new Orders( getOrderItems( rs.getInt( "ORDERID" ) ),
					                            rs.getString( "USERID" ),
					                            rs.getString( "STATUS" ) );

					orders.setOrderId( rs.getInt( "ORDERID" ) );
					result.add( orders );
				}

				rs.close();
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
	public Orders getOrdersFromOrderId( int userId )
	{
		PreparedStatement st;
		ResultSet rs;
		Orders result = null;

		try
		{
			if ( userId > 0 )
			{
				st = c.prepareStatement( "SELECT * FROM ORDERS WHERE ORDERID = ?" );
				st.setInt( 1, userId );
				rs = st.executeQuery();

				while ( rs.next() )
				{
					result = new Orders( getOrderItems( rs.getInt( "ORDERID" ) ),
					                     rs.getString( "USERID" ), rs.getString( "STATUS" ) );

					result.setOrderId( rs.getInt( "ORDERID" ) );
				}

				rs.close();
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
	public List< Orders > getAllOrders()
	{
		PreparedStatement st;
		ResultSet rs;
		List< Orders > result = new ArrayList<>();

		try
		{
			st = c.prepareStatement( "SELECT * FROM ORDERS" );
			rs = st.executeQuery();

			while ( rs.next() )
			{
				Orders order = new Orders( getOrderItems( rs.getInt( "ORDERID" ) ),
				                           rs.getString( "USERID" ), rs.getString( "STATUS" ) );

				order.setOrderId( rs.getInt( "ORDERID" ) );
				result.add( order );
			}

			rs.close();
			st.close();
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return result;
	}

	@Override
	public List< OrderItem > getOrderItems( int orderId )
	{
		PreparedStatement st;
		ResultSet rs;
		List< OrderItem > result = null;
		ProductPersistence accessProducts = Services.getProductPersistence();

		try
		{
			if ( orderId > 0 )
			{
				st = c.prepareStatement( "SELECT * FROM ORDERITEMS WHERE ORDERID = ?" );
				st.setInt( 1, orderId );
				rs = st.executeQuery();

				while ( rs.next() )
				{
					if ( result == null )
					{
						result = new ArrayList<>();
					}

					result.add( new OrderItem( accessProducts.getProduct( rs.getString( "SKU" ) ),
					                           rs.getInt( "QUANTITY" ), rs.getDouble( "PRICE" ) ) );
				}

				rs.close();
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
	public boolean addOrders( Orders order )
	{
		PreparedStatement st;

		try
		{
			if ( order != null )
			{
				int orderId;

				if ( getAllOrders() == null )
				{
					orderId = 1;
				}
				else
				{
					orderId = getAllOrders().size() + 1;
				}

				order.setOrderId( orderId );

				st = c.prepareStatement( "INSERT INTO ORDERS VALUES(?, ?, ?)" );

				st.setInt( 1, orderId );
				st.setString( 2, order.getUserId() );
				st.setString( 3, order.getStatus() );
				st.executeUpdate();
				st.close();

				addOrderItems( order );

				return true;
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return false;
	}

	@Override
	public boolean updateOrder( Orders order )
	{

		PreparedStatement st;
		ResultSet rs;
		boolean result = false;

		try
		{
			if ( order != null )
			{
				st = c.prepareStatement( "UPDATE ORDERS SET STATUS = ? WHERE ORDERID = ?" );
				st.setString( 1, order.getStatus() );
				st.setInt( 2, order.getOrderId() );
				st.executeUpdate();
				st.close();
				result = true;
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return result;
	}

	@Override
	public boolean addOrderItems( Orders order )
	{
		PreparedStatement st;
		ResultSet rs;
		List< OrderItem > items;
		int orderID;

		try
		{
			if ( order != null )
			{
				items = order.getItems();
				orderID = order.getOrderId();

				if ( items != null )
				{
					for ( int i = 0; i < items.size(); i++ )
					{
						int qty = items.get( i ).getQuantity();
						int newQty = -1;
						boolean success = false;
						String sku = items.get( i ).getProduct().getSku();

						st = c.prepareStatement( "INSERT INTO ORDERITEMS VALUES(?, ?, ?, ?, ?)" );
						st.setInt( 1, i );
						st.setString( 2, sku );
						st.setInt( 3, qty );
						st.setDouble( 4, items.get( i ).getPrice() );
						st.setInt( 5, orderID );
						st.executeUpdate();
						st.close();

						st = c.prepareStatement( "SELECT * FROM PRODUCTS WHERE SKU = ?" );
						st.setString( 1, sku );
						rs = st.executeQuery();

						if ( rs.next() )
						{
							newQty = rs.getInt( "QUANTITY" ) - qty;
							success = true;
						}

						rs.close();
						st.close();

						if ( success )
						{
							st = c.prepareStatement(
								"UPDATE PRODUCTS SET QUANTITY = ? WHERE SKU = ?" );
							st.setInt( 1, newQty );
							st.setString( 2, sku );
							st.executeUpdate();
							st.close();
						}
					}

					return true;
				}
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return false;
	}
}
