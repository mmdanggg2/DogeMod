package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
import mmdanggg2.doge.blocks.MiningRig;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class DogeRegisterBlocks {
	// Blocks
	public static void register(IForgeRegistry<Block> blockReg) {
		Doge.dogeBlock = new DogeBlock(Material.GROUND);
		blockReg.register(Doge.dogeBlock);
		
		Doge.miningRig = new MiningRig(Material.IRON, false);
		blockReg.register(Doge.miningRig);
		GameRegistry.registerTileEntity(MiningRigTileEntity.class, new ResourceLocation(DogeInfo.ID, "mining_rig_tile_entity"));
		
		Doge.miningRigOn = new MiningRig(Material.IRON, true);
		blockReg.register(Doge.miningRigOn);
	}
}
