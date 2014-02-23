package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class DogeSword extends ItemSword {
	
	public DogeSword(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);
		
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("dogeSword");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeSword");
		
	}
	
}
