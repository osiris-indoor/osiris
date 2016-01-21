package com.bitmonlab.osiris.api.map.rest.impl;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotBlank;

import com.bitmonlab.osiris.core.validations.validator.Validations;
import com.bitmonlab.osiris.core.validations.annotations.ValidationRequired;
import com.bitmonlab.osiris.core.validations.rest.violationProcessor.RestViolationProcessor;
import com.bitmonlab.osiris.api.core.map.exceptions.MapFileNotExistsException;
import com.bitmonlab.osiris.api.core.map.managers.api.MapFileManager;
import com.bitmonlab.osiris.api.map.rest.api.MapFileResource;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api("/osiris/geolocation/territory/map/file")
@Path("/osiris/geolocation/territory/map/file")
@Named
public class MapFileResourceImpl  implements MapFileResource{

  @Inject
  private MapFileManager mapFileManager;
  
  @Inject
  @Named("validationsOsirisMap")
  private Validations validations;
  
  @Override
  @GET
  @ValidationRequired(processor = RestViolationProcessor.class)
  @ApiOperation(value = "Get .map file", httpMethod="GET", response=InputStream.class)
  @ApiResponses(value = {
		  @ApiResponse(code = 200, message = ".map file was found", response=InputStream.class),
		  @ApiResponse(code = 400, message = "Invalid input parameter (header)"),
		  @ApiResponse(code = 404, message = ".map file was not found")})
  public Response getMapFile(
		  @ApiParam(value = "Application identifier", required = true) @NotBlank @NotNull @HeaderParam("api_key") String appIdentifier) throws MapFileNotExistsException{
	  validations.checkIsNotNullAndNotBlank(appIdentifier);
	  InputStream mapFile=mapFileManager.getMapFile(appIdentifier);
	  return Response.ok(mapFile).build();
  }	
  
  
}
