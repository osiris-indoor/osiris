package com.bitmonlab.batch.imports.map;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.bitmonlab.batch.imports.map.managers.api.ImportOSMFileManager;

public class MapImportMain {
	
	private static MongoOperations mapImportMongoOperation;
		
	
	
	public static void main(final String[] pArgs) throws Throwable {
		
				
		String appIdentifier = null;
		File osmFile = null;
		String graphBuilder = null;
		boolean bGraphBuilder = false;
		
		//Workaround to pass acceptance test
		String acceptanceTest = null;
		boolean bAcceptanceTest = false;
						
		if (pArgs.length == 2 || pArgs.length == 3 || pArgs.length==4) {		  
			
		    	appIdentifier = pArgs[0];
		    
		    	String fileName = pArgs[1];
		    	osmFile = new File(fileName);
	            if(!osmFile.exists()){	                 
	            	System.out.println("File " + fileName  + " not found ");
	            	System.exit(-1);
	            }
	        
			  if (pArgs.length == 3 || pArgs.length==4) {		 
				
		            graphBuilder = pArgs[2];
		            if(graphBuilder.equals("yes")){
		            	bGraphBuilder = true;
		            }else{
		            	bGraphBuilder = false;
		            }
		            
		            //Workaround to pass acceptance test
		            if(pArgs.length==4){
		            	if(pArgs[3].equals("acceptance-test")){
		            		bAcceptanceTest = true;
		            	}
		            }
			  }
	            					 
		}else{			
			System.out.println("java -Denv=local -jar map-import.jar AppIdentifier FileOSMFormat");
			System.exit(-1);
		}
		
		ApplicationContext ctx = new GenericXmlApplicationContext("map-import-context.xml");			      	      
		mapImportMongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
				
    	MapImportMain process =  (MapImportMain) ctx.getBean("mapImportMain");
    	
    	
    	ImportOSMFileManager importOSMFileManager =  (ImportOSMFileManager) ctx.getBean("importOSMFileManager"); 		    	
    	importOSMFileManager.importOSMFile(appIdentifier, new FileInputStream(osmFile), bGraphBuilder);	 
    	  
    	//Workaround to pass acceptance test
    	if(!bAcceptanceTest) System.exit(0);
	
	}
	
	

}
