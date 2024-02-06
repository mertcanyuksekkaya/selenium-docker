package base;

import org.openqa.selenium.WebDriver;

public class ThreadDriver {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public synchronized static WebDriver getDriver() {
        return driver.get();
    }

    public synchronized WebDriver setDriver(WebDriver driver) {
        this.driver.set(driver);
        return this.driver.get();
    }
}
