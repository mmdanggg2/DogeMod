package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class DogeShovel extends ItemSpade {

	public DogeShovel(int id, EnumToolMaterial par2EnumToolMaterial) {
		super(id, par2EnumToolMaterial);
		
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("dogeShovel");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeShovel");
		
	}

}
