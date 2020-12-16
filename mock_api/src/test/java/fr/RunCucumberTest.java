package fr;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(value = Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources", tags="not @Ignore")
public class RunCucumberTest { // will run all features found on the classpath
    // in the same package as this class
}
