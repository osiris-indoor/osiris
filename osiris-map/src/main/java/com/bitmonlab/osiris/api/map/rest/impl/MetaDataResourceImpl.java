package com.bitmonlab.osiris.api.map.rest.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotBlank;

import com.bitmonlab.core.validations.rest.violationProcessor.RestViolationProcessor;
import com.bitmonlab.osiris.api.map.assemblers.MetaDataAssemblerImpl;
import com.bitmonlab.osiris.api.map.exceptions.MetaDataNotExistsException;
import com.bitmonlab.osiris.api.map.managers.impl.MetaDataManagerImpl;
import com.bitmonlab.osiris.api.map.rest.api.MetaDataResource;
import com.bitmonlab.osiris.api.map.transferobject.MetaDataDTO;
import com.bitmonlab.commons.api.map.model.geojson.MetaData;
import com.bitmonlab.core.assembler.AssemblyException;
import com.bitmonlab.core.validations.validator.Validations;
import com.bitmonlab.core.validations.annotations.ValidationRequired;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api("/osiris/geolocation/territory/map/metadata")
@Path("/osiris/geolocation/territory/map/metadata")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Named
public class MetaDataResourceImpl implements MetaDataResource {
	
	@Inject
	private MetaDataManagerImpl metaDataManagerImpl;
	
	@Inject
	private MetaDataAssemblerImpl metaDataAssemblerImpl;
	
	@Inject
	@Named("validationsOsirisMap")
	private Validations validations;
	
	@Override
	@GET
	@ValidationRequired(processor = RestViolationProcessor.class)
	@ApiOperation(value = "Get metadata of map", httpMethod="GET", response=MetaDataDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Metadata of map was found", response=MetaDataDTO.class),
			@ApiResponse(code = 400, message = "Invalid input parameter (header)"),
			@ApiResponse(code = 400, message = "Metadata of map was not found")})			 
	public Response getMetaData(
			@ApiParam(value = "Application identifier", required = true) @NotBlank @NotNull @HeaderParam("api_key") String appIdentifier) throws AssemblyException, MetaDataNotExistsException {
		// TODO Auto-generated method stub		
		validations.checkIsNotNullAndNotBlank(appIdentifier);
		MetaData metaData = metaDataManagerImpl.getMetaData(appIdentifier);
		MetaDataDTO metaDataDTO=metaDataAssemblerImpl.createDataTransferObject(metaData);
		return Response.ok(metaDataDTO).build();
		
	}

}
