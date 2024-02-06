package exception;

import base.ThreadDriver;

public class ScenarioInfoException extends IllegalStateException{

    public ScenarioInfoException(String error){
        super(
                String.format(ThreadDriver.getDriver() +"\n"+
                        ThreadDriver.getDriver().getCurrentUrl()+"\n"+
                        error)
        );
    }
}
