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
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class DogeRegisterItems {
	
	public static void register(IForgeRegistry<Item> itemReg) {
		
		// Items
		Doge.dogecoin = new Dogecoin();
		itemReg.register(Doge.dogecoin);
		
		Doge.dogeLauncher = new DogeLauncher();
		itemReg.register(Doge.dogeLauncher);
		
		Doge.gpu = new GPU();
		itemReg.register(Doge.gpu);
		
		// Doge Tools
		Doge.dogePickaxe = new DogePickaxe(Doge.dogeToolMat);
		itemReg.register(Doge.dogePickaxe);
		
		Doge.dogeAxe = new DogeAxe(Doge.dogeToolMat);
		itemReg.register(Doge.dogeAxe);
		
		Doge.dogeShovel = new DogeShovel(Doge.dogeToolMat);
		itemReg.register(Doge.dogeShovel);
		
		Doge.dogeHoe = new DogeHoe(Doge.dogeToolMat);
		itemReg.register(Doge.dogeHoe);
		
		Doge.dogeSword = new DogeSword(Doge.dogeToolMat);
		itemReg.register(Doge.dogeSword);
		
		// Doge Armour
		Doge.dogeHelmet = new DogeHelmet(Doge.dogeArmorMat, 0, EntityEquipmentSlot.HEAD);
		itemReg.register(Doge.dogeHelmet);
		
		Doge.dogeChestplate = new DogeChestplate(Doge.dogeArmorMat, 0, EntityEquipmentSlot.CHEST);
		itemReg.register(Doge.dogeChestplate);
		
		Doge.dogeLeggings = new DogeLeggings(Doge.dogeArmorMat, 0, EntityEquipmentSlot.LEGS);
		itemReg.register(Doge.dogeLeggings);
		
		Doge.dogeBoots = new DogeBoots(Doge.dogeArmorMat, 0, EntityEquipmentSlot.FEET);
		itemReg.register(Doge.dogeBoots);
		
		// Block Items
		itemReg.register(new ItemBlock(Doge.dogeBlock).setRegistryName(Doge.dogeBlock.getRegistryName()));
		itemReg.register(new ItemBlock(Doge.miningRig).setRegistryName(Doge.miningRig.getRegistryName()));
	}
}
