package com.giantlink.intranet.exceptions;

public class GlNotFoundException extends Exception {


	public GlNotFoundException(String entityNameAttribut, String entityName) {
		super(entityNameAttribut + " " + entityName + " Not Found !");
	}

}