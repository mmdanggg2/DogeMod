package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class DogeSword extends ItemSword {
	
	public DogeSword(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("dogeSword");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeSword");
		
	}
	
}
