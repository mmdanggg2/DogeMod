package mmdanggg2.doge.items;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemPickaxe;

public class DogePickaxe extends ItemPickaxe {
	
	public DogePickaxe(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogePickaxe");
		//FIXME setTextureName(DogeInfo.NAME.toLowerCase() + ":dogePick");
		
	}
}
