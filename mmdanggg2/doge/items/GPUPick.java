package mmdanggg2.doge.items;

import mmdanggg2.doge.Doge;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GPUPick extends Item {
	
	public GPUPick() {
		this.maxStackSize = 1;
		this.setMaxDamage(10);
		this.setCreativeTab(Doge.dogeTab);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		// TODO Auto-generated method stub
		return 2f;// super.getDigSpeed(stack, block, meta);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		// TODO Auto-generated method stub
		return super.getHarvestLevel(stack, toolClass);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		// TODO Auto-generated method stub
		return super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_,
			int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
		// TODO Auto-generated method stub
		return super.onBlockDestroyed(p_150894_1_, p_150894_2_, p_150894_3_, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
	}
}
