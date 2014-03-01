package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class DogeArmour extends ItemArmor {
	public DogeArmour(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
	}
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			int layer) {
		if (stack.itemID == Doge.dogeHelmet.itemID
				|| stack.itemID == Doge.dogeChestplate.itemID
				|| stack.itemID == Doge.dogeBoots.itemID) {
			return BasicInfo.NAME.toLowerCase() + ":textures/models/armor/doge_layer_1.png";
		}
		if (stack.itemID == Doge.dogeLeggings.itemID) {
			return BasicInfo.NAME.toLowerCase() + ":textures/models/armor/doge_layer_2.png";
		} else {
			return null;
		}
		
	}
}