package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.DogeMob;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Dogecoin extends Item {
	public Dogecoin() {
		super();
		setMaxStackSize(64);
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogecoin");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogecoin");
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer player, EntityLivingBase activatedEntity) {
		if (!player.worldObj.isRemote && !(activatedEntity instanceof DogeMob) && !(activatedEntity instanceof EntityPlayer)) {
			if (!player.capabilities.isCreativeMode)
			{
				--par1ItemStack.stackSize;
			}
			DogeMob newDoge = new DogeMob(activatedEntity.worldObj);
			newDoge.setLocationAndAngles(
					activatedEntity.posX,
					activatedEntity.posY,
					activatedEntity.posZ,
					activatedEntity.rotationYaw,
					activatedEntity.rotationPitch);
			activatedEntity.setDead();
			if (!player.worldObj.isRemote) {
				player.worldObj.spawnEntityInWorld(newDoge);
				newDoge.makeTamed(player);
			}
		}
		return false;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		return false;
	}
}
