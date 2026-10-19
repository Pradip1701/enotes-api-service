package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.service.CategoryService;
import com.becoder.util.CommonUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired 
	private CategoryService categoryService;
	
	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto)
	{
		Boolean b=categoryService.saveCategory(categoryDto);
		
		if(b) 
		{
		  return CommonUtil.createBuildResponeMessage("saved success", HttpStatus.CREATED);//generic response handler
			//return new ResponseEntity<>("saved success",HttpStatus.CREATED);
		}
		else
		{
		  return CommonUtil.createErrorResponeMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);//generic response
			//return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("/category")
	public ResponseEntity<?> getAll()
	{
//		String nm=null;
//		nm.length();
		
		//You never wrote throw anywhere. Java itself throws the NullPointerException. Nothing in getAll() catches it,
		//so it travels up to Spring, and Spring looks in @ControllerAdvice for a matching handler.
		//Two handlers could match:
		//NullPointerException.class: exact match
		//Exception.class: matches because Exception is the parent
		//Spring picks the closest match in the inheritance tree, which is the ResourceNotFoundException handler. 
		//The order of methods in the file doesn’t matter
		
		//without a global handler, Spring Boot uses BasicErrorController and its default /error page. Unhandled custom
		//exceptions come back as 500 with a generic body, so I use @ControllerAdvice to return the right status code 
		//and a meaningful, consistent response.”
		List<CategoryDto> list=categoryService.getAll();
		
		if(CollectionUtils.isEmpty(list)) {
			return ResponseEntity.noContent().build();
		}
		else
		{
			return CommonUtil.createBuildRespone(list, HttpStatus.OK);
			//return new ResponseEntity<>(list,HttpStatus.OK);
		}
	}
	
	
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveAll()
	{
		List<CategoryResponse> list=categoryService.getActiveAll();
		
		if(CollectionUtils.isEmpty(list)) {
			return ResponseEntity.noContent().build();
		}
		else
		{   
			return new ResponseEntity<>(list,HttpStatus.OK);
		}
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws ResourceNotFoundException
	{
		//exception handling: GlobalExceptionHandler class 
	    //1. A local try/catch wins. If your controller has its own try/catch that catches the exception, it never
	    //reaches this class(GlobalExceptionHandler). That’s why the try/catch in your controller made the global
	    //handler pointless for that endpoint. and here we comment out all try catch code bcz try catch not best
		//practice bcz in big project we have to handle exception for every method.so we cant write try catch
		//everytime for each method.and try catch works fine without globalexception handler class
	    
				
//				try {
//					CategoryDto categoryDto=categoryService.getCategoryById(id);
//					
//					if(ObjectUtils.isEmpty(categoryDto)) {
//						return new ResponseEntity<>("details not found with id"+id,HttpStatus.NOT_FOUND);
//					}
//					else
//						return new ResponseEntity<>(categoryDto,HttpStatus.OK);
//					
//				}
//				catch(ResourceNotFoundException e)
//				{
//					return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//				}
		
		
				CategoryDto categoryDto=categoryService.getCategoryById(id);
				
				if(ObjectUtils.isEmpty(categoryDto)) {
					return new ResponseEntity<>("details not found with id"+id,HttpStatus.NOT_FOUND);
				}
				else
					return new ResponseEntity<>(categoryDto,HttpStatus.OK);

			}
	
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id)
	{
		Boolean deleted=categoryService.deleteCategoryById(id);
		if(deleted)
		{
			return new ResponseEntity<>("Category deleted success",HttpStatus.OK);
		}
		return new ResponseEntity<>("Category not delted",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
