package com.elpais.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElPaisHomePage {

    WebDriver driver;

    public ElPaisHomePage(WebDriver driver){
        this.driver = driver;
    }

    public void acceptCookieAlert() throws InterruptedException {
        By acceptBtn = By.cssSelector("#didomi-notice #didomi-notice-agree-button");
        WebElement acceptButton = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(acceptBtn));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", acceptButton);

        acceptButton.click();
    }

    public String getPageLanguage(){
        String lang = driver.findElement(By.tagName("html")).getAttribute("lang");
        return lang;
    }

    public void navigateToOpinionSection(){
        driver.navigate().to("https://elpais.com/opinion/");
    }


}