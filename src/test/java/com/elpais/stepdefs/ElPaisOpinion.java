package com.elpais.stepdefs;

import com.elpais.hooks.Hooks;
import com.elpais.pageobjects.ElPaisHomePage;
import com.elpais.pageobjects.ElPaisOpinionPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.elpais.utils.SeleniumUtils;

import java.util.List;
public class ElPaisOpinion {

    WebDriver driver = Hooks.driver;
    com.elpais.pageobjects.ElPaisHomePage ElPaisHomePage = new ElPaisHomePage(driver);
    ElPaisOpinionPage elPaisOpinionPage = new ElPaisOpinionPage(driver);

    @Given("the user navigates to the Site")
    public void theUserNavigatesToTheSite() {
        driver.get("https://elpais.com/");
    }
    @And("the user ensures that the website's text is displayed in Spanish")
    public void websiteSTextIsDisplayedInSpanish() {
        String lang = ElPaisHomePage.getPageLanguage();
        Assert.assertEquals(lang, "es-ES", "The Website is not in Spanish.");
    }
    @And("the user accepts the cookie alert on screen")
    public void theUserAcceptsTheCookieAlertOnScreen() throws InterruptedException {
        ElPaisHomePage.acceptCookieAlert();
    }
    @And("the user navigates to the Opinion section")
    public void theUserNavigatesToTheOpinionSection() {
        ElPaisHomePage.navigateToOpinionSection();
    }
    @Then("the user should be navigated to Opinion section")
    public void theUserShouldBeNavigatedToOpinionSection() {
        String currentURL = SeleniumUtils.getURLOfPage(driver);
        Assert.assertEquals(currentURL, "https://elpais.com/opinion/", "User not on Opinion section");
    }

    @And("fetch the first five articles of the section")
    public void fetchTheFirstFiveArticlesOfTheSection() {
        elPaisOpinionPage.setArticlesList();
    }

    @And("the user prints the title and content of first {int} articles and saves the cover image to local")
    public void theUserPrintsTheTitleAndContentOfFirstArticlesInSpanish(int totalArticleToPrint) {
        for(int i=0; i<totalArticleToPrint; i++){
            System.out.println("Reading article number: "+(i+1));
            elPaisOpinionPage.navigateToNthArticle(i);
            SeleniumUtils.waitForPageToLoad(driver, 30);
            System.out.println("URL of page: "+SeleniumUtils.getURLOfPage(driver));
            String title = elPaisOpinionPage.getTitleOfArticle();
            elPaisOpinionPage.saveTitleInSpanish(title);
            String content = elPaisOpinionPage.getContent();
            System.out.println("Title of article : "+title);
            System.out.println("Content of article : "+content);
            String imgURL = elPaisOpinionPage.getCoverImage();
            String imgPath= "coverImages/article-"+i+".png";
            SeleniumUtils.saveImage(imgURL, imgPath);
            elPaisOpinionPage.navigateTo();
        }
    }
    @And("user gets title of each article translated to English")
    public void userGetsTitleOfEachArticleTranslatedToEnglish() {
        elPaisOpinionPage.translateTitles("es" , "en");
        elPaisOpinionPage.printTranslatedTitles();
    }
    @And("the user identifies any words that are repeated more than twice across all headers combined and prints them")
    public void theUserIdentifiesAnyWordsThatAreRepeatedMoreThanTwiceAcrossAllHeadersCombinedAndPrintsThem() {
        List<String> translatedTitles = elPaisOpinionPage.getTranslatedTitlesList();
        elPaisOpinionPage.countWordOccurences(translatedTitles);
        elPaisOpinionPage.printWordsOccuringMoreThanTwice();
    }
}