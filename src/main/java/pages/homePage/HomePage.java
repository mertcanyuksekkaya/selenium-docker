package pages.homePage;

import exception.ScenarioInfoException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.homePage.constants.HomePageConstants;

public class HomePage extends HomePageConstants {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void iGoToTatilDukkani(String url, String browserInfo){
        navigate(url,browserInfo);
        wait(5);
    }


}
