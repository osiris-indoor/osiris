package com.bitmonlab.core.errorhandler;

import java.io.Serializable;

import javax.ws.rs.core.Response.Status;

public class DataException implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public DataException(){
		super();
	}
			
	public DataException(Integer code, Status status, String description) {
		super();
		this.code = code;
		this.status = status;
		this.description = description;
	}

	private Integer code;
	
	private Status status;
	
	private String description;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataException other = (DataException) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataException [code=" + code + ", status=" + status
				+ ", description=" + description + "]";
	}
	
}
