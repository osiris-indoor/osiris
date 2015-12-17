package com.bitmonlab.restsender;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

public class SimpleClientResponse {

	private int statusCode;
	
	private MultivaluedMap<String, String> headers;

	private String errorMessage;

	public SimpleClientResponse(com.sun.jersey.api.client.ClientResponse response) {
		statusCode = response.getClientResponseStatus().getStatusCode();

		headers = response.getHeaders();

		if (statusCode == 500) {
			errorMessage = response.getEntity(String.class);
		}

	}

	public SimpleClientResponse(com.sun.jersey.api.client.ClientResponse.Status noContent) {
		statusCode = noContent.getStatusCode();
	}

	public Status getStatus() {
		return Status.fromStatusCode(statusCode);
	}

	public void setStatus(Status status) {
		this.statusCode = status.getStatusCode();
	}

	public MultivaluedMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(MultivaluedMap<String, String> headers) {
		this.headers = headers;
	}

	public String getHeader(String key) {
		return headers.get(key).get(0);
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}