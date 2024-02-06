package stepDefinitions.homePage;

import TestContexts.TestContext;
import factory.DriverFactory;
import io.cucumber.java.en.And;
import pages.homePage.HomePage;
import utils.BrowserUtility;
import utils.PropertiesReader;

import java.io.File;

public class HomePageSteps {
    private HomePage homePage;
    private BrowserUtility browserUtility;

    public HomePageSteps(){
        homePage = new HomePage(DriverFactory.getDriver());
        browserUtility = new BrowserUtility();
    }

    @And("^I go to \"tatil dukkanı\" website")
    public void iGoToTatilDukkani(){
        homePage.iGoToTatilDukkani(PropertiesReader.getParameter(PropertiesReader.getParameter("environment")+".url"),browserUtility.getBrowserName());
    }

}
