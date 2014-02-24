package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;

public class DogeHoe extends ItemHoe {
	
	public DogeHoe(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("dogeHoe");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeHoe");
		
	}
	
}
