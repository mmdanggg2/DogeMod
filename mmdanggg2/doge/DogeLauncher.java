package mmdanggg2.doge;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DogeLauncher extends Item {

	public DogeLauncher(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("dogeLauncher");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogelauncher");
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		
		if (!par3EntityPlayer.capabilities.isCreativeMode)
	    {
	        --par1ItemStack.stackSize;
	    }
		
		if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityEgg(par2World, par3EntityPlayer));
        }
		
		return par1ItemStack;
	}

}
