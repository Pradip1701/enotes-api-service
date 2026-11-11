package com.becoder.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoggedInUserRes {

	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String mobNo;
	
	private List<RoleDto> role;
	
	private AccountStatusDto status;
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RoleDto{
		private Integer id;
		
		private String name;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class AccountStatusDto{
		private Integer id;
		
		private Boolean isActive;
	}
}
