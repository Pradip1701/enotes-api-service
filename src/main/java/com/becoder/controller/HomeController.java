package com.becoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.service.HomeService;
import com.becoder.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController 
{

	@Autowired
	private HomeService homeSer;
	
	@GetMapping("/verify")
	public ResponseEntity<?> register(@RequestParam Integer uid,@RequestParam String code) throws Exception
	{
		 Boolean isVerify=homeSer.verifyUser(uid, code);
		if(isVerify)
		{
		return CommonUtil.createBuildResponeMessage("account verification success",HttpStatus.OK);
		}
		else
		return CommonUtil.createErrorResponeMessage("invalid verification link",HttpStatus.BAD_REQUEST);
	}
}
