package com.becoder.controller;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
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

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/home")
public class HomeController 
{

	//Logger log=LoggerFactory.getLogger(HomeController.class);  //insted of this just user @Slf4j
	
	@Autowired
	private HomeService homeSer;
	
	@GetMapping("/verify")
	public ResponseEntity<?> register(@RequestParam Integer uid,@RequestParam String code) throws Exception
	{
		 log.info("HomeController : verifyUser : Execution start");
		 Boolean isVerify=homeSer.verifyUser(uid, code);
		if(isVerify)
		{
		return CommonUtil.createBuildResponeMessage("account verification success",HttpStatus.OK);
		}
		else
		return CommonUtil.createErrorResponeMessage("invalid verification link",HttpStatus.BAD_REQUEST);
	}
}
