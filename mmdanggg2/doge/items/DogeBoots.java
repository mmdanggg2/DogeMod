package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;

public class DogeBoots extends DogeArmour {
	
	public DogeBoots(ArmorMaterial par2EnumArmorMaterial,
			int par3, int armourPart) {
		super(par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("dogeBoots");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeBoots");
		
	}
	
}
