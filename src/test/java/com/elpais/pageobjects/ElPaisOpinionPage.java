package com.elpais.pageobjects;
import com.elpais.hooks.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.elpais.utils.SeleniumUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ElPaisOpinionPage {
    List<WebElement> articlesList;
    WebDriver driver;

    List<String> titlesList;
    List<String> translatedTitlesList;

    Map<String, Integer> wordsOccurencesMap = new HashMap<>();
    public ElPaisOpinionPage(WebDriver driver) {
        titlesList=new ArrayList<>();
        translatedTitlesList = new ArrayList<>();
        this.driver = driver;
    }
    public void setArticlesList() {
        articlesList = driver.findElements(By.cssSelector("[id=\"main-content\"] article h2 a"));
    }
    public WebElement getArticleAtIndex(int index) {
        return articlesList.get(index);
    }
    public void navigateToNthArticle(int i) {
        WebElement article = null;
        try {
            article = getArticleAtIndex(i);
            String articleURL = article.getAttribute("href");
            driver.navigate().to(articleURL);
        } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
            SeleniumUtils.refreshPage(driver);
            SeleniumUtils.waitForPageToLoad(driver, 30);
            setArticlesList();
            article = getArticleAtIndex(i);
            String articleURL = article.getAttribute("href");
            driver.navigate().to(articleURL);
        }
    }
    public String getTitleOfArticle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try{
            WebElement title = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1"))));
            return title.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public void saveTitleInSpanish(String title){
        titlesList.add(title);
    }

    public String getContent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            WebElement content = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[data-dtm-region=\"articulo_cuerpo\"]"))));
            return content.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    public void navigateTo() {
        String url = SeleniumUtils.getURLOfPage(driver);
        if (!url.contentEquals("https://elpais.com/opinion/")) {
            driver.navigate().to("https://elpais.com/opinion/");
        }
    }

    public String getCoverImage(){
        WebElement img = driver.findElement(By.cssSelector("[class=\"a_e_m\"] img"));
        return img.getAttribute("src");
    }

    public List<String> getTitlesList(){
        return titlesList;
    }

    public List<String> getTranslatedTitlesList(){
        return translatedTitlesList;
    }

    public void translateTitles(String fromLang, String toLang){
        for(String title: titlesList){
            try {
                String translatedTitle = Hooks.getTranslator().translate(title, fromLang, toLang);
                translatedTitlesList.add(translatedTitle);
            }
            catch (Exception e){
                System.out.println("Error occured while translating "+title+ ": "+e.getMessage());
            }
        }
    }

    public void printTranslatedTitles(){
        int i = 0;
        for (String translated: translatedTitlesList
        ) {
            System.out.println("Tranlsated title of article number "+i+1+ " is "+translated);
            i++;
        }
    }

    public void countWordOccurences(List<String> listTextContent) {
        for (String title : listTextContent) {
            String[] strArray = title.toLowerCase().split("\\s+");
            for (String word : strArray) {
                if (wordsOccurencesMap.containsKey(word)) {
                    wordsOccurencesMap.put(word, wordsOccurencesMap.get(word) + 1);
                } else {
                    wordsOccurencesMap.put(word, 1);
                }
            }
        }
    }
    public void printWordsOccuringMoreThanTwice(){
        System.out.println("Words occuring more than twice are : ");
        for(Map.Entry<String, Integer> entry : wordsOccurencesMap.entrySet()){
            if(entry.getValue()>2){
                System.out.println(entry.getKey());
            }
        }
    }



}