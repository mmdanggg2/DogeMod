package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.inventory.EntityEquipmentSlot;

public class DogeLeggings extends DogeArmour {

	public DogeLeggings(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("doge_leggings");
		setRegistryName("doge_leggings");
	}
}
