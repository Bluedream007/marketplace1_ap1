package com.bluedream.cliapp.marketplace1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;
import com.bluedream.cliapp.marketplace1.domain.UserInfo;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {

		
	UserInfo findByUsername(String username);
	
		
	
	
}
