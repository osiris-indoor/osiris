package com.bitmonlab.core.commons.validations.utils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PartialUpdateDataTypes {
	
	BOOLEAN("Boolean"),
	STRING("String"),
	INTEGER("Integer"),
	DOUBLE("Double"),
	LONG("Long");
	
	private String type;
	private static final Map<String,PartialUpdateDataTypes> lookUp = new HashMap<String,PartialUpdateDataTypes>();
	
	static {
		for(PartialUpdateDataTypes partialUpdateDataTypes : EnumSet.allOf(PartialUpdateDataTypes.class))
			lookUp.put(partialUpdateDataTypes.getCode(), partialUpdateDataTypes);
	}
	
	private PartialUpdateDataTypes(String type){
		this.type = type;
	}
	
	public String getCode(){
		return type;
	}
	
	public static PartialUpdateDataTypes getType(final String code){
		if (lookUp.containsKey(code))
			return lookUp.get(code);
		throw new IllegalStateException();
	}
	
 	
	public static Object getCastValue(String code, String value){
		
		switch(PartialUpdateDataTypes.getType(code)){
		case BOOLEAN:
			return Boolean.parseBoolean(value);
			
		case DOUBLE:
			return Double.parseDouble(value);
			
		case INTEGER:
			return Integer.parseInt(value);
			
		case LONG:
			return Long.parseLong(value);
			
		case STRING:
			return value;
		
		}
		
		throw new IllegalStateException("ObjectMapper -> className. NotSupported.");
	} 
	
};
