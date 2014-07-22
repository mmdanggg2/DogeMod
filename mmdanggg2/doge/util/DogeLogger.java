package mmdanggg2.doge.util;

import cpw.mods.fml.common.FMLLog;

public class DogeLogger {
	
	public static void logInfo(String message) {
		FMLLog.info(adjustMessage(message));
	}
	
	private static String adjustMessage(String message) {
		message = "[DOGE] " + message;
		return message;
	}
}
