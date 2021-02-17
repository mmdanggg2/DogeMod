package mmdanggg2.doge.init;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.block.MiningRig;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class DogeBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Doge.ID);
	
	public static final RegistryObject<Block> DOGE_BLOCK = BLOCKS.register("doge_block", ()-> new Block(Block.Properties.create(Material.IRON, MaterialColor.GOLD).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)));
	
	//public static final RegistryObject<MiningRig> MINING_RIG = BLOCKS.register("mining_rig", ()-> new MiningRig());
}
