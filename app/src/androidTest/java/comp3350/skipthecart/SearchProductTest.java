package comp3350.skipthecart;

import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.presentation.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

public class SearchProductTest {
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
    public void searchProduct()
    {
        onView(withId(R.id.customer_button))
                .perform(click());

        //login by prepared customer account
        AccountTestUtils.loginByCustomer();
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Potato Chips")).perform(pressImeActionButton());
        onData(anything()).inAdapterView(withId(R.id.gridView)).atPosition(0).perform(click());
        onView(withId(R.id.product_name)).check(matches(withText("Potato Chips")));
        pressBack();
        onView(withId(R.id.account)).perform(click());

        onView(withId(R.id.logout)).perform(click());

        pressBack();

    }
}
