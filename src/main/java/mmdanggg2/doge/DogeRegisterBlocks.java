package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
import mmdanggg2.doge.blocks.MiningRig;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DogeRegisterBlocks {
	// Blocks
	public static void register() {
		Doge.dogeBlock = new DogeBlock(Material.GROUND);
		GameRegistry.register(Doge.dogeBlock);
		GameRegistry.register(new ItemBlock(Doge.dogeBlock).setRegistryName(Doge.dogeBlock.getRegistryName()));
		
		Doge.miningRig = new MiningRig(Material.IRON, false);
		GameRegistry.register(Doge.miningRig);
		GameRegistry.register(new ItemBlock(Doge.miningRig).setRegistryName(Doge.miningRig.getRegistryName()));
		GameRegistry.registerTileEntity(MiningRigTileEntity.class, "miningRigTileEntity");
		
		Doge.miningRigOn = new MiningRig(Material.IRON, true);
		GameRegistry.register(Doge.miningRigOn);
		GameRegistry.register(new ItemBlock(Doge.miningRigOn).setRegistryName(Doge.miningRigOn.getRegistryName()));
	}
}
