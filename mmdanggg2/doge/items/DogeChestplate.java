package mmdanggg2.doge.items;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.EnumArmorMaterial;

public class DogeChestplate extends DogeArmour {
	
	public DogeChestplate(int id, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int armourPart) {
		super(id, par2EnumArmorMaterial, par3, armourPart);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeChestPlate");
		setTextureName(DogeInfo.NAME.toLowerCase() + ":dogeChestplate");
		
	}
	
	
	
	
}
