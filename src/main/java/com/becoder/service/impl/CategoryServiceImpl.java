package com.becoder.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.repository.CategoryRepository;
import com.becoder.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	//for model mapper we also have dependency this is not in springintilizer we have to take it from goggle
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//		Category category=new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
//		
		Category category=mapper.map(categoryDto,Category.class);
		
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category saveCategory=categoryRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	
	@Override
	public List<CategoryDto> getAll() {
		List<Category> categorylist=categoryRepository.findAll();
		
		List<CategoryDto> categoryDtoList=categorylist.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		return categoryDtoList;
	}


	@Override
	public List<CategoryResponse> getActiveAll() {
		List<Category> categorylist=categoryRepository.findByIsActiveTrue();
		
		List<CategoryResponse> categoryResponseList=categorylist.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		return categoryResponseList;
	}

}
