package com.becoder.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {

	private UserDto userDto;
	
	private String token;
}
