package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemPickaxe;

public class DogePickaxe extends ItemPickaxe {
	
	public DogePickaxe(ToolMaterial toolMaterial) {
		super(toolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogePickaxe");
		setRegistryName("dogePickaxe");
	}
}
