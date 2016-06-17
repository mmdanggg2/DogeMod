package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemHoe;

public class DogeHoe extends ItemHoe {
	
	public DogeHoe(ToolMaterial toolMaterial) {
		super(toolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeHoe");
		setRegistryName("dogeHoe");
	}
}
