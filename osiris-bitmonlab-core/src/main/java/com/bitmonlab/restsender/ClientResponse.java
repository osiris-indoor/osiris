package com.bitmonlab.restsender;

import com.sun.jersey.api.client.GenericType;

public class ClientResponse<T> extends SimpleClientResponse {

	private T entity;

	public ClientResponse(com.sun.jersey.api.client.ClientResponse response, Class<T> theClass) {
		super(response);

		try {
			entity = response.getEntity(theClass);
		} catch (Exception e) {
			entity = null;
		}
	}

	public ClientResponse(com.sun.jersey.api.client.ClientResponse response, GenericType<T> genericType) {
		super(response);

		try {
			entity = response.getEntity(genericType);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			entity = null;
		}

	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

}
