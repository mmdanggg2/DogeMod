package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class DogeSword extends ItemSword {
	
	public DogeSword(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeSword");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeSword");
		
	}
	
}
