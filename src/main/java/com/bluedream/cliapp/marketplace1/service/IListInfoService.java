package com.bluedream.cliapp.marketplace1.service;

import com.bluedream.cliapp.marketplace1.domain.ListInfo;
import com.bluedream.cliapp.marketplace1.domain.UserInfo;

public interface IListInfoService {
	
	ListInfo findByUserListingId(UserInfo userInfo, long listingId);
	
	

}
