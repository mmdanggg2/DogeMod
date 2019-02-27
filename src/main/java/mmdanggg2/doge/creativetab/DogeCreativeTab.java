package mmdanggg2.doge.creativetab;

import mmdanggg2.doge.Doge;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DogeCreativeTab extends CreativeTabs {
	
	public DogeCreativeTab(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Doge.dogecoin);
	}
	
}
