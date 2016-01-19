package com.bitmonlab.core.commons.validations.utils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AuthProvidersNames {
	FACEBOOK("facebook"),
	INTERNAL_TOKEN("internalToken"),
	EMAIL("email");
	
	private String type;
	private static final Map<String,AuthProvidersNames> lookUp = new HashMap<String,AuthProvidersNames>();
	
	static {
		for(AuthProvidersNames deviceType : EnumSet.allOf(AuthProvidersNames.class))
			lookUp.put(deviceType.getCode(), deviceType);
	}
	
	private AuthProvidersNames(String type){
		this.type = type;
	}
	
	public String getCode(){
		return type;
	}
	
	public static AuthProvidersNames getType(final String code){
		if (lookUp.containsKey(code))
			return lookUp.get(code);
		throw new IllegalStateException(lookUp.toString());
	}
};
