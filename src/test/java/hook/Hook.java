package hook;

import TestContexts.TestContext;
import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Hook {

    private TestContext testContext;
    public Hook(TestContext testContext){
        this.testContext = testContext;
    }

    @Before()
    public void startScenario(Scenario scenario){
        testContext.setScenarioName(scenario.getName());
        List<String> tagName = (List<String>) scenario.getSourceTagNames();
        String tagName1 = "";
        int j=0;
        for (String tag : tagName){
            if (j>1)
                tagName1 = tagName1+tag;
            j++;
        }
        testContext.setScenarioTags(tagName1);
    }

    @After(order = 0)
    public void finishScenario(){
        if (DriverFactory.getDriver() != null)
            new DriverFactory().quit();
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if (scenario.getStatus().equals("SKIPPED")){
            System.out.println("scenario is skipped: "+scenario.getStatus().equals("SKIPPED"));
        }else{
            System.out.println("scenario is skipped: "+scenario.getStatus().equals("SKIPPED"));
        }
        if (scenario.isFailed() || scenario.getStatus().equals("SKIPPED")) {
            List<String> tagName = (List<String>) scenario.getSourceTagNames();
            String filePath = System.getProperty("user.dir")+File.separator+"screenshots";
            String fileName = tagName.get(1);
            Shutterbug.shootPage(DriverFactory.getDriver(), Capture.FULL,true).withName(fileName).save(filePath);
            File file = new File(filePath+File.separator+fileName+".png");
            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                scenario.attach(fileContent, "image/png", fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
