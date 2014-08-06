package mmdanggg2.doge;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class DogeRegisterRecipies {
	public static void register() {
		// Recipes
		GameRegistry.addRecipe(new ItemStack(Doge.dogecoin, 1), " G ", "GDG", " G ", 'D', new ItemStack(Item.diamond), 'G', new ItemStack(Item.ingotGold));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeBlock), "###", "###", "###", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeLauncher, 1), " D ", "DD ", " BD", 'D', new ItemStack(Doge.dogecoin), 'B', new ItemStack(Block.stoneButton));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Doge.dogecoin, 9), new ItemStack(Doge.dogeBlock));
		
		GameRegistry.addRecipe(new ItemStack(Doge.gpu, 1), "RBR", "CGC", "III", 'R', new ItemStack(Items.redstone), 'G', new ItemStack(Items.gold_ingot), 'B', new ItemStack(Blocks.redstone_block), 'C', new ItemStack(Items.comparator), 'I', new ItemStack(Items.iron_ingot));
		
		GameRegistry.addRecipe(new ItemStack(Doge.miningRig, 1), "CDC", "GIG", "RPR", 'R', new ItemStack(Items.redstone), 'G', new ItemStack(Items.gold_ingot), 'P', new ItemStack(Items.iron_pickaxe), 'I', new ItemStack(Blocks.iron_block), 'C', new ItemStack(Items.comparator), 'D', new ItemStack(Doge.dogecoin));

		// Doge Tool Recipes
		GameRegistry.addRecipe(new ItemStack(Doge.dogePickaxe), "###", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Item.stick));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeAxe), " ##", " S#", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Item.stick));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeShovel), " # ", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Item.stick));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeHoe), " ##", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Item.stick));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeSword), " # ", " # ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Item.stick));
		
		// Doge Armour Recipes
		GameRegistry.addRecipe(new ItemStack(Doge.dogeHelmet), "###", "# #", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeChestplate), "# #", "###", "###", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeLeggings), "###", "# #", "# #", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeBoots), "# #", "# #", '#', new ItemStack(Doge.dogecoin));
	}
}
