package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemAxe;

public class DogeAxe extends ItemAxe {
	
	public DogeAxe(ToolMaterial material) {
		super(material, material.getAttackDamage(), -3.0f);//TODO check attack speed
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("doge_axe");
		setRegistryName("doge_axe");
	}
}
