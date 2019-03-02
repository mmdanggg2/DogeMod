package mmdanggg2.doge.items;

import javax.annotation.Nullable;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.entities.DogeProjectile;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.enchantment.EnchantmentArrowFire;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DogeLauncher extends ItemBow {
	
	public DogeLauncher() {
		super();
		setMaxStackSize(1);
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("doge_launcher");
		setRegistryName("doge_launcher");
	}
	
	protected ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isArrow(itemstack))
                {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

	@Override
    protected boolean isArrow(@Nullable ItemStack stack)
    {
		if (stack != null &&
				stack.getItem() instanceof Dogecoin && !stack.isEmpty()) {
			return true;
		} else {
			return false;
		}
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);
		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		ItemStack coinStack = findAmmo(player);
		
		if (flag || !coinStack.isEmpty()) {
			
			DogeProjectile projectile = new DogeProjectile(world, player);
			
			int powerEnchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
			
			if (powerEnchLvl > 0) {
				projectile.damage = projectile.damage + powerEnchLvl * 2f + 1f;
			}
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
            {
                projectile.setFire(100);
            }
			if (flag) {
				projectile.dropCoin = false;
			}
			else {
				coinStack.shrink(1);
				if (coinStack.isEmpty()) {
					player.inventory.deleteStack(coinStack);
				}
			}
			
			world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 1.5F));
			
			if (!world.isRemote) {
				projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2F, 1.0F);
				world.spawnEntity(projectile);
			}
	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		else {
			world.playSound(player, player.getPosition(), SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.NEUTRAL, 0.5F, 1f);
	        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		}
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.NONE;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 0;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase enitity, int timeLeft) {}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		Class<? extends Enchantment> enchClass = enchantment.getClass();
		if (enchClass == EnchantmentArrowDamage.class || enchClass == EnchantmentArrowInfinite.class || enchClass == EnchantmentArrowFire.class) {
			return true;
		} else {
			return false;
		}
	}
}
