package com.lw.rpc.handler;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

/**
 * LogResolver
 *
 */
public class LogResolver {
	public static final Logger log = Logger.getLogger(LogResolver.class);

	public void afterSysLog(JoinPoint point) {
		try {
		} catch (Exception e) {
			log.error(e);
		}
	}
}
