//package com.becoder.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
////@ControllerAdvice: a class that watches all controllers. If any controller throws an exception, this class can 
////handle it. That’s why it’s called a global exception handler: one place for all error handling, so you don’t write
////try/catch in every method.
//
////Can you limit the advice to certain controllers? Yes: @ControllerAdvice(basePackages = "com.becoder.controller").
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//	
//	
//	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<?> handleException(Exception e)
//	{
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
//	//@ExceptionHandler: says “when this exception happens, run this method.” The method decides the response 
//	//(message and HTTP status).
//	
//	@ExceptionHandler(NullPointerException.class)
//	public ResponseEntity<?> handleNullPointerException(Exception e)
//	{
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<?> handleResourceNotFoundException(Exception e)
//	{
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//	}
//}
