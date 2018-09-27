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

public class ViewOrderTest {
    //NOTE

    //THIS TEST WILL FAIL IF THERE ARE NO ORDERS PLACED
    //PLEASE MAKE SURE THAT THERE ARE SOME ORDERS PLACED
    //YOU CAN RUN PlaceOrderTest before running this test.
    //or you can run OrderShippingTest which will run all the tests for the ship feature

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
    public void viewOrder()
    {
        onView(withId(R.id.employee_button))
                .perform(click());

        //login by prepared customer account
        AccountTestUtils.loginByEmployee();

        onView(withId(R.id.cart_button))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.ordersList)).atPosition(0).perform(click());

        onView(withId(R.id.checkout)).perform(click());

        onView(withId(R.id.account)).perform(click());

        onView(withId(R.id.logout)).perform(click());
    }

}
