package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;

public class DogeBoots extends DogeArmour {
	
	public DogeBoots(ArmorMaterial par2EnumArmorMaterial, int par3, int armourPart) {
		super(par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeBoots");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeBoots");
		
	}
	
}
