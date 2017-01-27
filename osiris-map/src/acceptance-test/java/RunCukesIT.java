import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "pretty", "html:target/cucumber-html-report" }
, glue = { "com.bitmonlab.osiris.test.acceptancetest.map" }
,features={
		//"src/acceptance-test/resources/map/Feature/deleteFeature.feature",
		//"src/acceptance-test/resources/map/Search/search.feature"
		//"src/acceptance-test/resources/map/MetaData/getMetaData.feature",
		//"src/acceptance-test/resources/map/Search/getRoomByLocation.feature"
		//"src/acceptance-test/resources/map/MapFile/getMapFile.feature"
		
		
})

public class RunCukesIT {

}



