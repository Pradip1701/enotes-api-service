package com.becoder.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.becoder.handler.GenericResponse;

public class CommonUtil {

	public static ResponseEntity<?> createBuildRespone(Object data,HttpStatus status)
	{
		GenericResponse response=GenericResponse.builder()
				                .responseStatus(status)
				                .status("succes")
				                .message("succes")
				                .data(data)
				                .build();
		              return response.create();
	}
	
	
	public static ResponseEntity<?> createBuildResponeMessage(String message,HttpStatus status)
	{
		GenericResponse response=GenericResponse.builder()
				                .responseStatus(status)
				                .status("succes")
				                .message(message)
				                .build();
		              return response.create();
	}
	
	public static ResponseEntity<?> createErrorRespone(Object data,HttpStatus status)
	{
		GenericResponse response=GenericResponse.builder()
				                .responseStatus(status)
				                .status("failed")
				                .message("failed")
				                .build();
		             return response.create();
	}
	
	
	public static ResponseEntity<?> createErrorResponeMessage(String message,HttpStatus status)
	{
		GenericResponse response=GenericResponse.builder()
				                .responseStatus(status)
				                .status("failed")
				                .message(message)
				                .build();
		            return response.create();
	}
}
