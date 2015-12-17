package com.bitmonlab.core.errorhandler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.validation.InvalidEntityException;

/**
 * 
 * Every exception in rest layer is caught by this class to be handled and
 * 'restability' (I mean build rest) the error response, with a description
 * 
 * The flow of this goes as I next describe:
 * 
 * First it load the property file to a map in memory Later find all messages in
 * database and overload the properties values in case there are anyone already
 * in database. After that, The memory map is stored in database. And at the end
 * of the flow, a scheduler load from database the changes every 30 minutes.
 * 
 */
@Provider
public class RestErrorsHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(RestErrorsHandler.class);

    private Map<String, DataException> httpCodes = null;

    public RestErrorsHandler() {
	initializeFromProperties();
    }

    public Response toResponse(Exception exception) {
	try {

	    Class<? extends Exception> exceptionClass = exception.getClass();

	    if (exception instanceof WebApplicationException) {
		WebApplicationException wae = (WebApplicationException) exception;
		return Response.status(wae.getResponse().getStatus()).entity(wae.getResponse().getEntity()).build();
	    } else if (exception instanceof JsonParseException) {
		DataException httpDataException = httpDataException(exception, httpCodes);
		return returnErrorResponse(exception.getMessage(), exceptionClass.getSimpleName(), httpDataException, Status.BAD_REQUEST);
	    } else if (exception instanceof InvalidEntityException) {
		InvalidEntityException invalidEntityException = (InvalidEntityException) exception;
		ImmutableList<String> iList = invalidEntityException.getErrors();
		String exMsg = "";
		for (String errorMsg : iList) {
		    exMsg += errorMsg + ", ";
		}
		exMsg = exMsg.substring(0, exMsg.length() - 2);
		DataException httpDataException = httpDataException(exception, httpCodes);
		return returnErrorResponse(exMsg, exceptionClass.getSimpleName(), httpDataException, Status.BAD_REQUEST);
	    } else {
		DataException httpDataException = httpDataException(exception, httpCodes);
		return returnErrorResponse(exception.getMessage(), exceptionClass.getSimpleName(), httpDataException, null);
	    }

	} catch (Throwable e) {
	    e.printStackTrace();
	    return null;
	}
    }

    private Response returnErrorResponse(String exceptionMessage, String exceptionClassSimpleName, DataException httpDataException, Status status) {
	if (null == status)
	    status = httpDataException.getStatus();
	ErrorResponse error = buildErrorBean(status, httpDataException.getCode(), exceptionClassSimpleName, httpDataException.getDescription(),
		exceptionMessage);
	return Response.status(status.getStatusCode()).entity(error).build();
    }

    public ErrorResponse buildErrorBean(Status status, Integer code, String className, String description, String msg) {
	ErrorResponse error = new ErrorResponse();
	error.setStatus(status.getStatusCode());
	error.setStatusReason(status.getReasonPhrase());
	error.setCode(code);
	error.setException(className);
	error.setDescription(description);
	error.setMessage(msg);
	return error;
    }

    public DataException httpDataException(Exception exception, Map<String, DataException> httpCodes) {
	String className = exception.getClass().getName();
	DataException dataException = httpCodes.get(className);

	if (dataException == null) {
	    httpCodes.put(className, new DataException(httpCodes.keySet().size() + 1, Status.INTERNAL_SERVER_ERROR, ""));
	    dataException = httpCodes.get(className);
	    LOG.error("Application Error", exception);
	} else {
	    LOG.warn("User Error: {}, {}", dataException.getDescription(), exception.getMessage());
	}

	return dataException;
    }

    private void initializeFromProperties() {

	httpCodes = new HashMap<String, DataException>();
	Enumeration<String> allErrorsKeys = RestErrors.getKeys();
	while (allErrorsKeys.hasMoreElements()) {
	    try {
		String exceptionName = allErrorsKeys.nextElement();
		String string = RestErrors.getString(exceptionName);
		String restErrorLineValue[] = string.split(",");
		int code = Integer.parseInt(restErrorLineValue[0]);
		String status = restErrorLineValue[1];
		String description = restErrorLineValue[2];
		System.out.println(exceptionName + ";" + string + ";" + status + ";" + description);
		httpCodes.put(exceptionName, new DataException(code, Status.valueOf(status), description));
	    } catch (Throwable e) {
		e.printStackTrace();
	    }
	}
    }

    public Map<String, DataException> getHttpCodes() {
	return httpCodes;
    }

}
