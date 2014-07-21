package mmdanggg2.doge.items;

import mmdanggg2.doge.BasicInfo;
import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.DogeProjectile;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class DogeLauncher extends ItemBow {
	
	public DogeLauncher() {
		super();
		setMaxStackSize(1);
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeLauncher");
		setTextureName(BasicInfo.NAME.toLowerCase() + ":dogeLauncher");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

		boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
		
		if (flag || par3EntityPlayer.inventory.hasItem(Doge.dogecoin)) {
			
			DogeProjectile projectile = new DogeProjectile(par2World, par3EntityPlayer);
			
			int powerEnchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
			
			if (powerEnchLvl > 0) {
				projectile.damage = projectile.damage + powerEnchLvl * 2f + 1f;
			}
			if (flag) {
				projectile.dropCoin = false;
			}
			else {
				par3EntityPlayer.inventory.consumeInventoryItem(Doge.dogecoin);
			}
			
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			
			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(projectile);
			}
		}
		else {
			par2World.playSoundAtEntity(par3EntityPlayer, "note.hat", 0.5F, 1f);
		}
		return par1ItemStack;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(this.getIconString());
	}
	
	@Override
	public IIcon getItemIconForUseDuration(int p_94599_1_) {
		return this.itemIcon;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.none;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 0;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {}
}
