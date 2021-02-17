package mmdanggg2.doge.item;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.init.DogeItems;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DogeArmour extends ArmorItem {
	public DogeArmour(EquipmentSlotType slot, String registryName) {
		super(DogeArmourMaterial.DOGE, slot, new Item.Properties().group(DogeGroup.dogeGroup));
		setRegistryName(registryName);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		if (stack.getItem() == DogeItems.doge_helmet
				|| stack.getItem() == DogeItems.doge_chestplate
				|| stack.getItem() == DogeItems.doge_boots) {
			
			return Doge.ID + ":textures/models/armor/doge_layer_1.png";
		}
		else if (stack.getItem() == DogeItems.doge_leggings) {
			return Doge.ID + ":textures/models/armor/doge_layer_2.png";
		} else {
			return null;
		}
		
	}
}