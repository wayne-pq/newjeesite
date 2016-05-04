package com.thinkgem.jeesite.common.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;

public class FrontGuestbookLogLevel extends Level {

	private static final long serialVersionUID = 1L;

	/**
	 * 构造类
	 * 
	 * @author Sevencm
	 * 
	 */
	protected FrontGuestbookLogLevel(int level, String levelStr,
			int syslogEquivalent) {
		super(level, levelStr, syslogEquivalent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 自定义级别名称，以及级别范围
	 */
	private static final Level FrontGuestbookLogLevel = new FrontGuestbookLogLevel(
			40050, "FrontGuestbook", SyslogAppender.LOG_LOCAL0);

	/**
	 * 使用日志打印logger中的log方法
	 * 
	 * @param logger
	 * @param objLogInfo
	 */
	public static void FrontGuestbookLog(Logger logger, Object objLogInfo) {
		logger.log(FrontGuestbookLogLevel, objLogInfo);
	}

}
