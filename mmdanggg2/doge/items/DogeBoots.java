package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.EnumArmorMaterial;

public class DogeBoots extends DogeArmour {
	
	public DogeBoots(int id, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int armourPart) {
		super(id, par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeBoots");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeBoots");
		
	}
	
}
