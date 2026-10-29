package com.becoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.UserDto;
import com.becoder.service.UserService;
import com.becoder.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> register(@RequestBody UserDto userDto,HttpServletRequest req) throws Exception
	{
		String url=CommonUtil.getUrl(req);
		Boolean isSave=userService.register(userDto,url);
		if(isSave)
		{
		return CommonUtil.createBuildResponeMessage("registration success",HttpStatus.CREATED);
		}
		else
		return CommonUtil.createErrorResponeMessage("registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
	
}
