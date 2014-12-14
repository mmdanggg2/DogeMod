package mmdanggg2.doge.util;

import org.apache.logging.log4j.Logger;

import mmdanggg2.doge.DogeInfo;
import net.minecraftforge.fml.common.FMLLog;

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
