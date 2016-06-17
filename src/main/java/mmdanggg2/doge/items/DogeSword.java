package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemSword;

public class DogeSword extends ItemSword {
	
	public DogeSword(ToolMaterial toolMaterial) {
		super(toolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeSword");
		setRegistryName("dogeSword");
	}
}
