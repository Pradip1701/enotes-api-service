package com.becoder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.becoder.entity.AccountStatus;
import com.becoder.entity.User;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.UserRepository;
import com.becoder.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService{

	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Boolean verifyUser(Integer id, String code) throws ResourceNotFoundException {
		
        User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not valid"));
        if(user.getAccountStatus().getVerificationCode()==null)
        {
        	throw new IllegalArgumentException("verification link not valid"); 
        }
        if(user.getAccountStatus().getVerificationCode().equals(code))
        {
        	AccountStatus acc=user.getAccountStatus();
        	acc.setIsActive(true);
        	acc.setVerificationCode(null);
        	
        	user.setAccountStatus(acc);
        	userRepo.save(user);
        	return true;
        }
        return false;
        
	}

	
}
