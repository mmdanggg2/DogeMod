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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DogeRegisterItems {
	
	public static void register() {
		
		// Items
		Doge.dogecoin = new Dogecoin();
		GameRegistry.register(Doge.dogecoin);
		
		Doge.dogeLauncher = new DogeLauncher();
		GameRegistry.register(Doge.dogeLauncher);
		
		Doge.gpu = new GPU();
		GameRegistry.register(Doge.gpu);
		
		// Doge Tools
		Doge.dogePickaxe = new DogePickaxe(Doge.dogeToolMat);
		GameRegistry.register(Doge.dogePickaxe);
		
		Doge.dogeAxe = new DogeAxe(Doge.dogeToolMat);
		GameRegistry.register(Doge.dogeAxe);
		
		Doge.dogeShovel = new DogeShovel(Doge.dogeToolMat);
		GameRegistry.register(Doge.dogeShovel);
		
		Doge.dogeHoe = new DogeHoe(Doge.dogeToolMat);
		GameRegistry.register(Doge.dogeHoe);
		
		Doge.dogeSword = new DogeSword(Doge.dogeToolMat);
		GameRegistry.register(Doge.dogeSword);
		
		// Doge Armour
		Doge.dogeHelmet = new DogeHelmet(Doge.dogeArmorMat, 0, EntityEquipmentSlot.HEAD);
		GameRegistry.register(Doge.dogeHelmet);
		
		Doge.dogeChestplate = new DogeChestplate(Doge.dogeArmorMat, 0, EntityEquipmentSlot.CHEST);
		GameRegistry.register(Doge.dogeChestplate);
		
		Doge.dogeLeggings = new DogeLeggings(Doge.dogeArmorMat, 0, EntityEquipmentSlot.LEGS);
		GameRegistry.register(Doge.dogeLeggings);
		
		Doge.dogeBoots = new DogeBoots(Doge.dogeArmorMat, 0, EntityEquipmentSlot.FEET);
		GameRegistry.register(Doge.dogeBoots);
		
	}
}
