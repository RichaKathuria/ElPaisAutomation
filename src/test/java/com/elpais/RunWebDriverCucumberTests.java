package com.elpais;

import io.cucumber.testng.*;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        glue = {"com.elpais.stepdefs", "com.elpais.hooks"},
        features = "src/test/resources/features/test",
        plugin = {
                "pretty",
                "html:reports/tests/cucumber/cucumber-pretty.html",
                "testng:reports/tests/cucumber/testng/cucumber.xml",
                "json:reports/tests/cucumber/json/cucumberTestReport.json"
        }
)
public class RunWebDriverCucumberTests extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios(){
                return super.scenarios();
        }

}
