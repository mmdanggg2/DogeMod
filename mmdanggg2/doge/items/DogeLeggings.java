package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;

public class DogeLeggings extends DogeArmour {
	
	public DogeLeggings(ArmorMaterial par2EnumArmorMaterial,
			int par3, int armourPart) {
		super(par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("dogeLeggings");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeLeggings");
		
	}
	
}
