package com.bitmonlab.osiris.api.map.rest.impl;


import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotBlank;

import com.bitmonlab.core.validations.rest.violationProcessor.RestViolationProcessor;
import com.bitmonlab.osiris.api.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.map.managers.api.FeatureManager;
import com.bitmonlab.osiris.api.map.rest.api.FeatureResource;
import com.bitmonlab.osiris.api.map.transferobject.FeatureDTO;
import com.bitmonlab.commons.api.map.model.geojson.Feature;
import com.bitmonlab.core.assembler.Assembler;
import com.bitmonlab.core.assembler.AssemblyException;
import com.bitmonlab.core.validations.validator.Validations;
import com.bitmonlab.core.validations.annotations.ValidationRequired;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api("/osiris/geolocation/territory/feature")
@Path("/osiris/geolocation/territory/feature")
@Named
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class FeatureResourceImpl implements FeatureResource{	  
		
	@Inject
	private FeatureManager featureManager;

	@Inject 
	@Named("FeatureAssembler")
	private Assembler<FeatureDTO, Feature> featureAssembler;
	
	@Inject
	@Named("validationsOsirisMap")
	private Validations validations;

	@Override
	@POST		
	@ValidationRequired(processor = RestViolationProcessor.class)
	@ApiOperation(value = "Store a feature", httpMethod="POST", response=FeatureDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Feature was stored", response=FeatureDTO.class),
			@ApiResponse(code = 400, message = "Latitude range out of index"),
			@ApiResponse(code = 400, message = "Longitude range out of index"),
			@ApiResponse(code = 400, message = "Geometry is invalid"),
			@ApiResponse(code = 400, message = "Mongo GeoJSON format is not correct"),
			@ApiResponse(code = 400, message = "Invalid input parameter (header)")})
	public Response storeFeature(
			@ApiParam(value = "Application identifier", required = true) @NotBlank @NotNull @HeaderParam("api_key") String appIdentifier,
			@ApiParam(required=true, value="Feature") @Valid @NotNull FeatureDTO featureDTO) throws AssemblyException, MongoGeospatialException {
		validations.checkIsNotNullAndNotBlank(appIdentifier);
		validations.checkIsNotNull(featureDTO);
		Feature feature = featureManager.storeFeature(appIdentifier, featureAssembler.createDomainObject(featureDTO));		
		FeatureDTO featureResponseDTO=featureAssembler.createDataTransferObject(feature);		
		return Response.ok(featureResponseDTO).build();
	}

	@Override
	@Path("/{idFeature}")
	@DELETE
	@ValidationRequired(processor = RestViolationProcessor.class)
	@ApiOperation(value = "Delete a feature", httpMethod="DELETE")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Feature was deleted"),
			@ApiResponse(code = 400, message = "Invalid input parameter (header)"),
			@ApiResponse(code = 404, message = "Feature was not found")})
	public Response deleteFeature(
			@ApiParam(value = "Application identifier", required = true) @NotBlank @NotNull @HeaderParam("api_key") String appIdentifier, 
			@ApiParam(required=true, value="Feature identifier") @NotBlank @NotNull @PathParam("idFeature") String idFeature) throws FeatureNotExistException {
		// TODO Auto-generated method stub
		validations.checkIsNotNullAndNotBlank(appIdentifier,idFeature);
		featureManager.deleteFeature(appIdentifier, idFeature);
		return Response.noContent().build();
	}
		
	@Override
	@Path("/{idFeature}")
	@PUT
	@ValidationRequired(processor = RestViolationProcessor.class)
	@ApiOperation(value = "Update a feature", httpMethod="PUT", response=FeatureDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Feature was updated", response=FeatureDTO.class),
			@ApiResponse(code = 400, message = "Latitude range out of index"),
			@ApiResponse(code = 400, message = "Longitude range out of index"),
			@ApiResponse(code = 400, message = "Geometry is invalid"),
			@ApiResponse(code = 400, message = "Mongo GeoJSON format is not correct"),
			@ApiResponse(code = 400, message = "Invalid input parameter (header)"),
			@ApiResponse(code = 404, message = "Feature was not found")})
	public Response updateFeature(
			@ApiParam(value = "Application identifier", required = true) @NotBlank @NotNull @HeaderParam("api_key") String appIdentifier, 
			@ApiParam(required=true, value="Feature identifier") @NotBlank @NotNull @PathParam("idFeature") String idFeature, 
			@ApiParam(required=true, value="Updated feature") @Valid @NotNull FeatureDTO featureDTO) throws AssemblyException, FeatureNotExistException, MongoGeospatialException {
		// TODO Auto-generated method stub	
		 validations.checkIsNotNullAndNotBlank(appIdentifier,idFeature);
		 validations.checkIsNotNull(featureDTO);
		 Feature featureUpdate = featureManager.updateFeature(appIdentifier, idFeature, featureAssembler.createDomainObject(featureDTO));
		 FeatureDTO featureUpdateDTO=featureAssembler.createDataTransferObject(featureUpdate);
		 return Response.ok(featureUpdateDTO).build();
	}

	@Override
	@Path("/{idFeature}")
	@GET
	@ValidationRequired(processor = RestViolationProcessor.class)
	@ApiOperation(value = "Get a feature by id", httpMethod="GET", response=FeatureDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Feature was found", response=FeatureDTO.class),
			@ApiResponse(code = 400, message = "Invalid input parameter (header)"),
			@ApiResponse(code = 404, message = "Feature was not found")})
	public Response getFeatureByID(
			@ApiParam(value = "Application identifier", required = true) @NotBlank @NotNull @HeaderParam("api_key") String appIdentifier, 
			@ApiParam(required=true, value="Feature identifier") @NotBlank @NotNull @PathParam("idFeature") String idFeature) throws AssemblyException, FeatureNotExistException {
		// TODO Auto-generated method stub
		validations.checkIsNotNullAndNotBlank(appIdentifier,idFeature);
		Feature feature = featureManager.getFeatureByID(appIdentifier,idFeature);
		FeatureDTO featureDTO=featureAssembler.createDataTransferObject(feature);
		return Response.ok(featureDTO).build();
	}

}
