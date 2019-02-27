package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.inventory.EntityEquipmentSlot;

public class DogeBoots extends DogeArmour {
	
	public DogeBoots(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("doge_boots");
		setRegistryName("doge_boots");
	}
}
