package mmdanggg2.doge.item;

import javax.annotation.Nullable;

import mmdanggg2.doge.entities.DogeProjectile;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FlameEnchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.PowerEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DogeLauncher extends BowItem {
	
	public DogeLauncher() {
		super(new Item.Properties().group(DogeGroup.dogeGroup).maxStackSize(1));
		setRegistryName("doge_launcher");
	}
	
	protected ItemStack findAmmo(PlayerEntity player)
    {
        if (this.isArrow(player.getHeldItem(Hand.OFF_HAND)))
        {
            return player.getHeldItem(Hand.OFF_HAND);
        }
        else if (this.isArrow(player.getHeldItem(Hand.MAIN_HAND)))
        {
            return player.getHeldItem(Hand.MAIN_HAND);
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
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getHeldItem(hand);
		boolean flag = player.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
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
				world.addEntity(projectile);
			}
	        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
		}
		else {
			world.playSound(player, player.getPosition(), SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.NEUTRAL, 0.5F, 1f);
	        return new ActionResult<ItemStack>(ActionResultType.PASS, stack);
		}
	}
	
	@Override
	public UseAction getItemUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 0;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity enitity, int timeLeft) {}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		Class<? extends Enchantment> enchClass = enchantment.getClass();
		if (enchClass == PowerEnchantment.class || enchClass == InfinityEnchantment.class || enchClass == FlameEnchantment.class) {
			return true;
		} else {
			return false;
		}
	}
}
