package mmdanggg2.doge.item;

import mmdanggg2.doge.init.DogeItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum DogeItemTier implements IItemTier {
	DOGE;

	public int getMaxUses() {
		return 780;
	}
	
	public float getEfficiency() {
		return 20.0f;
	}
	
	public float getAttackDamage() {
		return 4.0f;
	}
	
	public int getHarvestLevel() {
		return 3;
	}
	
	public int getEnchantability() {
		return 20;
	}
	
	public Ingredient getRepairMaterial() {
		return Ingredient.fromItems(DogeItems.DOGECOIN.get());
	}
	
}
