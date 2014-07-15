package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class DogeRegisterBlocks {
	// Blocks
	public static void register() {
		Doge.dogeBlock = new DogeBlock(Material.ground);
		GameRegistry.registerBlock(Doge.dogeBlock, "dogeBlock");
	}
}
