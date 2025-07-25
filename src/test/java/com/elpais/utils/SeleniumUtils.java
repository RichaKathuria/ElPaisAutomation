package com.elpais.utils;
import browserstack.shaded.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
public class SeleniumUtils {

    public static String getURLOfPage(WebDriver driver){
        return driver.getCurrentUrl();
    }

    public static void refreshPage(WebDriver driver){
        driver.navigate().refresh();
    }


    public static void waitForPageToLoad(WebDriver driver, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(
                (ExpectedCondition<Boolean>) wd ->
                        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
        );
    }

    public static void saveImage(String imgURL , String fileName){
        try(InputStream in= new URL(imgURL).openStream()){
            File target = new File(fileName);
            FileUtils.copyInputStreamToFile(in, target);
        } catch (Exception e) {
            System.out.println("Error occured while saving image to local."+e.getMessage());
        }
    }
}