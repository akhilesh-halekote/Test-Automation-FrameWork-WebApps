package tests;

import org.qa.driverfactory.Browser;
import org.qa.listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static tests.TestModules.*;

@Listeners(TestListener.class)
public class SampleTestSuite {

    @Test
    @Browser
    public void testLogin(){
        validLoginScenario();
    }


}
