package mmdanggg2.doge.util;

import mmdanggg2.doge.Doge;
import cpw.mods.fml.common.FMLLog;

public class DogeLogger {
	
	public static void logInfo(String message) {
		FMLLog.info(adjustMessage(message));
	}
	
	private static String adjustMessage(String message) {
		message = "[DOGE] " + message;
		return message;
	}

	public static void logDebug(String message) {
		if (Doge.debug) {
			FMLLog.info(adjustMessage(message));
		}
	}
}
