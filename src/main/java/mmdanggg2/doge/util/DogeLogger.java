package mmdanggg2.doge.util;

import mmdanggg2.doge.DogeInfo;

import org.apache.logging.log4j.Logger;

public class DogeLogger {
	
	public static Logger logger;
	
	public static void logInfo(String message) {
		logger.info(message);
	}

	public static void logDebug(String message) {
		if (DogeInfo.debug) {
			logger.info("[DEBUG] " + message);
		}
	}
}
