package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
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
			
			return DogeInfo.NAME.toLowerCase() + ":textures/models/armor/doge_layer_1.png";
		}
		if (stack.getItem() == Doge.dogeLeggings) {
			return DogeInfo.NAME.toLowerCase() + ":textures/models/armor/doge_layer_2.png";
		} else {
			return null;
		}
		
	}
}