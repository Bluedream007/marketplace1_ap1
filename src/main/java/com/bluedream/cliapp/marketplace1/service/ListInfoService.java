package com.bluedream.cliapp.marketplace1.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluedream.cliapp.marketplace1.domain.CategoryInfo;
import com.bluedream.cliapp.marketplace1.domain.ListInfo;
import com.bluedream.cliapp.marketplace1.domain.UserInfo;
import com.bluedream.cliapp.marketplace1.repository.CategoryInfoRepository;
import com.bluedream.cliapp.marketplace1.repository.ListInfoRepository;
import com.bluedream.cliapp.marketplace1.repository.UserRepository;



@Service
//public class ListInfoService implements IListInfoService {
public class ListInfoService {
	
	public static final Logger logger = LoggerFactory.getLogger(ListInfoService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ListInfoRepository listInfoRepository;
	
	@Autowired
	private CategoryInfoRepository categoryInfoRepository;
	
	private String strRetVal = "Error - unknow";
	
	public List<String> createListInfo(String[] listInfo) {
		logger.info("createListInfo: ", listInfo);
		// CREATE_LISTING <username> <title> <description> <price> <category>
		// CREATE_LISTING user1 'Phone model 8' 'Black color, brand new' 1000 'Electronics'
		List<String> oRetMesgList = new ArrayList();
		
		String strUserName = listInfo[0];
		String strTitle = listInfo[1];
		String strDescription = listInfo[2];
		Integer intPrice = new Integer(listInfo[3]);
		String strCategoryName = listInfo[4];
		
		UserInfo oUserInfo = userRepository.findByUsername(strUserName);
		if (oUserInfo == null) {
			strRetVal = "Error - unknown user";
		
		} else {
		  CategoryInfo oCategoryInfo = new CategoryInfo(strCategoryName);
		  ListInfo oListInfo = new ListInfo(strTitle, strDescription,oUserInfo, 
				                            intPrice, oCategoryInfo);		
		  
		  
		  CategoryInfo newCategoryInfo = categoryInfoRepository.saveAndFlush(oCategoryInfo);
		  ListInfo newListInfo = listInfoRepository.saveAndFlush(oListInfo);
		  
		  strRetVal = String.valueOf(newListInfo.getListingId());
		}
		
		oRetMesgList.add(strRetVal);
		return oRetMesgList;
	}
	
	/**
	 * DELETE_LISTING <username> <listing_id>
	 *	
	 * @param listInfo
	 * @return Responses:
		-	"Success"
		-	"Error - listing does not exist"
		-	"Error - listing owner mismatch"
	 */
	public List<String> deleteListInfo(String[] listInfo) {
		logger.info("deleteListInfo: "+ listInfo);
		List<String> oRetMesgList = new ArrayList();
		
		String strUserName = listInfo[0];
		long lngListId = Long.parseLong(listInfo[1]);
		ListInfo qryListInfo = listInfoRepository.findByListingId(lngListId);
		//UserInfo oUserInfo = userRepository.findByUsername(strUserName);
		if (qryListInfo == null) {
			strRetVal = "Error - listing does not exist";		
		} else {		  		  
		  if (qryListInfo.getUserInfo().getUsername().equals(strUserName)) { 
			   listInfoRepository.deleteById(lngListId);
		       strRetVal = "Success";
		  } else 
			   strRetVal = "Error - listing owner mismatch";		  
		}		
		
		oRetMesgList.add(strRetVal);
		return oRetMesgList;
	}
	
	/**
	 * GET_LISTING <username> <listing_id>
	 * # GET_LISTING user1 100001
	 * 
	 * @param listInfo
	 * @return Responses:
		-	"<title>|<description>|<price>|<created_at>|<category>|<username>"
	 * 
	 */
	public List<String> fetchListInfo(String[] listInfo) {
		logger.info("fetchListInfo: "+ listInfo);
		List<String> oRetMesgList = new ArrayList();
		
		String strUserName = listInfo[0];
		long lngListId = Long.parseLong(listInfo[1]);
		UserInfo oUserInfo = userRepository.findByUsername(strUserName);
		if (oUserInfo == null) {
			strRetVal = "Error - unknown user";
		
		} else {
		  		  
		  //ListInfo qryListInfo = listInfoRepository.findByListingId(oUserInfo, lngListId);
		  ListInfo qryListInfo = listInfoRepository.findByListingId(lngListId);		  
		  if (qryListInfo == null) {
			  strRetVal = "Error - not found";
		  } else {		  
			if (qryListInfo.getUserInfo().getUsername().equals(strUserName)) 
		       strRetVal = qryListInfo.toString("|", "FULL");
			else 
				strRetVal = "Error - not found";
		  }
		}		
		
		oRetMesgList.add(strRetVal);
		return oRetMesgList;
	}
	
	/**
	 * GET_CATEGORY <username> <category> {sort_price|sort_time} {asc|dsc}
	 * eg:
	 * # GET_CATEGORY user1 'Sports' sort_price dsc
	 * @param listInfo
	 * @return Responses:
           -	"Error - category not found"
           -	"Error - unknown user"
           -	"<title>|<description>|<price>|<created_at>
                 <title>|<description>|<price>|<created_at>
                 <title>|<description>|<price>|<created_at>
	 */
	public List<String> fetchByCategoryWithSort(String[] listInfo) {
		logger.info("fetchByCategoryWithSort: "+ listInfo);
		List<String> oRetMesgList = new ArrayList();
		
		String strUserName = listInfo[0];
		String strCategory = listInfo[1];
		String strSortField = listInfo[2];
		String strSortType = listInfo[3];
				
		UserInfo oUserInfo = userRepository.findByUsername(strUserName);
		
		if (oUserInfo == null) {
			strRetVal = "Error - unknown user";
			oRetMesgList.add(strRetVal);
		} else {
		  CategoryInfo oCategoryInfo = categoryInfoRepository.findByCategoryName(strCategory);
		  List<ListInfo> qryListInfo = null;
		  if (oCategoryInfo == null) {
			  strRetVal = "Error - category not found";
			  oRetMesgList.add(strRetVal);
		  } else {
			  if (strSortField.equals("sort_price") && strSortType.equals("dsc")) 
			     qryListInfo = listInfoRepository.qrySortByPriceDesc(strCategory);
			  if (strSortField.equals("sort_price") && strSortType.equals("asc")) 
				  qryListInfo = listInfoRepository.qrySortByPriceAsc(strCategory);
			  
			  if (strSortField.equals("sort_time") && strSortType.equals("dsc")) 
				  qryListInfo = listInfoRepository.qrySortByCreatetimeDesc(strCategory);
			  if (strSortField.equals("sort_time") && strSortType.equals("asc")) 
					  qryListInfo = listInfoRepository.qrySortByCreatetimeAsc(strCategory);
			  
			  for(ListInfo item : qryListInfo){
				  strRetVal = item.toString("|", "SHORT1");
				  oRetMesgList.add(strRetVal);
			  } // for 				
		  }	// if   
		} // if 			
		
		return oRetMesgList;
	}
	
	/**
	 * GET_TOP_CATEGORY <username>
	 * @param listInfo
	 * @return Responses:
        -	"Error - unknown user"
        -	<category name> (Category having the highest number of listings).

	 */
	public List<String> fetchTopCategory(String[] listInfo) {
		logger.info("fetchTopCategory: "+ listInfo);
		List<String> oRetMesgList = new ArrayList();
		List<Object[]> qryListInfo = null;
		
		String strUserName = listInfo[0];				
		UserInfo oUserInfo = userRepository.findByUsername(strUserName);
		
		if (oUserInfo == null) {
			strRetVal = "Error - unknown user";
			
		} else {
		  qryListInfo = listInfoRepository.fetchTopCategoryInfo();
		  if (qryListInfo != null) {
			  //strRetVal = qryListInfo.getCategoryName().getCategoryName();
			  for(Object[] item : qryListInfo){
				  
				  logger.info("fetchTopCategory: "+ item[0] + item[1]);
				  //strRetVal = item[0] + ":" + item[1];
				  strRetVal = (String) item[0];
				  oRetMesgList.add(strRetVal);
			  } // for 
		  } else 
			  oRetMesgList.add(strRetVal);	  
		    
		} // if 			
		
		
		return oRetMesgList;
	}
	
	/*
	 @Override
	 public ListInfo findByUserListingId(UserInfo userInfo, long listingId) {
		 ListInfo newListInfo = null;
		 
		 return newListInfo ;
		 
	 }
	*/

}
