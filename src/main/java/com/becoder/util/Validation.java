package com.becoder.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.becoder.Constants;
import com.becoder.dto.CategoryDto;
import com.becoder.dto.TodoDto;
import com.becoder.dto.TodoDto.StatusDto;
import com.becoder.dto.UserDto;
import com.becoder.dto.UserDto.RoleDto;
import com.becoder.entity.Category;
import com.becoder.enums.TodoStatus;
import com.becoder.exception.ExistDataException;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.exception.ValidationException;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.UserRepository;
import com.becoder.entity.Role;

@Component
public class Validation
{
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private UserRepository userRepo;
	
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


	public void UserValidation(UserDto userDto) {
		
		if(!StringUtils.hasText(userDto.getFirstName()))
		{
			throw new IllegalArgumentException("firstname is not valid");
		}
		if(!StringUtils.hasText(userDto.getLastName()))
		{
			throw new IllegalArgumentException("lastname is not valid");
		}
		if(!StringUtils.hasText(userDto.getEmail()) && userDto.getEmail().matches(Constants.EMAIL_REGEX))
		{
			throw new IllegalArgumentException("email is not valid");
		}
		else {
			//existing email validaiton
			 Boolean b=userRepo.existsByEmail(userDto.getEmail());
			if(b)
			{
				throw new ExistDataException("email already registerd");
			}
		}
		
		
		if(!StringUtils.hasText(userDto.getMobNo()) && userDto.getMobNo().matches(Constants.MOB_REGEX))
		{
			throw new IllegalArgumentException("mobileno is not valid");
		}
		
		if(CollectionUtils.isEmpty(userDto.getRole()))
		{
			throw new IllegalArgumentException("provide the roles");
		}
		else
		{
			List<Integer> allRole=roleRepo.findAll().stream().map(all -> all.getId()).toList();
			
			List<Integer> reqRole=userDto.getRole().stream()
					                               .map(r->r.getId())
					                               .filter(r->!allRole.contains(r))
					                               .toList();
			if(!CollectionUtils.isEmpty(reqRole))
			{
				throw new IllegalArgumentException("provide the roles");
			}

		}
		
		
		
	}

}
