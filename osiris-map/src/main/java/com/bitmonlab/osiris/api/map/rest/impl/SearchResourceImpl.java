package com.bitmonlab.osiris.api.map.rest.impl;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.core.map.exceptions.RoomNotFoundException;
import com.bitmonlab.osiris.api.core.map.managers.api.SearchManager;
import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.LayerDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.RoomDTO;
import com.bitmonlab.osiris.api.map.rest.api.SearchResource;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.core.assembler.Assembler;
import com.bitmonlab.osiris.core.assembler.AssemblyException;
import com.bitmonlab.osiris.core.validations.annotations.ValidationRequired;
import com.bitmonlab.osiris.core.validations.rest.violationProcessor.RestViolationProcessor;
import com.bitmonlab.osiris.core.validations.validator.Validations;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api("/osiris/geolocation/territory/search")
@Path("/osiris/geolocation/territory/search")
@Named
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class SearchResourceImpl implements SearchResource{
	
	@Inject
	private SearchManager searchManager;

	@Inject 
	@Named("FeatureAssembler")
	private Assembler<FeatureDTO, Feature> featureAssembler;
	
	@Inject 
	@Named("RoomAssembler")
	private Assembler<RoomDTO, Feature> roomAssembler;
	
	@Inject
	@Named("validationsOsirisMap")
	private Validations validations;
	
	@Override		
	@POST	
	@ValidationRequired(processor = RestViolationProcessor.class)
	@ApiOperation(value = "Get features according to query", httpMethod="POST", response=FeatureDTO.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Features were found", response=FeatureDTO.class),
			@ApiResponse(code = 400, message = "Invalid input parameter (header)"),
			@ApiResponse(code = 400, message = "Query is not correct")})
	public Response getFeaturesByQuery(
			@ApiParam(value = "Application identifier", required = true) @NotBlank @NotNull @HeaderParam("api_key") String appIdentifier, 
			@ApiParam(value = "Query", required = true) @NotBlank @NotNull String query, 
			@ApiParam(required=false,value="Layer",allowableValues="ALL,MAP,FEATURES",defaultValue="ALL") @QueryParam("layer") @DefaultValue("ALL") LayerDTO layer,
			@ApiParam(required=false,value="Index of page",defaultValue="0") @QueryParam("pageIndex") @DefaultValue("0") Integer pageIndex, 
			@ApiParam(required=false,value="Size of page",defaultValue="20") @QueryParam("pageSize") @DefaultValue("20") Integer pageSize,
			@ApiParam(required=false,value="Order field") @QueryParam("orderField") String orderField, 
			@ApiParam(required=false,value="Order",allowableValues="ASC,DESC") @DefaultValue("ASC") @QueryParam("order") String order) throws AssemblyException, QueryException {
						
		validations.checkIsNotNullAndNotBlank(appIdentifier,query);
		validations.checkIsNotNull(layer);
		validations.checkMin(0, pageIndex);
		validations.checkMin(1, pageSize);
		
		Collection<Feature>  features = null;
		
		if(StringUtils.isEmpty(orderField)){
			features =  searchManager.getFeaturesByQuery(appIdentifier, query, layer, pageIndex, pageSize);				
		}else{
			features =  searchManager.getFeaturesByQuery(appIdentifier, query, layer, pageIndex, pageSize, orderField, order);
		}
		
		Collection<FeatureDTO> collectionFeatureDTO=featureAssembler.createDataTransferObjects(features);
		return Response.ok(collectionFeatureDTO).build();
	}	
	
	
	@Override			
	@Path("/room")
	@GET
	@ValidationRequired(processor = RestViolationProcessor.class)
	@ApiOperation(value = "Get room according to indoor location", httpMethod="GET",response=RoomDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Room belongs to location", response=RoomDTO.class),
			@ApiResponse(code = 400, message = "Invalid input parameter"),
			@ApiResponse(code = 404, message = "Room not found"),
			@ApiResponse(code = 500, message = "Problem in the system")})
	public Response getRoomByLocation(
			@ApiParam(value = "Application identifier", required = true) @NotBlank @NotNull @HeaderParam("api_key") String appIdentifier, 			
			@ApiParam(value="Longitude of location", required=true) @Min(-180) @Max(180) @NotNull @QueryParam("longitude") Double longitude,
			@ApiParam(value="Latitude of location", required=true) @Min(-90) @Max(90) @NotNull @QueryParam("latitude") Double latitude,
			@ApiParam(value = "Floor of location", required = true) @NotNull  @QueryParam("floor") Integer floor) throws AssemblyException, RoomNotFoundException{
		validations.checkIsNotNullAndNotBlank(appIdentifier);
		validations.checkMin(-180.0, longitude);
		validations.checkMax(180.0, longitude);
		validations.checkMin(-90.0, latitude);
		validations.checkMax(90.0, latitude);
		validations.checkIsNotNull(floor);
		
		Feature room=searchManager.getRoomByLocation(appIdentifier, longitude, latitude, floor);			
		RoomDTO roomDTO=roomAssembler.createDataTransferObject(room);				
		return Response.ok(roomDTO).build();		
	}
	
	


}
