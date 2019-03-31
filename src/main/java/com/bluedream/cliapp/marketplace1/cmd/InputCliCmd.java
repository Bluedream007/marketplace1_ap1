package com.bluedream.cliapp.marketplace1.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluedream.cliapp.marketplace1.service.ListInfoService;
import com.bluedream.cliapp.marketplace1.service.UserInfoService;

@Service
public class InputCliCmd {
	
	public static final Logger logger = LoggerFactory.getLogger(InputCliCmd.class);
	
	@Autowired 
	private UserInfoService userInfoService;
	
	@Autowired 
	private ListInfoService listInfoService;
	
	public List<String> cmdExecution(String inputData) {
		List<String> oRetMesgList = new ArrayList();
		ExeInfo oExeInfo = parseInputData(inputData);
		
		String strCmd = oExeInfo.getExeCommand(); 
        switch(strCmd) 
        { 
            case "REGISTER": 
            	logger.info("Execute REGISTER with data: "+ oExeInfo.getExeDatas());
            	String[] aryUserName = oExeInfo.getExeDatas();            	
            	oRetMesgList = userInfoService.registerUser(aryUserName[0]);            	
                break; 
            case "CREATE_LISTING": 
            	logger.info("Execute CREATE_LISTING with data: "+ oExeInfo.getExeDatas());
            	oRetMesgList = listInfoService.createListInfo(oExeInfo.getExeDatas());            	
                break;
            case "DELETE_LISTING": 
            	logger.info("Execute DELETE_LISTING with data: "+ oExeInfo.getExeDatas());
            	oRetMesgList = listInfoService.deleteListInfo(oExeInfo.getExeDatas());            	
                break;       
            case "GET_LISTING": 
            	logger.info("Execute GET_LISTING with data: "+ oExeInfo.getExeDatas());
            	oRetMesgList = listInfoService.fetchListInfo(oExeInfo.getExeDatas());            	
                break;  
            case "GET_CATEGORY": 
            	logger.info("Execute GET_CATEGORY with data: "+ oExeInfo.getExeDatas());
            	oRetMesgList = listInfoService.fetchByCategoryWithSort(oExeInfo.getExeDatas());            	
                break;      
            case "GET_TOP_CATEGORY": 
            	logger.info("Execute GET_TOP_CATEGORY with data: "+ oExeInfo.getExeDatas());
            	oRetMesgList = listInfoService.fetchTopCategory(oExeInfo.getExeDatas());            	
                break;        
            
            default: 
            	logger.info("no match command"); 
        } 
		
		return oRetMesgList;
	}
	
	private ExeInfo parseInputData(String inputStrData) {
		ExeInfo oRetExeInfo = null;
		String strInCmd = "";		
				
		/*
		String[] aryInpArgs = inputStrData.split(" +");
		String[] aryInDatas = new String[aryInpArgs.length-1];
		int i = 0;
		for(String s : aryInpArgs)
		{
			if (i == 0) {
				strInCmd = aryInpArgs[0];
			} else {
				aryInDatas[i-1] = aryInpArgs[i];
			}
			i = i +1;
			// for test
		    logger.info(i + "data of arrays", s);
		}
		*/
		// regex string parser
		List<String> listData = new ArrayList<String>();
		Matcher oMatcher = Pattern.compile("([^\']\\S*|\'.+?\')\\s*").matcher(inputStrData);
		while (oMatcher.find())			
			  listData.add(oMatcher.group(1).replace("'", "") ); // Add .replace(...) to remove surrounding quotes.

		String[] aryInDatas = new String[listData.size()-1];
		int i = 0;
		for(String s : listData)
		{
			if (i == 0) {
				strInCmd = listData.get(0);
			} else {
				aryInDatas[i-1] = listData.get(i);
			}
			// for test
			logger.info(i + "data of arrays"+ listData.get(i).toString());
			i = i +1;		    
		}
		
		oRetExeInfo = new ExeInfo(strInCmd, aryInDatas);
		
		return oRetExeInfo;
	}

}
