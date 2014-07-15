package mmdanggg2.doge;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class DogeRegisterRecipies {
	public static void register() {
		// Recipes
		GameRegistry.addRecipe(new ItemStack(Doge.dogecoin, 1), " G ", "GDG", " G ", 'D', new ItemStack(Items.diamond), 'G', new ItemStack(Items.gold_ingot));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeBlock), "###", "###", "###", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeLauncher, 1), " D ", "DD ", " BD", 'D', new ItemStack(Doge.dogecoin), 'B', new ItemStack(Blocks.stone_button));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Doge.dogecoin, 9), new ItemStack(Doge.dogeBlock));
		
		// Doge Tool Recipes
		GameRegistry.addRecipe(new ItemStack(Doge.dogePickaxe), "###", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.stick));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeAxe), " ##", " S#", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.stick));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeShovel), " # ", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.stick));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeHoe), " ##", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.stick));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeSword), " # ", " # ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.stick));
		
		// Doge Armour Recipes
		GameRegistry.addRecipe(new ItemStack(Doge.dogeHelmet), "###", "# #", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeChestplate), "# #", "###", "###", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeLeggings), "###", "# #", "# #", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeBoots), "# #", "# #", '#', new ItemStack(Doge.dogecoin));
	}
}
