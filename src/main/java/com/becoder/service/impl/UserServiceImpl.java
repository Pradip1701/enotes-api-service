package com.becoder.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.EmailRequest;
import com.becoder.dto.UserDto;
import com.becoder.entity.Role;
import com.becoder.entity.User;
import com.becoder.repository.RoleRepository;
import com.becoder.repository.UserRepository;
import com.becoder.service.UserService;
import com.becoder.util.Validation;

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
	public Boolean register(UserDto userDto) throws Exception {
		
		//user validation
		validation.UserValidation(userDto);
		User user=mapper.map(userDto, User.class);
		
		setRole(userDto,user);
		User userSave=userRepo.save(user);
		if(!ObjectUtils.isEmpty(userSave))
		{
			sendEmail(userSave);
			return true;
		}else
			return false;
	}

	private void setRole(UserDto userDto, User user) {
		
		List<Integer> reqrole=userDto.getRole().stream().map(r->r.getId()).toList();
		
		List<Role> role=roleRepo.findAllById(reqrole);
		user.setRole(role);
	}

	private void sendEmail(User userSave) throws Exception {

		String msg = "<html><body>"
		        + "Hi, <b>" + userSave.getFirstName() + "</b>,"
		        + "<br><br>Your account has been registered successfully!"
		        + "<br><br>Click the link below to verify your account:"
		        + "<br><a href='http://localhost:8080/verify-account'>Click here</a>"
		        + "<br><br>Thanks,"
		        + "<br>Enotes.com"
		        + "</body></html>";
		
		EmailRequest emailReq=EmailRequest.builder()
				              .to(userSave.getEmail())
				              .title("account creating conformation")
				              .subject("account created success")
				              .message(msg)
				              .build();
		emailService.sendEmail(emailReq);
	}


	

	
}
