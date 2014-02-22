package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.entities.DogeProjectile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
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
            par2World.spawnEntityInWorld(new DogeProjectile(par2World, par3EntityPlayer));
        }
		
		return par1ItemStack;
	}

}
