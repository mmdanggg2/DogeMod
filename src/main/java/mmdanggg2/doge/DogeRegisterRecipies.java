package mmdanggg2.doge;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DogeRegisterRecipies {
	public static void register() {
		// Recipes
		//GameRegistry.addShapedRecipe(new ResourceLocation(DogeInfo.ID, "")., new ResourceLocation(""), new ItemStack(Doge.dogecoin, 1), " G ", "GDG", " G ", 'D', new ItemStack(Items.DIAMOND), 'G', new ItemStack(Items.GOLD_INGOT));
		
		//GameRegistry.addRecipe(new ItemStack(Doge.dogeBlock), "###", "###", "###", '#', new ItemStack(Doge.dogecoin));
		
		/*GameRegistry.addRecipe(new ItemStack(Doge.dogeLauncher, 1), " D ", "DD ", " BD", 'D', new ItemStack(Doge.dogecoin), 'B', new ItemStack(Blocks.STONE_BUTTON));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Doge.dogecoin, 9), new ItemStack(Doge.dogeBlock));
		
		GameRegistry.addRecipe(new ItemStack(Doge.gpu, 1), "RBR", "CGC", "III", 'R', new ItemStack(Items.REDSTONE), 'G', new ItemStack(Items.GOLD_INGOT), 'B', new ItemStack(Blocks.REDSTONE_BLOCK), 'C', new ItemStack(Items.COMPARATOR), 'I', new ItemStack(Items.IRON_INGOT));
		
		GameRegistry.addRecipe(new ItemStack(Doge.miningRig, 1), "CDC", "GIG", "RPR", 'R', new ItemStack(Items.REDSTONE), 'G', new ItemStack(Items.GOLD_INGOT), 'P', new ItemStack(Items.IRON_PICKAXE), 'I', new ItemStack(Blocks.IRON_BLOCK), 'C', new ItemStack(Items.COMPARATOR), 'D', new ItemStack(Doge.dogecoin));

		// Doge Tool Recipes
		GameRegistry.addRecipe(new ItemStack(Doge.dogePickaxe), "###", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.STICK));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeAxe), " ##", " S#", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.STICK));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeShovel), " # ", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.STICK));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeHoe), " ##", " S ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.STICK));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeSword), " # ", " # ", " S ", '#', new ItemStack(Doge.dogecoin), 'S', new ItemStack(Items.STICK));
		
		// Doge Armour Recipes
		GameRegistry.addRecipe(new ItemStack(Doge.dogeHelmet), "###", "# #", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeChestplate), "# #", "###", "###", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeLeggings), "###", "# #", "# #", '#', new ItemStack(Doge.dogecoin));
		
		GameRegistry.addRecipe(new ItemStack(Doge.dogeBoots), "# #", "# #", '#', new ItemStack(Doge.dogecoin));
		*/
		//Item Repair
		Doge.dogeToolMat.setRepairItem(new ItemStack(Doge.dogecoin));
		Doge.dogeArmorMat.setRepairItem(new ItemStack(Doge.dogecoin));
	}
}
