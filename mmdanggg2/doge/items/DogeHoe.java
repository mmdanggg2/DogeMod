package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;

public class DogeHoe extends ItemHoe {
	
	public DogeHoe(int id, EnumToolMaterial par2EnumToolMaterial) {
		super(id, par2EnumToolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeHoe");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeHoe");
		
	}
	
}
