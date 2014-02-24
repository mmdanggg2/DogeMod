package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;

public class DogeAxe extends ItemAxe {
	
	public DogeAxe(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("dogeAxe");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeAxe");
		
	}
	
}
