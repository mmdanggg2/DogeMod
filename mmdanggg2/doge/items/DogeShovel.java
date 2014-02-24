package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;

public class DogeShovel extends ItemSpade {
	
	public DogeShovel(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("dogeShovel");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeShovel");
		
	}
	
}
