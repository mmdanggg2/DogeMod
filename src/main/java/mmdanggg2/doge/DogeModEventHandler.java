package mmdanggg2.doge;

import mmdanggg2.doge.entity.ShibaEntity;
import mmdanggg2.doge.init.DogeEntityTypes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

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
