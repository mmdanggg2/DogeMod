package mmdanggg2.doge.items;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemHoe;

public class DogeHoe extends ItemHoe {
	
	public DogeHoe(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeHoe");
		//FIXME setTextureName(DogeInfo.NAME.toLowerCase() + ":dogeHoe");
		
	}
	
}
