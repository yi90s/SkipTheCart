package comp3350.skipthecart.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.persistence.ProductPersistence;

public class ProductPersistenceHSQLDB implements ProductPersistence
{
	private final Connection c;

	public ProductPersistenceHSQLDB( final String dbPath )
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
	public List< Product > getProductSequential()
	{
		Statement st;
		ResultSet rs;
		Product product;
		List< Product > products = new ArrayList<>();

		try
		{
			st = c.createStatement();
			rs = st.executeQuery( "SELECT * FROM PRODUCTS" );

			while ( rs.next() )
			{
				product = new Product( rs.getString( "PRODUCTNAME" ), rs.getString( "SKU" ),
				                       rs.getString( "DESCRIPTION" ), rs.getDouble( "PRICE" ),
				                       rs.getInt( "QUANTITY" ), rs.getString( "IMAGE" ) );
				products.add( product );
			}

			st.close();
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return products;
	}

	@Override
	public Product getProduct( String argSku )
	{
		PreparedStatement st;
		ResultSet rs;
		Product result = null;

		try
		{
			if ( argSku != null )
			{
				st = c.prepareStatement( "SELECT * FROM PRODUCTS WHERE SKU = ?" );
				st.setString( 1, argSku );
				rs = st.executeQuery();

				if ( rs.next() )
				{
					result = new Product( rs.getString( "PRODUCTNAME" ), rs.getString( "SKU" ),
					                      rs.getString( "DESCRIPTION" ), rs.getDouble( "PRICE" ),
					                      rs.getInt( "QUANTITY" ), rs.getString( "IMAGE" ) );
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
	public Product insertProduct( Product currentProduct )
	{
		PreparedStatement st;

		try
		{
			if ( currentProduct != null )
			{
				st = c.prepareStatement( "INSERT INTO PRODUCTS VALUES(?, ?, ?, ?, ?, ?)" );
				st.setString( 1, currentProduct.getProductName() );
				st.setString( 2, currentProduct.getSku() );
				st.setString( 3, currentProduct.getDescription() );
				st.setDouble( 4, currentProduct.getPrice() );
				st.setLong( 5, currentProduct.getQuantity() );
				st.setString( 6, currentProduct.getImage() );
				st.executeUpdate();
				st.close();
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return currentProduct;
	}

	@Override
	public Product updateProduct( Product currentProduct )
	{
		PreparedStatement st;

		try
		{
			st = c.prepareStatement(
				"UPDATE PRODUCTS SET PRODUCTNAME = ?, DESCRIPTION = ?, PRICE = ?, QUANTITY = ? WHERE SKU = ?" );
			st.setString( 1, currentProduct.getProductName() );
			st.setString( 2, currentProduct.getDescription() );
			st.setDouble( 3, currentProduct.getPrice() );
			st.setLong( 4, currentProduct.getQuantity() );
			st.setString( 5, currentProduct.getSku() );
			st.executeUpdate();
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return currentProduct;
	}

	@Override
	public void deleteProduct( Product currentProduct )
	{
		PreparedStatement st;

		try
		{
			if ( currentProduct != null )
			{
				st = c.prepareStatement( "DELETE FROM PRODUCTS WHERE SKU = ?" );
				st.setString( 1, currentProduct.getSku() );
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
	public List< Product > searchProduct( String query )
	{
		PreparedStatement st;
		ResultSet rs;
		String[] words;
		String append = "";
		List< Product > results = new ArrayList<>();

		try
		{
			if ( query != null && !query.isEmpty() )
			{
				words = query.trim().split( "\\s+" );

				if ( words.length > 1 )
				{
					for ( int i = 1; i < words.length; i++ )
					{
						append += " AND UPPER(PRODUCTNAME) LIKE UPPER('%" + words[i] + "%')";
					}
				}

				st = c.prepareStatement(
					"SELECT * FROM PRODUCTS WHERE UPPER(PRODUCTNAME) LIKE UPPER('%" + words[0] +
					"%')" + append );
				rs = st.executeQuery();

				while ( rs.next() )
				{
					results.add( new Product( rs.getString( "PRODUCTNAME" ),
					                          rs.getString( "SKU" ),
					                          rs.getString( "DESCRIPTION" ),
					                          rs.getDouble( "PRICE" ),
					                          rs.getInt( "QUANTITY" ),
					                          rs.getString( "IMAGE" ) ) );
				}

				st.close();
			}
		}
		catch ( final SQLException e )
		{
			throw new PersistenceException( e );
		}

		return results;
	}
}
