package mmdanggg2.doge;

import mmdanggg2.doge.client.gui.MiningRigScreen;
import mmdanggg2.doge.config.DogeConfig;
import mmdanggg2.doge.entity.ShibaEntity;
import mmdanggg2.doge.init.DogeBlocks;
import mmdanggg2.doge.init.DogeContainerTypes;
import mmdanggg2.doge.init.DogeEntityTypes;
import mmdanggg2.doge.item.DogeArmour;
import mmdanggg2.doge.item.DogeGroup;
import mmdanggg2.doge.item.DogeLauncher;
import mmdanggg2.doge.item.Dogecoin;
import mmdanggg2.doge.util.DogeLogger;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = Doge.ID, bus = EventBusSubscriber.Bus.MOD)
public class DogeModEventHandler {
	
	@SubscribeEvent
	public static void onFMLSetupEvent(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			GlobalEntityTypeAttributes.put(DogeEntityTypes.SHIBA.get(), ShibaEntity.getCustomAttributes().create());
		});
	}
	
	/*TODO Recipies
	public static void onRegisterRecipies() {
		//Item Repair
		Doge.dogeToolMat.setRepairItem(new ItemStack(Doge.dogecoin));
		Doge.dogeArmorMat.setRepairItem(new ItemStack(Doge.dogecoin));
	}*/
}
