package mmdanggg2.doge.item;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import mmdanggg2.doge.entities.DogeProjectile;
import mmdanggg2.doge.init.DogeEntityTypes;
import mmdanggg2.doge.init.DogeItems;
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
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DogeLauncher extends ShootableItem {
	
	public DogeLauncher() {
		super(new Item.Properties().group(DogeGroup.dogeGroup).maxStackSize(1));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

		ItemStack stack = player.getHeldItem(hand);
		boolean infAmmo = player.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		ItemStack coinStack = player.findAmmo(stack);
		
		if (infAmmo || !coinStack.isEmpty()) {

			if (!world.isRemote) {
				DogeProjectile projectile = new DogeProjectile(player, world);
				
				int powerEnchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				
				if (powerEnchLvl > 0) {
					projectile.damage = projectile.damage + powerEnchLvl * 2f + 1f;
				}
				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
				{
					projectile.setFire(100);
				}
				
				projectile.dropCoin = !infAmmo;
				
				//the function formally known as projectile.shoot
				projectile.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, 2F, 1.0F);
				world.addEntity(projectile);
			}
			world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (random.nextFloat() * 1.5F));
			
			if (!infAmmo) {
				coinStack.shrink(1);
				if (coinStack.isEmpty()) {
					player.inventory.deleteStack(coinStack);
				}
			}
			return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
		}
		else {
			world.playSound(player, player.getPosition(), SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.NEUTRAL, 0.5F, 1f);
			return new ActionResult<ItemStack>(ActionResultType.PASS, stack);
		}
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 0;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity enitity, int timeLeft) {}
	
	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return (stack) -> stack.getItem() == DogeItems.DOGECOIN.get();
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}
	
	@Override
	public int getItemEnchantability() {
		return 45;
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		boolean valid = true;
		for (Enchantment ench : EnchantmentHelper.getEnchantments(book).keySet()) {
			valid &= canApplyAtEnchantingTable(stack, ench);
		}
		return valid;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		Class<? extends Enchantment> enchClass = enchantment.getClass();
		if (enchClass == PowerEnchantment.class || enchClass == InfinityEnchantment.class || enchClass == FlameEnchantment.class) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int func_230305_d_() {
		return 10;
	}
}
