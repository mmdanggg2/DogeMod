package mmdanggg2.doge.util;

import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	public static float getFloat(NBTTagCompound stackTag, String tag, float def) {
		if (stackTag != null) {
			if (stackTag.hasKey(tag)) {
				return stackTag.getFloat(tag);
			}
			else {
				stackTag.setFloat(tag, def);
				return def;
			}
		}
		return def;
	}
	
	public static int getInt(NBTTagCompound stackTag, String tag, int def) {
		if (stackTag != null) {
			if (stackTag.hasKey(tag)) {
				return stackTag.getInteger(tag);
			}
			else {
				stackTag.setInteger(tag, def);
				return def;
			}
		}
		return def;
	}
}
