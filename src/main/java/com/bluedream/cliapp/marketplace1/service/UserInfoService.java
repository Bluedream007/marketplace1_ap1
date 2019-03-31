package com.bluedream.cliapp.marketplace1.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluedream.cliapp.marketplace1.domain.UserInfo;
import com.bluedream.cliapp.marketplace1.repository.UserRepository;

@Service
public class UserInfoService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	private String strRetVal = "Error - unknow";
	
	public List<String> registerUser(String userName) {
		logger.info("registerUser: ", userName);
		List<String> oRetMesgList = new ArrayList();
		
		//String strRetVal = "Error - unknow";
		UserInfo oUserInfo = userRepository.findByUsername(userName);
		if (oUserInfo != null) {
			strRetVal = "Error - user already existing";
		} else {
		  oUserInfo = new UserInfo(userName);		  	
		  UserInfo newUser = userRepository.saveAndFlush(oUserInfo);        
		  strRetVal = "Success";
		}
		
		oRetMesgList.add(strRetVal);
		return oRetMesgList;
	}
	
	

}
