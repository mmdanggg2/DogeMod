package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;

public class DogePickaxe extends ItemPickaxe {
	
	public DogePickaxe(int id, EnumToolMaterial par2EnumToolMaterial) {
		super(id, par2EnumToolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogePickaxe");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogePick");
		
	}
}
