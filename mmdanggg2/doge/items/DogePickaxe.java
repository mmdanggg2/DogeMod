package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;

public class DogePickaxe extends ItemPickaxe {
	
	public DogePickaxe(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("dogePickaxe");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogePick");
		
	}
}
