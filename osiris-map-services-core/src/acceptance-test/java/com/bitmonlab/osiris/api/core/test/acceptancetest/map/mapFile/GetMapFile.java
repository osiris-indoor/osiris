package com.bitmonlab.osiris.api.core.test.acceptancetest.map.mapFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;

import junit.framework.Assert;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.bitmonlab.osiris.api.core.map.exceptions.MapFileNotExistsException;
import com.bitmonlab.osiris.api.core.map.managers.api.MapFileManager;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GetMapFile {
	
	@Inject
	private MapFileManager mapFileManager;
		
	private InputStream response;
	
	private String collectionNameMap = "files_map";
	
	public static Exception exceptionCapture;
	
	@Inject 
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Given("^I have a map file with appId \"([^\"]*)\"$")
	public void I_a_map_with_appId(String appId) throws IOException{
	    // Express the Regexp above with the code you wish you had		
			File mapFile = new File("src/acceptance-test/resources/maps/background_" + appId +".map");
			GridFS gridFS = getGridFS(collectionNameMap);
			removeFile(appId,gridFS);		
			saveFile(appId,mapFile,gridFS);				
	}
	
	@When("^I invoke a GET to getMapFile and applicationIdentifier \"([^\"]*)\" to get .map file$")
	public void I_invoke_a_GET_to_getMapFile_and_applicationIdentifier_to_get_map_file(String appIdentifier) throws Throwable {
	    // Express the Regexp above with the code you wish you had		
		try{
			response=mapFileManager.getMapFile(appIdentifier);	
		}catch (Exception e){			
			exceptionCapture = e;
		}
	}
	
	@Then("^I receive a MapFileNotExistsException$")
	public void I_receive_a_MapFileNotExistsException() throws Throwable {
	    // Express the Regexp above with the code you wish you had
		Assert.assertEquals(exceptionCapture.getClass() , new MapFileNotExistsException().getClass() );
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
