package com.bitmonlab.osiris.commons.model.security;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class BasicAuth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7103315600939610068L;
	
	@Id private String username;
	private String password;
	
		
	public BasicAuth() {
	}
	public BasicAuth(String username) {
		this.username = username;
	}
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return username;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
}