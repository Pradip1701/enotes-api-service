package com.becoder.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.UserDto;
import com.becoder.entity.Role;
import com.becoder.entity.User;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.Validation;

@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private RoleRepository roleRepo;
	
	
	@Override
	public Boolean register(UserDto userDto) {
		
		//user validation
		validation.UserValidation(userDto);
		User user=mapper.map(userDto, User.class);
		
		setRole(userDto,user);
		User userSave=userRepo.save(user);
		if(!ObjectUtils.isEmpty(userSave))
		{
			return true;
		}else
			return false;
	}


	private void setRole(UserDto userDto, User user) {
	
		List<Integer> reqrole=userDto.getRole().stream().map(r->r.getId()).toList();
		
		List<Role> role=roleRepo.findAllById(reqrole);
		user.setRole(role);
	}

	
}
