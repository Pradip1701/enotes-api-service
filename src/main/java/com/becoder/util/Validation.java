package com.becoder.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.TodoDto;
import com.becoder.dto.TodoDto.StatusDto;
import com.becoder.entity.Category;
import com.becoder.enums.TodoStatus;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.exception.ValidationException;

@Component
public class Validation
{
	
	public void categoryValidation(CategoryDto categoryDto)
	{
		
		Map<String,Object> error=new LinkedHashMap();
		
		if(ObjectUtils.isEmpty(categoryDto))
		{
			throw new IllegalArgumentException("json object shoudn't be null/empty");
		}
		else
		{
			
			//Validation nName field
			if(ObjectUtils.isEmpty(categoryDto.getName()))
			{
				error.put("name", "name field is empty or null");
			}
			else
			{
				if(categoryDto.getName().length()<3)
				{
					error.put("name", "name length min 3");
				}
				if(categoryDto.getName().length()>100)
				{
					error.put("name", "name length max 100");
				}
			}
			
			//Validation Description
			if(ObjectUtils.isEmpty(categoryDto.getDescription()))
			{
				error.put("description", "description field is empty or null");
			}
			
			
			//Validation IsActive
			if(ObjectUtils.isEmpty(categoryDto.getIsActive()))
			{
				error.put("isactive", "isactive field is empty or null");
			}
			else
			{
				if(categoryDto.getIsActive()!=Boolean.TRUE && categoryDto.getIsActive()!=Boolean.FALSE)
				{
					error.put("isactive", "isactive field is emtpy or null");
				}
			}
		}
		
		
		if(!error.isEmpty())
		{
			throw new ValidationException(error);
		}
		
	}
	
	
	public void todoValidation(TodoDto todoDto) throws Exception
	{
		StatusDto reqStatus=todoDto.getStatus();
		
		Boolean Statusfound=false;
		
		for(TodoStatus tds:TodoStatus.values())
		{
			if(tds.getId()==reqStatus.getId())
			{
				Statusfound=true;
			}
		}
		
		if(!Statusfound)
		{
			throw new ResourceNotFoundException("not valid status");
		}
	}

}
