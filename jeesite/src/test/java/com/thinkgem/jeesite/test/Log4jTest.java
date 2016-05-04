package com.thinkgem.jeesite.test;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.thinkgem.jeesite.common.log.FrontGuestbookLogLevel;

public class Log4jTest {
	public static void main(String[] args) {
		Logger logger = Logger.getLogger("com.foo"); // "com.foo" 是实例进行命名，也可以任意
		
		//PropertyConfigurator.configure("E:\\git\\repositories\\myjeesite\\jeesite\\src\\main\\resources\\jeesite.properties");
		
		logger.setLevel(Level.INFO);

		logger.info("Starting search for nearest gas station.");

		System.out.println();
	}
	
	
	
	//自定义log4j等级
	@Test
	public void FrontGuestbook(){
		Logger logger = Logger.getLogger("FrontGuestbook"); 
		FrontGuestbookLogLevel.FrontGuestbookLog(logger, "自定义日志级别");  
	        logger.info("哈哈哈哈");  
	}
	

}
