import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "pretty", "html:target/cucumber-html-report" }
, glue = { "com.bitmonlab.osiris.api.core.test.acceptancetest.map" }
,features={
		//"src/acceptance-test/resources/map/Feature/deleteFeature.feature"
		//"src/acceptance-test/resources/map/Feature/updateFeature.feature",
		//"src/acceptance-test/resources/map/Feature/storeFeature.feature",		
		//"src/acceptance-test/resources/map/Feature/getFeatureByID.feature",
		//"src/acceptance-test/resources/map/Search/search.feature",
		//"src/acceptance-test/resources/map/mapFile/GetMapFile.feature",
		//"src/acceptance-test/resources/map/Metadata/getMetaData.feature"
				
})

public class RunCukesIT {

}



