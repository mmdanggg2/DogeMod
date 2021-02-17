package mmdanggg2.doge.item;

import java.util.function.Supplier;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.init.DogeItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class DogeGroup extends ItemGroup {
	
	// Creative Tab
	public static final ItemGroup dogeGroup = new DogeGroup();

	public DogeGroup() {
		super("doge_tab");
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(DogeItems.DOGECOIN.get());
	}
	
}
