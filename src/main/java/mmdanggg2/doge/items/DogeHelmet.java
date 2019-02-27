package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.inventory.EntityEquipmentSlot;

public class DogeHelmet extends DogeArmour {

	public DogeHelmet(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("doge_helmet");
		setRegistryName("doge_helmet");
	}
}
