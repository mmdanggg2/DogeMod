package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemSpade;

public class DogeShovel extends ItemSpade {
	
	public DogeShovel(ToolMaterial toolMaterial) {
		super(toolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeShovel");
		setRegistryName("dogeShovel");
	}
}
