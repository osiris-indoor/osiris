package com.bitmonlab.osiris.core.errorhandler;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class RestErrors {
	private static final String BUNDLE_NAME = "rest-errors"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private RestErrors() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	public static Enumeration<String> getKeys(){
		return RESOURCE_BUNDLE.getKeys();
	}
}
