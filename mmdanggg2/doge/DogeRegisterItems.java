package mmdanggg2.doge;

import mmdanggg2.doge.items.DogeAxe;
import mmdanggg2.doge.items.DogeBoots;
import mmdanggg2.doge.items.DogeChestplate;
import mmdanggg2.doge.items.DogeHelmet;
import mmdanggg2.doge.items.DogeHoe;
import mmdanggg2.doge.items.DogeLauncher;
import mmdanggg2.doge.items.DogeLeggings;
import mmdanggg2.doge.items.DogePickaxe;
import mmdanggg2.doge.items.DogeShovel;
import mmdanggg2.doge.items.DogeSword;
import mmdanggg2.doge.items.Dogecoin;
import mmdanggg2.doge.items.GPU;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class DogeRegisterItems {
	
	public static void register() {
		Doge.dogeToolMat.customCraftingMaterial = Doge.dogecoin;
		Doge.dogeArmorMat.customCraftingMaterial = Doge.dogecoin;
		
		// Items
		Doge.dogecoin = new Dogecoin(Doge.dogecoinID);
		GameRegistry.registerItem(Doge.dogecoin, "dogecoin");
		LanguageRegistry.addName(Doge.dogecoin, "Dogecoin");
		
		Doge.dogeLauncher = new DogeLauncher(Doge.dogeLauncherID);
		GameRegistry.registerItem(Doge.dogeLauncher, "dogeLauncher");
		LanguageRegistry.addName(Doge.dogeLauncher, "Doge Launcher");
		
		Doge.gpu = new GPU(Doge.gpuID);
		GameRegistry.registerItem(Doge.gpu, "gpu");
		LanguageRegistry.addName(Doge.gpu, "GPU");
		
		// Doge Tools
		Doge.dogePickaxe = new DogePickaxe(Doge.dogePickaxeID, Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogePickaxe, "dogePickaxe");
		LanguageRegistry.addName(Doge.dogePickaxe, "such mine");
		
		Doge.dogeAxe = new DogeAxe(Doge.dogeAxeID, Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogeAxe, "dogeAxe");
		LanguageRegistry.addName(Doge.dogeAxe, "many chop");
		
		Doge.dogeShovel = new DogeShovel(Doge.dogeShovelID, Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogeShovel, "dogeShovel");
		LanguageRegistry.addName(Doge.dogeShovel, "much dig");
		
		Doge.dogeHoe = new DogeHoe(Doge.dogeHoeID, Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogeHoe, "dogeHoe");
		LanguageRegistry.addName(Doge.dogeHoe, "very farm");
		
		Doge.dogeSword = new DogeSword(Doge.dogeSwordID, Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogeSword, "dogeSword");
		LanguageRegistry.addName(Doge.dogeSword, "wow attack");
		
		// Doge Armour
		Doge.dogeHelmet = new DogeHelmet(Doge.dogeHelmetID, Doge.dogeArmorMat, Doge.dogeArmourRenderID, 0);
		GameRegistry.registerItem(Doge.dogeHelmet, "dogeHelmet");
		LanguageRegistry.addName(Doge.dogeHelmet, "many protect face");
		
		Doge.dogeChestplate = new DogeChestplate(Doge.dogeChestplateID, Doge.dogeArmorMat, Doge.dogeArmourRenderID, 1);
		GameRegistry.registerItem(Doge.dogeChestplate, "dogeChestplate");
		LanguageRegistry.addName(Doge.dogeChestplate, "much chest armour");
		
		Doge.dogeLeggings = new DogeLeggings(Doge.dogeLeggingsID, Doge.dogeArmorMat, Doge.dogeArmourRenderID, 2);
		GameRegistry.registerItem(Doge.dogeLeggings, "dogeLeggings");
		LanguageRegistry.addName(Doge.dogeLeggings, "very leg defend");
		
		Doge.dogeBoots = new DogeBoots(Doge.dogeBootsID, Doge.dogeArmorMat, Doge.dogeArmourRenderID, 3);
		GameRegistry.registerItem(Doge.dogeBoots, "dogeBoots");
		LanguageRegistry.addName(Doge.dogeBoots, "wow shoes");
	}
}
