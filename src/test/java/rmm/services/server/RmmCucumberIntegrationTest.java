package rmm.services.server;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * The Class RmmCucumberIntegrationTest.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/features", plugin = {"pretty", "html:target/cucumber-reports"}, monochrome = true)
public class RmmCucumberIntegrationTest {
}
