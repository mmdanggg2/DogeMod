package mmdanggg2.doge.init;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.block.DogeBlock;
import mmdanggg2.doge.block.MiningRig;
import mmdanggg2.doge.item.DogeArmour;
import mmdanggg2.doge.item.DogeLauncher;
import mmdanggg2.doge.item.Dogecoin;
import mmdanggg2.doge.item.GPU;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Doge.ID)
public class DogeItems {
	
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, Doge.ID);
	
	// Tools
	public static final PickaxeItem doge_pickaxe = null;
	public static final AxeItem doge_axe = null;
	public static final ShovelItem doge_shovel = null;
	public static final HoeItem doge_hoe = null;
	public static final SwordItem doge_sword = null;

	// Doge Armour
	public static final DogeArmour doge_helmet = null;
	public static final DogeArmour doge_chestplate = null;
	public static final DogeArmour doge_leggings = null;
	public static final DogeArmour doge_boots = null;

	// Other
	public static final Dogecoin dogecoin = null;

	public static final DogeLauncher doge_launcher = null;
	
	public static final GPU gpu = null;
	
	//Blocks
	public static final BlockItem doge_block = null;
	
	public static final BlockItem mining_rig = null;
}
