package com.bitmonlab.osiris.commons.model.security;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class BasicAuth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7103315600939610068L;
	
	@Id private String _id;
	private String password;
	
		
	public BasicAuth() {
	}
	public BasicAuth(String _id) {
		this._id = _id;
	}
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return _id;
	}

	public String get_id() {
		return _id;
	}
	public void setUsername(String _id) {
		this._id = _id;
	}
	
	
	
	
}