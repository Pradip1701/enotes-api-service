package com.becoder.service;

import com.becoder.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	
	public Boolean register(UserDto userDto,String url) throws Exception;

}
