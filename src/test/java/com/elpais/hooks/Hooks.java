package com.elpais.hooks;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.elpais.utils.RapidTranslateClient;
import com.elpais.utils.ScreenshotUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashMap;
public class Hooks {

    public static RemoteWebDriver driver;
    private static RapidTranslateClient translator;

    private static boolean isRunningOnMobile = false;
    private static String browser = "";




    public static String getBrowser() {
        return browser;
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        HashMap<String, String> bstackOptions = new HashMap<>();
        bstackOptions.putIfAbsent("source", "cucumber-java:sample-master:v1.2");
        capabilities.setCapability("bstack:options", bstackOptions);
        driver = new RemoteWebDriver(
                new java.net.URL("https://hub.browserstack.com/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        translator = new RapidTranslateClient();


        browser = driver.getCapabilities().getCapability("browserName").toString();

        if(driver.getCapabilities().getCapability("deviceName")!=null){
            isRunningOnMobile = true;
        }
        else{
            driver.manage().window().maximize();
        }

    }

    public static boolean getIsRunningOnMobile(){
        return isRunningOnMobile;
    }

    @After
    public void cleanUp(Scenario scenario){
        if(scenario.isFailed()){
            try {
                ScreenshotUtils.takeScreenshot(driver, scenario.getName());
            } catch (IOException e) {
                System.out.println("Error occured while taking screenshot for scenario: "+scenario.getName());
                e.printStackTrace();
            }
        }
        driver.quit();
    }

    public static RapidTranslateClient getTranslator(){
        return translator;
    }

}