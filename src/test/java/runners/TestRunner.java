package runners;
import factory.DriverFactory;
import factory.TargetFactory;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.PropertiesReader;
import java.io.File;

@CucumberOptions(
        features = "features",
        glue = {"stepDefinitions","hook"},
        tags = ""
)
public class TestRunner {

    private TestNGCucumberRunner testNGCucumberRunner;


    @BeforeSuite
    public void setUpSuite(){
//        if you are using java build, give -Dpackage=docker on the command line
        if (System.getProperty("package") == null){
            String featuresPath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"features";
            System.setProperty("cucumber.features", featuresPath);
        }
        System.setProperty("basefolder.name","test-reports" + File.separator + PropertiesReader.getParameter("environment") + File.separator);
    }

    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser"})
    public synchronized void setUpClass(String browser) {
        WebDriver driver = new DriverFactory().setDriver(new TargetFactory().createInstance(browser));
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite(){
        String destination = new File(System.getProperty("user.dir")+"/screenshots").getAbsolutePath();
        if (new File(destination).exists()) {
            new File(destination).delete();
        }
    }
}
