package com.elpais.utils;
import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String name) throws IOException {
        File src= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("screenshots/"+name+".png"));
    }
}