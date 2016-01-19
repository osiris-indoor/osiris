package com.bitmonlab.osiris.restsender;

import java.io.Serializable;

public class BasicAuth implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String password;

	public BasicAuth(String n, String p) {
		this.name = n;
		this.password = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
