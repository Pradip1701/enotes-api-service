package com.becoder.service;

import com.becoder.exception.ResourceNotFoundException;

public interface HomeService {
	
	public Boolean verifyUser(Integer id,String code) throws ResourceNotFoundException;

}
