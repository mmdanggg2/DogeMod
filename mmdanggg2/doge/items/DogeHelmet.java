package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;

public class DogeHelmet extends DogeArmour {

	public DogeHelmet(int id, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int armourPart) {
		super(id, par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("dogeHelmet");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeHelmet");
		
	}

}
