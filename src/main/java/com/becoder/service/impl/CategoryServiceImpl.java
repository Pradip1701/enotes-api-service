package com.becoder.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.service.CategoryService;
import com.becoder.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService{

	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	
	//for model mapper we also have dependency this is not in springintilizer we have to take it from goggle
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//		Category category=new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
//		
		
		
		//Validation Checking
		validation.categoryValidation(categoryDto);
		Category category=mapper.map(categoryDto,Category.class);
		
		if(ObjectUtils.isEmpty(category.getId()))
		{
			category.setIsDeleted(false);
			category.setCreatedBy(1);
			category.setCreatedOn(new Date());
		}
		else {
			   updateCategory(category);
		}
		
		Category saveCategory=categoryRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	
	private void updateCategory(Category category) {
		
		Optional<Category> findById=categoryRepository.findById(category.getId());
		if(findById.isPresent())
		{
			Category existingcategory=findById.get();
			category.setCreatedBy(existingcategory.getCreatedBy());
			category.setCreatedOn(existingcategory.getCreatedOn());
			category.setIsDeleted(existingcategory.getIsDeleted());
			category.setUpdatedBy(1);
			category.setUpdatedOn(new Date());

		}
	}


	@Override
	public List<CategoryDto> getAll() {
		List<Category> categorylist=categoryRepository.findByIsDeletedFalse();
		
		List<CategoryDto> categoryDtoList=categorylist.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		return categoryDtoList;
	}


	@Override
	public List<CategoryResponse> getActiveAll() {
		List<Category> categorylist=categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
//		if(categorylist == null)
//    	{
//    		throw new IllegalArgumentException("NAME IS NULL");
//    	}
		//for above our @ExceptionHandler(Exception.class)  this works bcz its parent of every class
		
		List<CategoryResponse> categoryResponseList=categorylist.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		return categoryResponseList;
	}


	@Override
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
        Category category=categoryRepository.findByIdAndIsDeletedFalse(id)
        		.orElseThrow(()->new ResourceNotFoundException("category not found with id"+id));
        if(!ObjectUtils.isEmpty(category))
        {
        	//category.getName().toUpperCase();  for this our nullpointer exception class works
        	return mapper.map(category,CategoryDto.class);
        	
        }
		return null;
	}


	@Override
	public Boolean deleteCategoryById(Integer id) {
		
		 Optional<Category> findByCategory=categoryRepository.findById(id);
	        if(findByCategory.isPresent())
	        {
	        	Category category=findByCategory.get();
	        	category.setIsDeleted(true);
	        	categoryRepository.save(category);
	        	return true;
	        }
		     return false;
	}

}
