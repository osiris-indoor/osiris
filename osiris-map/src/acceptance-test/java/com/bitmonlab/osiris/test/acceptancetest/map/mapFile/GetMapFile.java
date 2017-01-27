package com.bitmonlab.osiris.test.acceptancetest.map.mapFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.bitmonlab.osiris.test.acceptancetest.map.commons.HttpResponse;
import com.bitmonlab.osiris.restsender.ClientResponse;
import com.bitmonlab.osiris.restsender.Headers;
import com.bitmonlab.osiris.restsender.RestRequestSender;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class GetMapFile {
	
	@Inject
	@Named("osirisMapRequestSender")
	private RestRequestSender sender;
	
	@Inject
	private HttpResponse httpResponse;
	
	private ClientResponse<InputStream> response;
	
	private String collectionNameMap = "files_map";
	
	@Inject 
	@Named("osirisGeolocationMongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Given("^I have a map file with APPID \"([^\"]*)\"$")
	public void I_a_map_with_APPID(String appId) throws IOException{
	    // Express the Regexp above with the code you wish you had
		File mapFile = new File("src/acceptance-test/resources/maps/background_" + appId +".map");
		GridFS gridFS = getGridFS(collectionNameMap);
		removeFile(appId,gridFS);		
		saveFile(appId,mapFile,gridFS);
		
	}
	
	@When("^I invoke a GET to \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\" to get .map file$")
	public void I_invoke_a_GET_to_and_applicationIdentifier_to_get_map_file(String url, String appIdentifier) throws Throwable {
	    // Express the Regexp above with the code you wish you had
		response  = sender.download(url,"application/octet-stream", "application/octet-stream",  new Headers("api_key", appIdentifier), new Headers("Authorization", "Basic cm9vdDoxMjM0"));		
		httpResponse.setResponse(response);
	}
	
	private GridFS getGridFS(String collectionName){
		DB db=mongoTemplate.getDb();		
		GridFS gridFS = new GridFS(db, collectionName);
		return gridFS;
	}
	
	private void removeFile(String appIdentifier,GridFS gridFS){
		GridFSDBFile gridFSFileRemoveFile = gridFS.findOne(appIdentifier);
		if(gridFSFileRemoveFile!=null){
			gridFS.remove(gridFSFileRemoveFile);
		}
	}
	
	private void saveFile(String appIdentifier, File file, GridFS gridFS) throws IOException{
		GridFSInputFile gridFSInputFile = gridFS.createFile(file);
		gridFSInputFile.setFilename(appIdentifier);
		gridFSInputFile.save();
	}	

}
