package base;

import exception.ScenarioInfoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BasePage {

    protected WebDriver driver;
    protected JavascriptExecutor jsExec;
    protected final Logger log = LogManager.getLogger(BasePage.class);
    protected Duration waitSecond = Duration.ofSeconds(5);
    protected WebDriverWait wait;
    private  ThreadDriver threadDriver;

    protected BasePage(WebDriver driver){
        this.driver = driver;
        System.setProperty("file.encoding", "UTF-8");
        wait = new WebDriverWait(driver, waitSecond);
        jsExec = (JavascriptExecutor) this.driver;
        PageFactory.initElements(this.driver, this);
        threadDriver = new ThreadDriver();
        threadDriver.setDriver(this.driver);
    }

    protected void waitForLoad() {
        try {
            new WebDriverWait(driver, waitSecond).until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }

    public void wait(int second){
        try {
            Thread.sleep(second*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitms(int msecond){
        try {
            Thread.sleep(msecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void navigate(String url, String browserInfo){
        driver.get(url);
        driver.manage().window().maximize();
        log.info(Thread.currentThread().getName()+" : " + url + " adresi "+ browserInfo + " browser ile açıldı");
    }

    protected void navigateSpecific(String url){
        driver.get(url);
        driver.manage().window().maximize();
        log.info(Thread.currentThread().getName()+" : " + url + " adresi açıldı");
    }
    protected void navigateBack(){
        driver.navigate().back();
        log.info(Thread.currentThread().getName()+" bir önceki sayfaya dönüldü.");
    }
    protected void refresh(){
        driver.navigate().refresh();
        log.info(Thread.currentThread().getName()+" sayfa yenilendi.");
    }

    protected void waitVisibility(WebElement element) {
        wait = new WebDriverWait(driver, waitSecond);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }catch (StaleElementReferenceException e){
            wait(2);
            wait.until(ExpectedConditions.visibilityOf(element));
        }
    }

    protected void waitVisibility(By by) {
        try{
            wait = new WebDriverWait(driver, waitSecond);
            wait.until(visibilityOfElementLocated(by));
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }

    protected void waitVisibilityWithSecond(By by, int second) {
        try{
            wait = new WebDriverWait(driver, Duration.ofSeconds(second));
            wait.until(visibilityOfElementLocated(by));
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }

    public WebElement waitPresence(By by) {
        try{
            wait = new WebDriverWait(driver, waitSecond);
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }


    protected WebElement waitClickable(WebElement element) {
        try {
            wait = new WebDriverWait(driver, waitSecond);
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }

    protected void waitInVisibility(WebElement element){
        try {
            wait = new WebDriverWait(driver, waitSecond);
            wait.until(ExpectedConditions.invisibilityOf(element));
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }

    protected WebElement find(By by) {
        try{
//            waitForLoad();
            waitPresence(by);
            WebElement element = driver.findElement(by);
            log.info(Thread.currentThread().getName()+" : " + by+" elementi bulundu.");
            return element;
        }catch (NoSuchElementException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }

    public List<WebElement> finds(By by) {
        try{
//            waitForLoad();
            waitVisibility(by);
            List<WebElement> ListElement = driver.findElements(by);
            log.info(Thread.currentThread().getName()+" : "+ by +" elementleri bulundu.");
            return ListElement;
        }catch (NoSuchElementException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }

    protected void sendKeys(WebElement element,String text) {
        try{
//            waitForLoad();
//			waitVisibility(element);
            element.sendKeys(text);
            log.info(Thread.currentThread().getName()+" : "+element+" elementine "+text+" yazılıyor.");
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }
    protected void sendKeys(By by,String text) {
        try{
//            waitForLoad();
//			waitVisibility(element);
            find(by).sendKeys(text);
            log.info(Thread.currentThread().getName()+" : "+by+" elementine "+text+" yazılıyor.");
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }
    protected void sendKeysClear(WebElement element) {
        try{
//            waitForLoad();
            element.clear();
            log.info(Thread.currentThread().getName()+" : "+element+" elementindeki text temizleniyor.");
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }
    protected void sendKeysClear(By by) {
        try{
//            waitForLoad();
            find(by).clear();
            log.info(Thread.currentThread().getName()+" : "+by+" elementindeki text temizleniyor.");
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }
    public void clearText(WebElement element){
        sendKeys(element, Keys.CONTROL+"a");
        wait(1);
        sendKeys(element, String.valueOf(Keys.BACK_SPACE));
    }
    public void pressBackSpace(By by,int count){
        for (int i=0; i<count; i++){
            sendKeys(by, String.valueOf(Keys.BACK_SPACE));
            waitms(10);
        }
    }
    protected void click(WebElement element) {
        try{
//            waitForLoad();
            waitClickable(element);
            element.click();
            log.info(Thread.currentThread().getName()+" : "+element+" elementine tıklandı.");
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }
    protected void click(By by) {
        try{
//            waitForLoad();
            waitClickable(find(by));
            find(by).click();
            log.info(Thread.currentThread().getName()+" : "+by+" elementine tıklandı.");
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }

    protected void clickJS(WebElement element) {
        try{
            waitVisibility(element);
            jsExec.executeScript("arguments[0].click();", element);
            log.info(Thread.currentThread().getName()+" : "+element+" elementine JS ile tıklandı.");
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }
    protected void clickJS(By by) {
        try{
            waitVisibility(by);
            WebElement element =driver.findElement(by);
            jsExec.executeScript("arguments[0].click();", element);
            log.info(Thread.currentThread().getName()+" : "+by+" elementine JS ile tıklandı.");
        }catch (TimeoutException e){
            throw new ScenarioInfoException(e.getMessage());
        }
    }
    protected void switchTab() {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        System.out.println(driver.switchTo().window(tabs.get(tabs.size() - 1)));
    }
    protected void switchTab(int index) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(index));
        log.info(index+". sekmeye switch yapıldı.");
    }
    public void switchIframe(String iframeId) {
        try {
            driver.switchTo().frame(iframeId);
        } catch (Exception e) {
            wait(1);
            driver.switchTo().frame(iframeId);
        }
    }
    public void switchIframe(WebElement iframeId) {
        try {
            driver.switchTo().frame(iframeId);
            log.info(Thread.currentThread().getName()+" : "+iframeId+" iframe'e switch yapıldı.");
        } catch (Exception e) {
            wait(1);
            driver.switchTo().frame(iframeId);
            log.info(Thread.currentThread().getName()+" : "+iframeId+" iframe'e switch işlemi tekrar denendi.");
        }
    }

    //If you want to switch to the iframe, that index is written.
    public void switchIframe(int iframeIndex) {
        try {
            driver.switchTo().frame(iframeIndex);
        } catch (Exception e) {
            wait(1);
            driver.switchTo().frame(iframeIndex);
        }
    }

    public void switchDefaultContent(){
        driver.switchTo().defaultContent();
    }

    public void openInNewTab(String url) {
        jsExec.executeScript("window.open('about:blank','_blank');");
        log.info(Thread.currentThread().getName()+" : yeni sekme açma işlemi yapıldı.");
        switchTab();
        navigateSpecific(url);
        log.info(Thread.currentThread().getName()+" :"+ url +" linkine yeni sekmede gidiliyor.");
    }

    protected String getElementText(WebElement element){
        waitVisibility(element);
        return element.getText();
    }

    protected String getElementText(By by){
        return find(by).getText();
    }
    protected List<String> getAllElementText(By by){
        List<String> list2 = new ArrayList<>();
        if (isElementExist(by)){
            List<WebElement> list = driver.findElements(by);
            for (WebElement element : list){
                list2.add(element.getText());
            }
        }
        return list2;
    }

    protected String getElementTextWitOutVisibility(WebElement element){
        return element.getText();
    }

    public boolean isElementExist(WebElement element) {
        try {
            log.info(Thread.currentThread().getName()+" : "+ element + " elementi var.");
            return element.isDisplayed();
        } catch(NoSuchElementException e) {
            log.info(Thread.currentThread().getName()+" : "+ element + " elementi yok.");
            return false;
        }
    }
    public boolean isElementExist(By by) {
        if (driver.findElements(by).size() > 0) {
            log.info(Thread.currentThread().getName()+" : "+by + " elementi var.");
            return true;
        }else {
            log.info(Thread.currentThread().getName()+" : "+by+" elementi yok.");
            return false;
        }
    }
    public void selectVisibleText(By by,String text){
        Select select = new Select(find(by));
        select.selectByVisibleText(text);
    }

    public void selectVisibleText(WebElement element,String text){
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public void selectByValue(By by,String value){
        Select select = new Select(find(by));
        select.selectByValue(value);
    }

    public void selectByIndex(By by,int index){
        Select select = new Select(find(by));
        select.selectByIndex(index);
    }
    public void selectElementIndex(WebElement element,int index){
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public void scrollTo(int pixel){
        jsExec.executeScript("window.scrollBy(0,"+pixel+")", "");
        System.out.println("scroll edildi.");
    }

    public void scrollToElement(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        try {
            jse.executeScript("arguments[0].scrollIntoView()",element);
        }catch (Exception e){
            jse.executeScript("arguments[0].scrollIntoView()",element);
        }
        log.info(Thread.currentThread().getName()+" : "+"Elemente scroll yapıldı");
    }
}
