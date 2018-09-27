package comp3350.skipthecart;

import android.support.test.rule.ActivityTestRule;
import android.support.transition.Transition;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.skipthecart.application.Services;
import comp3350.skipthecart.logic.AccessEmployee;
import comp3350.skipthecart.objects.Employee;
import comp3350.skipthecart.presentation.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PlaceOrderTest.class,
        ViewOrderTest.class,
        ShipOrderTest.class

})
public class OrderShippingTest {

}
