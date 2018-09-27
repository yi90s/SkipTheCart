package comp3350.skipthecart.application;

import comp3350.skipthecart.persistence.CustomerPersistence;
import comp3350.skipthecart.persistence.EmployeePersistence;
import comp3350.skipthecart.persistence.OrdersPersistence;
import comp3350.skipthecart.persistence.PaymentPersistence;
import comp3350.skipthecart.persistence.ProductPersistence;
import comp3350.skipthecart.persistence.hsqldb.CustomerPersistenceHSQLDB;
import comp3350.skipthecart.persistence.hsqldb.EmployeePersistenceHSQLDB;
import comp3350.skipthecart.persistence.hsqldb.OrdersPersistenceHSQLDB;
import comp3350.skipthecart.persistence.hsqldb.PaymentPersistenceHSQLDB;
import comp3350.skipthecart.persistence.hsqldb.ProductPersistenceHSQLDB;
import comp3350.skipthecart.persistence.stubs.CustomerPersistenceStub;
import comp3350.skipthecart.persistence.stubs.EmployeePersistenceStub;
import comp3350.skipthecart.persistence.stubs.OrdersPersistenceStub;
import comp3350.skipthecart.persistence.stubs.PaymentPersistenceStub;
import comp3350.skipthecart.persistence.stubs.ProductPersistenceStub;

public class Services
{
	private static ProductPersistence productPersistence = null;
	private static CustomerPersistence customerPersistence = null;
	private static EmployeePersistence employeePersistence = null;
	private static PaymentPersistence paymentPersistence = null;
	private static OrdersPersistence ordersPersistence = null;
	private static String dbName = "local";
	private static boolean useStubs = false;

	public static void setStubs( final boolean value )
	{
		useStubs = value;
	}

	public static synchronized ProductPersistence getProductPersistence()
	{
		if ( productPersistence == null )
		{
			if ( useStubs )
			{
				productPersistence = new ProductPersistenceStub();
			}
			else
			{
				productPersistence = new ProductPersistenceHSQLDB( dbName );
			}
		}

		return productPersistence;
	}

	public static synchronized EmployeePersistence getEmployeePersistence()
	{
		if ( employeePersistence == null )
		{
			if ( useStubs )
			{
				employeePersistence = new EmployeePersistenceStub();
			}
			else
			{
				employeePersistence = new EmployeePersistenceHSQLDB( dbName );
			}
		}

		return employeePersistence;
	}

	public static synchronized CustomerPersistence getCustomerPersistence()
	{
		if ( customerPersistence == null )
		{
			if ( useStubs )
			{
				customerPersistence = new CustomerPersistenceStub();
			}
			else
			{
				customerPersistence = new CustomerPersistenceHSQLDB( dbName );
			}
		}

		return customerPersistence;
	}

	public static synchronized PaymentPersistence getPaymentPersistence()
	{
		if ( paymentPersistence == null )
		{
			if ( useStubs )
			{
				paymentPersistence = new PaymentPersistenceStub();
			}
			else
			{
				paymentPersistence = new PaymentPersistenceHSQLDB( dbName );
			}
		}

		return paymentPersistence;
	}

	public static synchronized OrdersPersistence getOrdersPersistence()
	{
		if ( ordersPersistence == null )
		{
			if ( useStubs )
			{
				ordersPersistence = new OrdersPersistenceStub();
			}
			else
			{
				ordersPersistence = new OrdersPersistenceHSQLDB( dbName );
			}
		}

		return ordersPersistence;
	}

	public static String getDBPathName()
	{
		return dbName;
	}

	public static void setDBPathName( final String name )
	{
		try
		{
			Class.forName( "org.hsqldb.jdbcDriver" ).newInstance();
		}
		catch ( InstantiationException e )
		{
			e.printStackTrace();
		}
		catch ( IllegalAccessException e )
		{
			e.printStackTrace();
		}
		catch ( ClassNotFoundException e )
		{
			e.printStackTrace();
		}

		dbName = name;
	}
}
