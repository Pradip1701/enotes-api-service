package com.becoder.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.EmailRequest;
import com.becoder.dto.UserDto;
import com.becoder.entity.AccountStatus;
import com.becoder.entity.Role;
import com.becoder.entity.User;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.Validation;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private EmailSendService emailService;
	
	
	@Override
	public Boolean register(UserDto userDto,String url) throws Exception {
		
		//user validation
		validation.UserValidation(userDto);
		User user=mapper.map(userDto, User.class);
		
		setRole(userDto,user);
		setStatus(userDto,user);
		User userSave=userRepo.save(user);
		if(!ObjectUtils.isEmpty(userSave))
		{
			sendEmail(userSave,url);
			return true;
		}else
			return false;
	}

	private void setStatus(UserDto userDto, User user) {
       AccountStatus accountStatus=AccountStatus.builder()
    		                                     .isActive(false)
    		                                     .verificationCode(UUID.randomUUID().toString())
    		                                     .build();
       user.setAccountStatus(accountStatus);
	}

	private void setRole(UserDto userDto, User user) {
		
		List<Integer> reqrole=userDto.getRole().stream().map(r->r.getId()).toList();
		
		List<Role> role=roleRepo.findAllById(reqrole);
		user.setRole(role);
	}

	private void sendEmail(User userSave,String url) throws Exception {

		//String uurl="http://localhost:8080/";  //if we want to make this url dynamic then we have opetion
		
		String msg = "<html><body>"
		        + "Hi, <b>" + userSave.getFirstName() + "</b>,"
		        + "<br><br>Your account has been registered successfully!"
		        + "<br><br>Click the link below to verify your account:"
		        + "<br><a href='[[url]]'>Click here</a>"
		        + "<br><br>Thanks,"
		        + "<br>Enotes.com"
		        + "</body></html>";
		
		msg=msg.replace("[[url]]", url+"/api/v1/home/verify?uid="+userSave.getId()+"&&code="+userSave.getAccountStatus().getVerificationCode());
		
		EmailRequest emailReq=EmailRequest.builder()
				              .to(userSave.getEmail())
				              .title("account creating conformation")
				              .subject("account created success")
				              .message(msg)
				              .build();
		emailService.sendEmail(emailReq);
	}


	

	
}
