package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.ItemAxe;

public class DogeAxe extends ItemAxe {
	
	public DogeAxe(ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeAxe");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeAxe");
		
	}
	
}
