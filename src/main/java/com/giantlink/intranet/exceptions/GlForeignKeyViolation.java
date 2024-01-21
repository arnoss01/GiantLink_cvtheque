package com.giantlink.intranet.exceptions;

public class GlForeignKeyViolation extends Exception {


	public GlForeignKeyViolation(String entityNameAttribut) {
		super("You can't delete " + entityNameAttribut + " foreignkey violation !");
	}
}
