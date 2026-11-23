package com.becoder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.becoder.dto.UserDto;
import com.becoder.dto.UserReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name="User authentication",description="all the user authentication api")
@RequestMapping("/api/v1/user")
public interface AuthEndpoint {

	
	@Operation(summary="user register endpoint",tags= {"Auth"},description="this is for extra info")
	@PostMapping("/save")
	public ResponseEntity<?> register(@RequestBody UserDto userDto,HttpServletRequest req) throws Exception;
	
	@Operation(summary="user login endpoint",tags= {"Auth"},description="this is for extra info")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserReq userreq) throws Exception;
	
	@Operation(summary="get logged inuser endpoint",tags= {"Auth"},description="this is for extra info")
	@GetMapping("/logged-in-user")
	public ResponseEntity<?> loggedIn() throws Exception;
}
