package comp3350.skipthecart;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.objects.Product;
import comp3350.skipthecart.presentation.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

public class InventoryManagementTest
{
	private static Product addedProduct;
	@Rule
	public ActivityTestRule< MainActivity > activityRule = new ActivityTestRule<>(
		MainActivity.class );

	@Before
	public void setUp()
	{
		addedProduct = ProductTestUtils.getAddedProduct();
		Services.setStubs( true );
	}

	@After
	public void tearDown()
	{
		Services.setStubs( false );
	}

	@Test
	public void changeProductTest()
	{

		//login by employee
		onView( withId( R.id.employee_button ) )
			.perform( click() );
		AccountTestUtils.loginByEmployee();

		onView( withId( R.id.add_button ) )
			.perform( click() );

		//fill information of new added product
		onView( withId( R.id.product_name ) )
			.perform( typeText( addedProduct.getProductName() ) );
		onView( withId( R.id.product_sku ) )
			.perform( typeText( addedProduct.getSku() ) );
		onView( withId( R.id.product_price ) )
			.perform( typeText( String.valueOf( addedProduct.getPrice() ) ) );
		onView( withId( R.id.product_quantity ) )
			.perform( typeText( String.valueOf( addedProduct.getQuantity() ) ) );
		onView( withId( R.id.product_description ) )
			.perform( typeText( addedProduct.getProductName() ) );
		onView( withId( R.id.button_create_product ) )
			.perform( click() );

		//edit information of a product
		onData( anything() ).inAdapterView( withId( R.id.gridView ) ).atPosition( 7 ).perform(
			click() );
		onView( withId( R.id.details_button ) ).perform( click() );
		onView( withId( R.id.product_name ) )
			.perform( clearText(), typeText( addedProduct.getProductName() + "2" ) );
		onView( withId( R.id.product_price ) )
			.perform( clearText(), typeText( String.valueOf( addedProduct.getPrice() + 2 ) ) );
		onView( withId( R.id.product_quantity ) )
			.perform( clearText(), typeText( String.valueOf( addedProduct.getQuantity() + 2 ) ) );
		onView( withId( R.id.product_description ) )
			.perform( clearText(), typeText( addedProduct.getProductName() + "2" ),
			          closeSoftKeyboard() );
		onView( withId( R.id.button_update_product ) )
			.perform( click() );

		//delete the added product
		onView( withId( R.id.details_button ) ).perform( click() );
		onView( withId( R.id.button_delete_product ) ).perform( click() );
	}
}
