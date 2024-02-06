package factory;

import exception.TargetNotValidException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.BrowserUtility;
import utils.PropertiesReader;

import java.net.URL;

public class TargetFactory {

    private static final Logger logger = LogManager.getLogger(TargetFactory.class);

    public WebDriver createInstance(String browser) {
        Target target = Target.valueOf(PropertiesReader.getParameter("target").toUpperCase());
        WebDriver webdriver;
        switch (target) {
            case LOCAL:
                webdriver = BrowserFactory.valueOf(browser.toUpperCase()).createDriver();
                break;
            case REMOTE:
                webdriver = createRemoteInstance(browser);
                break;
            default:
                throw new TargetNotValidException(target.toString());
        }
        return webdriver;
    }

    private RemoteWebDriver createRemoteInstance(String browser) {
        RemoteWebDriver remoteWebDriver = null;
        String gridURL = null;
        try {
            gridURL = String.format("%s", PropertiesReader.getParameter("gridURL"));
            MutableCapabilities mutableCapabilities = BrowserFactory.valueOf(browser.toUpperCase()).getOptions();
//            mutableCapabilities.setCapability("se:recordVideo",true);
            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), mutableCapabilities);
        } catch (java.net.MalformedURLException e) {
            logger.error("Grid URL is invalid or Grid is not available - "+gridURL);
            logger.error(String.format("Browser: %s", new BrowserUtility().getBrowserName()));
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Browser %s is not valid or recognized", new BrowserUtility().getBrowserName()));
        }
        return remoteWebDriver;
    }

    enum Target {
        LOCAL, REMOTE
    }
}
