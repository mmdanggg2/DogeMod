package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;

public class DogeChestplate extends DogeArmour {
	
	public DogeChestplate(int id, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int armourPart) {
		super(id, par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("dogeChestPlate");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeChestplate");
		
	}
	
	
	
	
}
