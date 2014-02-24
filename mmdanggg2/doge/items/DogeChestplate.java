package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;

public class DogeChestplate extends DogeArmour {
	
	public DogeChestplate(ArmorMaterial par2EnumArmorMaterial,
			int par3, int armourPart) {
		super(par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("dogeChestplate");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeChestplate");
		
	}
	
	
	
	
}
