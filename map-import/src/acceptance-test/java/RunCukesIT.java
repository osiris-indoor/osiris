import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "pretty", "html:target/cucumber-html-report" }
, glue = { "com.bitmonlab.batch.test.acceptancetest.imports.map" }
,features={

})

public class RunCukesIT {

}



