package com.becoder.dto;

import java.util.List;

import com.becoder.dto.UserDto.RoleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

	private String to;
	
	private String subject;
	
	private String message;
	
	private String title;
}
