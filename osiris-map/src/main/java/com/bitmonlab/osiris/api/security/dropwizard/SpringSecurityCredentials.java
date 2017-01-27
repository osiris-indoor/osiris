package com.bitmonlab.osiris.api.security.dropwizard;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

public class SpringSecurityCredentials {

	private String username;
	private String password;
	private String api_key;
	
	public SpringSecurityCredentials(String username, String password, String api_key) {
		this.username = checkNotNull(username);
		this.password = checkNotNull(password);
		this.api_key = checkNotNull(api_key);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final SpringSecurityCredentials other = (SpringSecurityCredentials) obj;

		return Objects.equal(this.getUsername(), other.getUsername()) && 
				Objects.equal(this.getPassword(), other.getPassword()) &&
				Objects.equal(this.getApi_key(), other.getApi_key());
		
	}
	
	@Override
	public int hashCode() {
		 return Objects.hashCode(
				 this.getUsername(), this.getPassword(), this.getApi_key());
	}

	@Override
	public String toString() {
		 return Objects.toStringHelper(this)
				 .add("username", this.getPassword())
				 .toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	
	

}
