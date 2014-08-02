package mmdanggg2.doge.items;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.Doge;

public class DogeChestplate extends DogeArmour {
	
	public DogeChestplate(ArmorMaterial par2EnumArmorMaterial, int par3, int armourPart) {
		super(par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeChestplate");
		setTextureName(DogeInfo.NAME.toLowerCase() + ":dogeChestplate");
		
	}
	
	
	
	
}
