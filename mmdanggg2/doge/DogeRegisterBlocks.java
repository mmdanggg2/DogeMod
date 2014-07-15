package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class DogeRegisterBlocks {
	public static void register() {
		// Blocks
		Doge.dogeBlock = new DogeBlock(Doge.dogeBlockID, Material.ground);
		GameRegistry.registerBlock(Doge.dogeBlock, "dogeBlock");
		LanguageRegistry.addName(Doge.dogeBlock, "Doge Block");
	}
}
