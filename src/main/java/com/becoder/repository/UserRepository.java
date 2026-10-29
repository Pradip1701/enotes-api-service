package com.becoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.becoder.entity.AccountStatus;
import com.becoder.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	Boolean existsByEmail(String email);

	AccountStatus findByAccountStatusId(Integer id);

}
