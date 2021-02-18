package mmdanggg2.doge.util;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.config.DogeConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DogeLogger {
	
	public final static Logger logger = LogManager.getLogger(Doge.ID);
	
	public static void logInfo(String message) {
		logger.info(message);
	}

	public static void logDebug(String message) {
		if (DogeConfig.debug) {
			logger.info("[DEBUG] " + message);
		}
	}
}
