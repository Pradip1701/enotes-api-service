package com.becoder.service;

import com.becoder.dto.UserDto;
import com.becoder.dto.UserReq;
import com.becoder.dto.UserResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	
	public Boolean register(UserDto userDto,String url) throws Exception;

	public UserResponse login(UserReq userreq);

}
