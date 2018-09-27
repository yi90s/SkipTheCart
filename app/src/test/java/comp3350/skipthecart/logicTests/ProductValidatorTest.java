package comp3350.skipthecart.logicTests;

import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.ProductException;
import comp3350.skipthecart.logic.ProductValidator;

public class ProductValidatorTest
{
	private static final ProductValidator productValidator = new ProductValidator();
	private static final String dummyArg = "test";
	private static final String validNum = "1";

	@BeforeClass
	public static void setStubs()
	{
		Services.setStubs( true );
	}

	@Test( expected = ProductException.class )
	public void testEmptyName()
	{
		productValidator.getValidProduct( "", dummyArg, dummyArg, validNum, validNum );
	}

	@Test( expected = ProductException.class )
	public void testLongName()
	{
		productValidator.getValidProduct(
			"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", dummyArg, dummyArg,
			validNum, validNum );
	}

	@Test( expected = ProductException.class )
	public void testEmptySku()
	{
		productValidator.getValidProduct( dummyArg, "", dummyArg, validNum, validNum );
	}

	@Test( expected = ProductException.class )
	public void testLongSku()
	{
		productValidator.getValidProduct( dummyArg,
		                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		                                  dummyArg, validNum, validNum );
	}

	@Test( expected = ProductException.class )
	public void testEmptyDescription()
	{
		productValidator.getValidProduct( dummyArg, dummyArg, "", validNum, validNum );
	}

	@Test( expected = ProductException.class )
	public void testEmptyPrice()
	{
		productValidator.getValidProduct( dummyArg, dummyArg, dummyArg, "", validNum );
	}

	@Test( expected = ProductException.class )
	public void testLongPrice()
	{
		productValidator.getValidProduct( dummyArg, dummyArg, dummyArg, "12345678987654",
		                                  validNum );
	}

	@Test( expected = ProductException.class )
	public void testEmptyQty()
	{
		productValidator.getValidProduct( dummyArg, dummyArg, dummyArg, validNum, "" );
	}

	@Test( expected = ProductException.class )
	public void testLongQty()
	{
		productValidator.getValidProduct( dummyArg, dummyArg, dummyArg, validNum, "12345678987" );
	}
}
