package com.becoder.exception;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice: a class that watches all controllers. If any controller throws an exception, this class can 
//handle it. That’s why it’s called a global exception handler: one place for all error handling, so you don’t write
//try/catch in every method.

//Can you limit the advice to certain controllers? Yes: @ControllerAdvice(basePackages = "com.becoder.controller").

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e)
	{
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//@ExceptionHandler: says “when this exception happens, run this method.” The method decides the response 
	//(message and HTTP status).
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e)
	{
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e)
	{
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	
	//this is for predefined validation purpuse
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
//	{
//		List<ObjectError> allErrors=e.getBindingResult().getAllErrors();
//		
//		Map<String,Object> error=new LinkedHashMap();
//		allErrors.stream().forEach(er -> {
//		String msg=er.getDefaultMessage();
//		String field=((FieldError)(er)).getField();
//		
//		error.put(field, msg);
//		});
//		
//		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
//	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e)
	{
		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e)
	{
		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e)
	{
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
}
