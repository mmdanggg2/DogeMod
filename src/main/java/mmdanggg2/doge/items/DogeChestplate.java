package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.inventory.EntityEquipmentSlot;

public class DogeChestplate extends DogeArmour {

	public DogeChestplate(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeChestplate");
		setRegistryName("dogeChestplate");
	}
}
