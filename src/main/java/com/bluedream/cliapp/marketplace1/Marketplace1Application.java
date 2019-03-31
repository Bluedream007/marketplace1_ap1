package com.bluedream.cliapp.marketplace1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bluedream.cliapp.marketplace1.cmd.InputCliCmd;
import com.bluedream.cliapp.marketplace1.domain.ListInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;

@SpringBootApplication
public class Marketplace1Application implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(Marketplace1Application.class);
			
	//@Value("${spring.main.banner-mode}")
	//String bannerMode;
	
	//@Value("${myArgument}")
	//String strMyArgs;
	
	@Autowired 
	InputCliCmd oInputCliCmd;
	
	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");		
		
		SpringApplication app = new SpringApplication(Marketplace1Application.class);		
		app.setBannerMode(Banner.Mode.OFF);		
        app.run(args);		
		LOG.info("APPLICATION FINISHED");
	}
	
	@Override
    public void run(String... args) {
				
		BufferedReader br = null;
		int intCnt = 0;
		String strSrc = "";
        String strTar = "";

        try {

            // Refer to this http://www.mkyong.com/java/how-to-read-input-from-console-java/
            // for JDK 1.6, please use java.io.Console class to read system input.
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter something after #. Input 'q' to quit execution.");
            String input;
            List<String> oRetMesgList = new ArrayList();
            
            while (true) {                
                System.out.print("# ");  
                input = br.readLine();
                

                if ("q".equals(input)) {
                    System.out.println("Exit!");
                    System.exit(0);
                } else if (input != null) {

                	oRetMesgList = oInputCliCmd.cmdExecution(input);
                }
                
                // show output   
                /*
                if (oRetMesgList.size() == 1) {
                   System.out.println(oRetMesgList.get(0));
                } else {    
                  for(String item : oRetMesgList){
                	  System.out.println(item);
      			  } 	
                   
                } 
                */  
                //System.out.println("-----------\n");
                for(String item : oRetMesgList){
              	  System.out.println(item);
    			} 
                
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	/*
	@PostConstruct
	private void postConstruct() {
		LOG.info("\n---------------postConstruct()-----------------------");		
		System.out.println("myArgument: " + strMyArgs);;
	}
	*/

}
