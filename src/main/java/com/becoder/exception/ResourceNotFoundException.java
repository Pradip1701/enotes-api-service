package com.becoder.exception;
//Why NullPointerException needs no class from you NullPointerException is already written by Java (it’s in java.lang).
//Java throws it automatically when you use a null object, so you just write a handler for it.
//ResourceNotFoundException is not built into Java. Java has no idea what “category not found” means in your project.
//It’s a business situation that only you know about. So:


public class ResourceNotFoundException extends Exception{
	
	public ResourceNotFoundException(String message)
	{
		super(message);
	}

}
