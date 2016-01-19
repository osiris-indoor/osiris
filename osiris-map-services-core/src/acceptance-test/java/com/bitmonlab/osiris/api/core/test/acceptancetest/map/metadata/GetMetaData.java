package com.bitmonlab.osiris.api.core.test.acceptancetest.map.metadata;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.bitmonlab.osiris.core.assembler.AssemblyException;
import com.bitmonlab.osiris.api.core.map.assemblers.MetaDataAssemblerImpl;
import com.bitmonlab.osiris.api.core.map.exceptions.MetaDataNotExistsException;
import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.core.map.managers.impl.MetaDataManagerImpl;
import com.bitmonlab.osiris.api.core.map.transferobject.MetaDataDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.MetaData;
import com.bitmonlab.osiris.restsender.RestRequestSender;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GetMetaData {
	
	
	@Inject 
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Inject
	private MetaDataManagerImpl metaDataManagerImpl;
	
	@Inject
	private MetaDataAssemblerImpl metaDataAssemblerImpl;
	
	private MetaDataDTO response;
	
	private final static String collectionMetaData="Metadata";
	
	public static Exception exceptionCapture;
	
	@Given("^I have a map with appId \"([^\"]*)\" and metadata osmChecksum \"([^\"]*)\", routingChecksum \"([^\"]*)\", minLat \"([^\"]*)\", minLon \"([^\"]*)\", maxLat \"([^\"]*)\" and maxLon \"([^\"]*)\"$")
	public void I_have_a_map_with_appId_and_metadata_osmChecksum_routingChecksum_minLat_minLon_maxLat_and_maxLon(String appId, String osmChecksum, String routingChecksum, String minLat, String minLon, String maxLat, String maxLon){
	    // Express the Regexp above with the code you wish you had
		MetaData metadata=createMetaData(appId, osmChecksum, routingChecksum, minLat, minLon, maxLat, maxLon);
		mongoTemplate.save(metadata, collectionMetaData);
	}

	@When("^I invoke a GET metadata to getMetaData and applicationIdentifier \"([^\"]*)\"$")
	public void I_invoke_a_GET_metadata_to_getMetaData_and_applicationIdentifier(String appId) throws MetaDataNotExistsException, AssemblyException{
	    // Express the Regexp above with the code you wish you had
		
		try{
			MetaData metaData = metaDataManagerImpl.getMetaData(appId);
			response=metaDataAssemblerImpl.createDataTransferObject(metaData);
		}catch (Exception e){			
			exceptionCapture = e;
		}
		
	}

	@Then("^I verify metadata has same fields osmChecksum \"([^\"]*)\", routingChecksum \"([^\"]*)\", minLat \"([^\"]*)\", minLon \"([^\"]*)\", maxLat \"([^\"]*)\" and maxLon \"([^\"]*)\"$")
	public void I_verify_metadata_has_same_fields_osmChecksum_routingChecksum_minLat_minLon_maxLat_and_maxLon(String osmChecksum, String routingChecksum, String minLat, String minLon, String maxLat, String maxLon){
	    // Express the Regexp above with the code you wish you had
		String chkSumResponse = response.getOSMChecksum();
		String minLatResponse = response.getMinLatitude();
		String minLonResponse = response.getMinLongitude();
		String maxLatResponse = response.getMaxLatitude();
		String maxLonResponse = response.getMaxLongitude();
		String checksumRouting = response.getRoutingChecksum();
											
		Assert.assertEquals("Checksum must be the same", osmChecksum, chkSumResponse);	
		Assert.assertEquals("minLat must be the same", minLat, minLatResponse);
		Assert.assertEquals("minLon must be the same", minLon, minLonResponse);
		Assert.assertEquals("maxLat must be the same", maxLat, maxLatResponse);
		Assert.assertEquals("maxLon must be the same", maxLon, maxLonResponse);
		Assert.assertEquals("RoutingChecksum must be the same",routingChecksum, checksumRouting);
	}
	
	@Then("^I receive a MetaDataNotExistsException$")
	public void I_receive_a_MetaDataNotExistsException() throws Throwable {
	    // Express the Regexp above with the code you wish you had
		Assert.assertEquals(exceptionCapture.getClass() , new MetaDataNotExistsException().getClass() );
	}
	
	private MetaData createMetaData(String appId, String osmChecksum, String routingChecksum, String minLat, String minLon, String maxLat, String maxLon){
		MetaData metaData=new MetaData();
		metaData.setAppId(appId);
		metaData.setMaxlat(Double.parseDouble(maxLat));
		metaData.setMaxlon(Double.parseDouble(maxLon));
		metaData.setMinlat(Double.parseDouble(minLat));
		metaData.setMinlon(Double.parseDouble(minLon));
		metaData.setOSMChecksum(osmChecksum);
		metaData.setRoutingChecksum(routingChecksum);
		return metaData;
	}
	
}
