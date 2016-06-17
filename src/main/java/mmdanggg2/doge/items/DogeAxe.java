package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemAxe;

public class DogeAxe extends ItemAxe {
	
	public DogeAxe(ToolMaterial material) {
		super(material, material.getDamageVsEntity(), material.getEfficiencyOnProperMaterial());
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeAxe");
		setRegistryName("dogeAxe");
	}
}
