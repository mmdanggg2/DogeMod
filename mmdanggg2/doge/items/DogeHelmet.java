package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;

public class DogeHelmet extends DogeArmour {
	
	public DogeHelmet(ArmorMaterial par2EnumArmorMaterial,
			int par3, int armourPart) {
		super(par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("dogeHelmet");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeHelmet");
		
	}
	
}
