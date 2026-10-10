package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.service.CategoryService;

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
			return new ResponseEntity<>("saved success",HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("/category")
	public ResponseEntity<?> getAll()
	{
		List<CategoryDto> list=categoryService.getAll();
		
		if(CollectionUtils.isEmpty(list)) {
			return ResponseEntity.noContent().build();
		}
		else
		{
			return new ResponseEntity<>(list,HttpStatus.OK);
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
	

}
