package com.becoder.service;

import java.util.List;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;

public interface CategoryService {
	
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;

	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAll();

	public List<CategoryResponse> getActiveAll();

	public Boolean deleteCategoryById(Integer id);

}
