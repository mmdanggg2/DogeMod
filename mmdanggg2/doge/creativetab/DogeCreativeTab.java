package mmdanggg2.doge.creativetab;

import mmdanggg2.doge.Doge;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class DogeCreativeTab extends CreativeTabs {
	
	public DogeCreativeTab(String label) {
		super(label);
	}
	
	@Override
	public Item getTabIconItem() {
		return Doge.dogecoin;
	}
	
}
