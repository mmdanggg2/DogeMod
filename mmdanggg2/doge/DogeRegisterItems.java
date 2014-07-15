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
import cpw.mods.fml.common.registry.GameRegistry;

public class DogeRegisterItems {
	
	public static void register() {
		Doge.dogeToolMat.customCraftingMaterial = Doge.dogecoin;
		Doge.dogeArmorMat.customCraftingMaterial = Doge.dogecoin;
		
		// Items
		Doge.dogecoin = new Dogecoin();
		GameRegistry.registerItem(Doge.dogecoin, "dogecoin");
		
		Doge.dogeLauncher = new DogeLauncher();
		GameRegistry.registerItem(Doge.dogeLauncher, "dogeLauncher");
		
		// Doge Tools
		Doge.dogePickaxe = new DogePickaxe(Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogePickaxe, "dogePickaxe");
		
		Doge.dogeAxe = new DogeAxe(Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogeAxe, "dogeAxe");
		
		Doge.dogeShovel = new DogeShovel(Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogeShovel, "dogeShovel");
		
		Doge.dogeHoe = new DogeHoe(Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogeHoe, "dogeHoe");
		
		Doge.dogeSword = new DogeSword(Doge.dogeToolMat);
		GameRegistry.registerItem(Doge.dogeSword, "dogeSword");
		
		// Doge Armour
		Doge.dogeHelmet = new DogeHelmet(Doge.dogeArmorMat, Doge.dogeArmourRenderID, 0);
		GameRegistry.registerItem(Doge.dogeHelmet, "dogeHelmet");
		
		Doge.dogeChestplate = new DogeChestplate(Doge.dogeArmorMat, Doge.dogeArmourRenderID, 1);
		GameRegistry.registerItem(Doge.dogeChestplate, "dogeChestplate");
		
		Doge.dogeLeggings = new DogeLeggings(Doge.dogeArmorMat, Doge.dogeArmourRenderID, 2);
		GameRegistry.registerItem(Doge.dogeLeggings, "dogeLeggings");
		
		Doge.dogeBoots = new DogeBoots(Doge.dogeArmorMat, Doge.dogeArmourRenderID, 3);
		GameRegistry.registerItem(Doge.dogeBoots, "dogeBoots");
		
	}
}
