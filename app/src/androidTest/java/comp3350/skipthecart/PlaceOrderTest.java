package comp3350.skipthecart;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.presentation.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

public class PlaceOrderTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        Services.setStubs(false);
    }

    @After
    public void tearDown(){
        Services.setStubs(false);
    }

    @Test
    public void placeOrder()
    {
        onView(withId(R.id.customer_button))
                .perform(click());

        //login by prepared customer account
        AccountTestUtils.loginByCustomer();

        //add an item into the cart
        onData(anything()).inAdapterView(withId(R.id.gridView)).atPosition(0).perform(click());
        onView(withId(R.id.details_button))
                .perform(click());
        pressBack();

        //add another item into the cart
        onData(anything()).inAdapterView(withId(R.id.gridView)).atPosition(1).perform(click());
        onView(withId(R.id.details_button))
                .perform(click());
        pressBack();

        //go to cart page
        onView(withId(R.id.cart_button))
                .perform(click());

        onView(withId(R.id.checkout)).perform(click());
        onView(withId(R.id.account)).perform(click());

        onView(withId(R.id.logout)).perform(click());

        pressBack();
    }
}
