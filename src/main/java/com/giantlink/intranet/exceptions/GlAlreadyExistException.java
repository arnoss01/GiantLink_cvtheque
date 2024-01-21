package com.giantlink.intranet.exceptions;

public class GlAlreadyExistException extends Exception {


	public GlAlreadyExistException(String entityNameAttribut, String entityName) {
		super(entityNameAttribut + " " + entityName + " Already exist !");
	}

}
