package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class DogeArmour extends ItemArmor {
	public DogeArmour(ArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par2EnumArmorMaterial, par3, par4);
	}
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.getItem() == Doge.dogeHelmet
				|| stack.getItem() == Doge.dogeChestplate
				|| stack.getItem() == Doge.dogeBoots) {
			
			return BasicInfo.NAME.toLowerCase() + ":textures/models/armor/Doge_Layer_1.png";
		}
		if (stack.getItem() == Doge.dogeLeggings) {
			return BasicInfo.NAME.toLowerCase() + ":textures/models/armor/Doge_Layer_2.png";
		} else {
			return null;
		}
		
	}
}